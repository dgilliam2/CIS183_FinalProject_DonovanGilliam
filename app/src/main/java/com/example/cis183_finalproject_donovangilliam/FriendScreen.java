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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FriendScreen extends AppCompatActivity
{
    Button btn_j_fs_addfriend;
    Button btn_j_fs_sortfriend;
    Button btn_j_fs_home;


    ListView lv_j_fs_friendlist;

    Intent intent_j_fs_mainactivity;
    Intent intent_j_fs_addfriend;
    Intent intent_j_fs_managefriend;

    DatabaseHelper dbhelper;
    ArrayList<Friend> friend_list = new ArrayList<>();
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


        lv_j_fs_friendlist = findViewById(R.id.lv_v_fs_friendlist);
        lv_j_fs_friendlist.setAdapter(adapter);


        intent_j_fs_mainactivity = new Intent(FriendScreen.this, MainActivity.class);
        intent_j_fs_addfriend = new Intent(FriendScreen.this, AddFriendScreen.class);
        intent_j_fs_managefriend = new Intent(FriendScreen.this, ManageFriendScreen.class);

        //ADD CHECK TO SEE IF ACTIVITY STARTED FROM LOGIN SCREEN, ONLY PUSH NOTIFICATIONS IF COMING
        // FROM THIS SCREEN
        Intent cameFrom = getIntent();
        Bundle infoPassed = cameFrom.getExtras();

        dbhelper = new DatabaseHelper(FriendScreen.this);

        dbhelper.fillFriendsList(Session.getLoggedInUser(), friend_list);
        if (dbhelper.lastLoginThreeDaysElapsed(Session.getLoggedInUser()))
        {
            Session.setLoginLapseNotification(true);
            dbhelper.updateLastLogin(Session.getLoggedInUser());
        }
        else
        {
            dbhelper.updateLastLogin(Session.getLoggedInUser());
        }

        manageFriendListener();
        deleteFriendListener();
        addFriendListener();
        homeButtonListener();
        sortFriendListener();
    }

    private void manageFriendListener()
    {
        lv_j_fs_friendlist.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {

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
                startActivity(intent_j_fs_mainactivity);
            }
        });
    }

    private void sortFriendsPopup()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(FriendScreen.this);
        builder.setTitle("Sort Friends");

        View view = getLayoutInflater().inflate(R.layout.custom_cell_sort_alert_dialog, null);
        builder.setView(view);

        CheckBox cb_j_sf_fname = view.findViewById(R.id.cb_v_sf_fname);
        CheckBox cb_j_sf_lname = view.findViewById(R.id.cb_v_sf_lname);
        CheckBox cb_j_sf_gender = view.findViewById(R.id.cb_v_sf_gender);
        CheckBox cb_j_sf_closeness = view.findViewById(R.id.cb_v_sf_closeness);
        CheckBox cb_j_sf_marked = view.findViewById(R.id.cb_v_sf_marked);

        EditText et_j_sf_fname = view.findViewById(R.id.et_v_sf_fname);
        EditText et_j_sf_lname = view.findViewById(R.id.et_v_sf_lname);

        Spinner spn_j_sf_gender = view.findViewById(R.id.spn_v_sf_gender);
        Spinner spn_j_sf_closeness = view.findViewById(R.id.spn_v_sf_closeness);

        ArrayList<String> genderList = new ArrayList<>();
        genderList.add("Male");
        genderList.add("Female");
        genderList.add("Other");
        ArrayList<String> closenessList = new ArrayList<>();
        closenessList.add("Not Close");
        closenessList.add("Acquainted");
        closenessList.add("Friend");
        closenessList.add("Close Friend");
        closenessList.add("Best Friend");



        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genderList);
        ArrayAdapter<String> closenessAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, closenessList);

        spn_j_sf_gender.setAdapter(genderAdapter);
        spn_j_sf_closeness.setAdapter(closenessAdapter);


        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener()
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
                    Log.d("Gender Sorting", "Selected Gender: " + spn_j_sf_gender.getSelectedItem().toString());
                    Log.d("Gender Sorting", "Selected Gender Index: " + spn_j_sf_gender.getSelectedItemPosition());
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

        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}