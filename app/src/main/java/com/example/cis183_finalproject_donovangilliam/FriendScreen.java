package com.example.cis183_finalproject_donovangilliam;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
// this was a pain in the ass
public class FriendScreen extends AppCompatActivity
{
    ArrayList<Friend> friend_list = new ArrayList<>();
    DatabaseHelper dbhelper;

    Button btn_j_fs_addfriend;
    Button btn_j_fs_sortfriend;
    Button btn_j_fs_home;
    Button btn_j_fs_userinfo;

    ListView lv_j_fs_friendlist;

    Intent intent_j_fs_mainactivity;
    Intent intent_j_fs_addfriend;
    Intent intent_j_fs_managefriend;

    FriendListAdapter adapter = new FriendListAdapter(this, friend_list);

    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_friend_screen);

        btn_j_fs_addfriend = findViewById(R.id.btn_v_fs_addfriend);
        btn_j_fs_sortfriend = findViewById(R.id.btn_v_fs_sortfriend);
        btn_j_fs_home = findViewById(R.id.btn_v_fs_home);
        btn_j_fs_userinfo = findViewById(R.id.btn_v_fs_userinfo);


        lv_j_fs_friendlist = findViewById(R.id.lv_v_fs_friendlist);
        lv_j_fs_friendlist.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        intent_j_fs_mainactivity = new Intent(FriendScreen.this, MainActivity.class);
        intent_j_fs_addfriend = new Intent(FriendScreen.this, AddFriendScreen.class);
        intent_j_fs_managefriend = new Intent(FriendScreen.this, ManageFriendScreen.class);

        //ADD CHECK TO SEE IF ACTIVITY STARTED FROM LOGIN SCREEN, ONLY PUSH NOTIFICATIONS IF COMING
        // FROM THIS SCREEN
        Intent cameFrom = getIntent();
        dbhelper = new DatabaseHelper(FriendScreen.this);

        dbhelper.fillFriendsList(Session.getLoggedInUser(), friend_list);

        if (cameFrom != null)
        {
            String extra = cameFrom.getStringExtra("MainScreen");
            if (extra != null && extra.equals("Main"))
            {
                if (dbhelper.lastLoginThreeDaysElapsed(Session.getLoggedInUser())) {
                    loginLapseNotification();
                    dbhelper.updateLastLogin(Session.getLoggedInUser());
                }
                else
                {
                    dbhelper.updateLastLogin(Session.getLoggedInUser());

                }
            }
            else
            {
                dbhelper.updateLastLogin(Session.getLoggedInUser());

            }
        }

        manageFriendListener();
        deleteFriendListener();
        addFriendListener();
        homeButtonListener();
        sortFriendListener();
        userInfoListener();
    }

    private void manageFriendListener()
    {
        lv_j_fs_friendlist.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Friend friend = friend_list.get(i);
                intent_j_fs_managefriend.putExtra("PassedFriend", friend);
                startActivity(intent_j_fs_managefriend);
            }
        });

    }

    private void deleteFriendListener()
    {
        lv_j_fs_friendlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                dbhelper.removeFriendFromDB(friend_list.get(i).getFriendID());
                dbhelper.fillFriendsList(Session.getLoggedInUser(), friend_list);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    private void addFriendListener()
    {
        btn_j_fs_addfriend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(intent_j_fs_addfriend);
            }
        });
    }

    private void sortFriendListener()
    {
        btn_j_fs_sortfriend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                sortFriendsPopup();
            }
        });
    }

    private void homeButtonListener()
    {
        btn_j_fs_home.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Session.setLoggedInUser("");
                startActivity(intent_j_fs_mainactivity);
            }
        });
    }

    private void userInfoListener()
    {
        btn_j_fs_userinfo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder userInfoBuilder = new AlertDialog.Builder(FriendScreen.this);
                userInfoBuilder.setTitle("Your Info");

                View userInfo = getLayoutInflater().inflate(R.layout.custom_cell_user_info_dialog, null);
                userInfoBuilder.setView(userInfo);

                User user = dbhelper.getUserByUsername(Session.getLoggedInUser());
                TextView tv_j_ui_username = userInfo.findViewById(R.id.tv_v_ui_username);
                TextView tv_j_ui_name = userInfo.findViewById(R.id.tv_v_ui_name);
                TextView tv_j_ui_age = userInfo.findViewById(R.id.tv_v_ui_age);
                TextView tv_j_ui_bday = userInfo.findViewById(R.id.tv_v_ui_bday);
                TextView tv_j_ui_numfriend = userInfo.findViewById(R.id.tv_v_ui_numfriend);

                Button btn_j_ui_manage = userInfo.findViewById(R.id.btn_v_ui_manage);
                Button btn_j_ui_delete = userInfo.findViewById(R.id.btn_v_ui_delete);

                Intent intent_j_fs_manageuser = new Intent(FriendScreen.this, ManageUserScreen.class);

                tv_j_ui_username.setText("Username: " + user.getUsername());
                tv_j_ui_name.setText("Name: " + user.getFname() + " " +  user.getLname());
                tv_j_ui_age.setText("Age: " + user.getAge());
                tv_j_ui_bday.setText("Birthday: " + user.getBirthday());
                tv_j_ui_numfriend.setText("# of Friends: " + dbhelper.getNumberOfFriendsForUser(user.getUsername()));

                userInfoBuilder.setNegativeButton("Close", null);
                userInfoBuilder.show();

                btn_j_ui_manage.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        User user = dbhelper.getUserByUsername(Session.getLoggedInUser());
                        intent_j_fs_manageuser.putExtra("PassedUser", user);
                        startActivity(intent_j_fs_manageuser);
                    }
                });

                btn_j_ui_delete.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        AlertDialog.Builder confirmDeleteAlert = new AlertDialog.Builder(FriendScreen.this);
                        confirmDeleteAlert.setTitle("Delete Account?");
                        confirmDeleteAlert.setNegativeButton("No", null);
                        confirmDeleteAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                dbhelper.removeUserFromDB(Session.getLoggedInUser());
                                Session.setLoggedInUser("");
                                startActivity(intent_j_fs_mainactivity);
                            }
                        });
                        confirmDeleteAlert.show();
                    }
                });
            }
        });
    }

    private void sortFriendsPopup()
    {
        AlertDialog.Builder sortFriendBuilder = new AlertDialog.Builder(FriendScreen.this);
        sortFriendBuilder.setTitle("Sort Friends");

        View sortFriends = getLayoutInflater().inflate(R.layout.custom_cell_sort_alert_dialog, null);
        sortFriendBuilder.setView(sortFriends);

        ArrayList<String> gender_list = new ArrayList<>();
        gender_list.add("Male");
        gender_list.add("Female");
        gender_list.add("Other");

        ArrayList<String> closeness_list = new ArrayList<>();
        closeness_list.add("Not Close");
        closeness_list.add("Acquainted");
        closeness_list.add("Friend");
        closeness_list.add("Close Friend");
        closeness_list.add("Best Friend");

        ArrayList<String> comm_type_list = new ArrayList<>();
        dbhelper.fillCommTypeList(comm_type_list);


        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, gender_list);
        ArrayAdapter<String> closenessAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, closeness_list);

        CheckBox cb_j_sf_fname = sortFriends.findViewById(R.id.cb_v_sf_fname);
        CheckBox cb_j_sf_lname = sortFriends.findViewById(R.id.cb_v_sf_lname);
        CheckBox cb_j_sf_gender = sortFriends.findViewById(R.id.cb_v_sf_gender);
        CheckBox cb_j_sf_closeness = sortFriends.findViewById(R.id.cb_v_sf_closeness);
        CheckBox cb_j_sf_marked = sortFriends.findViewById(R.id.cb_v_sf_marked);

        EditText et_j_sf_fname = sortFriends.findViewById(R.id.et_v_sf_fname);
        EditText et_j_sf_lname = sortFriends.findViewById(R.id.et_v_sf_lname);

        Spinner spn_j_sf_gender = sortFriends.findViewById(R.id.spn_v_sf_gender);
        Spinner spn_j_sf_closeness = sortFriends.findViewById(R.id.spn_v_sf_closeness);



        spn_j_sf_gender.setAdapter(genderAdapter);
        spn_j_sf_closeness.setAdapter(closenessAdapter);

        sortFriendBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                String sql_statement = "SELECT * FROM Friendship WHERE 1=1 AND TiedUser = '" + Session.getLoggedInUser() + "'";
                boolean concatQuery = false;

                if (cb_j_sf_fname.isChecked() && !et_j_sf_fname.getText().toString().isEmpty())
                {
                    sql_statement += " AND Fname = '" + et_j_sf_fname.getText().toString() + "'";
                    concatQuery = true;
                }

                if (cb_j_sf_lname.isChecked() && !et_j_sf_lname.getText().toString().isEmpty())
                {
                    sql_statement += " AND Lname = '" + et_j_sf_lname.getText().toString() + "'";
                    concatQuery = true;
                }

                if (cb_j_sf_gender.isChecked())
                {
                    sql_statement += " AND Gender = " + spn_j_sf_gender.getSelectedItemPosition();
                    concatQuery = true;
                }

                if (cb_j_sf_closeness.isChecked())
                {
                    sql_statement += " AND ClosenessLevel = " + spn_j_sf_closeness.getSelectedItemPosition();
                    concatQuery = true;
                }

                if (cb_j_sf_marked.isChecked())
                {
                        sql_statement += " AND IsMarked = 1";
                        concatQuery = true;
                }

                if (concatQuery)
                {
                    // sort with concatenated query, returning specified filters
                    dbhelper.filterFriends(sql_statement, friend_list);
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    // sort with unmodified query string, returning the default friend list
                    dbhelper.filterFriends(sql_statement, friend_list);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(FriendScreen.this, "Default results displayed. Please select one filter.", Toast.LENGTH_LONG).show();
                }
            }
        });

        sortFriendBuilder.setNegativeButton("Cancel", null);
        AlertDialog dialog = sortFriendBuilder.create();
        dialog.show();
    }

    private void loginLapseNotification()
    {
        int markedFriends = dbhelper.getNumOfMarkedFriends(Session.getLoggedInUser());
        AlertDialog.Builder loginLapseBuilder = new AlertDialog.Builder(FriendScreen.this);
        loginLapseBuilder.setTitle("It's been a while!");
        loginLapseBuilder.setMessage("Hey there! It's been 3 days since you've last logged in.\n" +
                "Why not talk to some of the friends you've marked?\n" +
                "You currently have " + markedFriends + " marked friends.");
        loginLapseBuilder.setNegativeButton("Sure!", null);
        loginLapseBuilder.show();
    }
}