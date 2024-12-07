package com.example.cis183_finalproject_donovangilliam;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ManageFriendScreen extends AppCompatActivity {

    DatabaseHelper dbhelper;

    EditText et_j_mf_fname;
    EditText et_j_mf_lname;
    EditText et_j_mf_email;
    EditText et_j_mf_phonenum;

    Spinner spn_j_mf_gender;
    Spinner spn_j_mf_closeness;

    DatePicker dp_j_mf_datepicker;

    Button btn_j_mf_confirm;

    CheckBox cb_j_mf_marked;

    Intent intent_j_mf_friendscreen;

    private Friend passedFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_friend_screen);

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

        et_j_mf_fname = findViewById(R.id.et_v_mf_fname);
        et_j_mf_lname = findViewById(R.id.et_v_mf_lname);
        et_j_mf_email = findViewById(R.id.et_v_mf_email);
        et_j_mf_phonenum = findViewById(R.id.et_v_mf_phonenum);

        spn_j_mf_gender = findViewById(R.id.spn_v_mf_gender);
        spn_j_mf_closeness = findViewById(R.id.spn_v_mf_closeness);

        dp_j_mf_datepicker = findViewById(R.id.dp_v_mf_datepicker);

        btn_j_mf_confirm = findViewById(R.id.btn_v_mf_confirm);

        cb_j_mf_marked = findViewById(R.id.cb_v_mf_marked);
        spn_j_mf_gender.setAdapter(genderAdapter);
        spn_j_mf_closeness.setAdapter(closenessAdapter);

        dbhelper = new DatabaseHelper(this);

        intent_j_mf_friendscreen = new Intent(ManageFriendScreen.this, FriendScreen.class);
        Intent cameFrom = getIntent();

        if (cameFrom.getSerializableExtra("PassedFriend") != null) {
            passedFriend = (Friend) cameFrom.getSerializableExtra("PassedFriend");

            et_j_mf_fname.setText(passedFriend.getFname());
            et_j_mf_lname.setText(passedFriend.getLname());
            et_j_mf_email.setText(passedFriend.getEmail());
            et_j_mf_phonenum.setText(passedFriend.getPhoneNum());

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

            Date birthday;
            Calendar calendar = Calendar.getInstance();
            try {
                birthday = sdf.parse(passedFriend.getBirthday());
                calendar.setTime(birthday);
                dp_j_mf_datepicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            int gender = passedFriend.getGender();
            spn_j_mf_gender.setSelection(gender);

            int closeness = passedFriend.getClosenessLevel();
            spn_j_mf_closeness.setSelection(closeness);

            boolean checked = (passedFriend.isMarked() == 1);
            cb_j_mf_marked.setChecked(checked);

            confirmEditListener();
        }
    }

    private void confirmEditListener() {
        btn_j_mf_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmEditPrompt();
            }
        });
    }

    private void confirmEditPrompt() {
        AlertDialog.Builder editFriendBuilder = new AlertDialog.Builder(ManageFriendScreen.this);
        editFriendBuilder.setTitle("Confirm Edit");
        editFriendBuilder.setMessage("Are you sure you would like to update this friend?");
        editFriendBuilder.setNegativeButton("No", null);
        editFriendBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!et_j_mf_fname.getText().toString().isEmpty()
                        && !et_j_mf_lname.getText().toString().isEmpty()
                        && !et_j_mf_email.getText().toString().isEmpty()
                        && !et_j_mf_phonenum.getText().toString().isEmpty()) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(dp_j_mf_datepicker.getYear(),
                            dp_j_mf_datepicker.getMonth(),
                            dp_j_mf_datepicker.getDayOfMonth());
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    String birthday = sdf.format(calendar.getTime());

                    Calendar today = Calendar.getInstance();
                    // calc age
                    int age = today.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);

                    Friend friend = new Friend(passedFriend.getFriendID(), et_j_mf_fname.getText().toString(),
                            et_j_mf_lname.getText().toString(),
                            et_j_mf_email.getText().toString(),
                            spn_j_mf_gender.getSelectedItemPosition(),
                            age,
                            birthday,
                            et_j_mf_phonenum.getText().toString(),
                            spn_j_mf_closeness.getSelectedItemPosition(),
                            Session.getLoggedInUser(),
                            // convert boolean to int
                            // true result = 1, false result = 0
                            cb_j_mf_marked.isChecked() ? 1 : 0
                    );
                    dbhelper.updateFriendInDB(friend);
                    interestPrompt();
                }
            }
        });
        editFriendBuilder.show();
    }

    private void interestPrompt() {
        AlertDialog.Builder addInterestBuilder = new AlertDialog.Builder(ManageFriendScreen.this);
        addInterestBuilder.setTitle("Update Interests?");
        addInterestBuilder.setMessage("Friend updated. Would you like to update interests for this friend?");
        addInterestBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                //startActivity(intent_j_mf_friendscreen);
                commMethodPrompt();
            }
        });
        addInterestBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                editInterestView();
            }
        });
        addInterestBuilder.show();
    }

    private void commMethodPrompt()
    {
        AlertDialog.Builder addCommsBuilder = new AlertDialog.Builder(ManageFriendScreen.this);
        addCommsBuilder.setTitle("Update Communication Methods?");
        addCommsBuilder.setMessage("Would you like to update communication methods for this friend?");
        addCommsBuilder.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                startActivity(intent_j_mf_friendscreen);
            }
        });
        addCommsBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                commMethodView();
            }
        });
        addCommsBuilder.show();
    }

    private void editInterestView() {
        AlertDialog.Builder interestViewBuilder = new AlertDialog.Builder(ManageFriendScreen.this);
        View view = getLayoutInflater().inflate(R.layout.custom_cell_add_interests_dialog, null);

        EditText et_j_aid_interest1 = view.findViewById(R.id.et_v_aid_interest1);
        EditText et_j_aid_interest2 = view.findViewById(R.id.et_v_aid_interest2);
        EditText et_j_aid_interest3 = view.findViewById(R.id.et_v_aid_interest3);

        interestViewBuilder.setTitle("Update Interests");
        interestViewBuilder.setView(view);

        int friendID = passedFriend.getFriendID();
        ArrayList<Interest> interest_list;
        interest_list = dbhelper.getInterestsByID(friendID);

        // redo this maybe, clunky but works, idk
        if (interest_list.size() > 0) {
            et_j_aid_interest1.setText(interest_list.get(0).getInterestName());
        } else {
            et_j_aid_interest1.setText("");
        }
        if (interest_list.size() > 1) {
            et_j_aid_interest2.setText(interest_list.get(1).getInterestName());
        } else {
            et_j_aid_interest2.setText("");
        }
        if (interest_list.size() > 2) {
            et_j_aid_interest3.setText(interest_list.get(2).getInterestName());
        } else {
            et_j_aid_interest3.setText("");
        }

        interestViewBuilder.setNegativeButton("Cancel", null);

        interestViewBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!et_j_aid_interest1.getText().toString().isEmpty()) {
                    if (interest_list.size() > 0) {
                        Interest interest1 = new Interest(interest_list.get(0).getInterestID(),
                                friendID,
                                et_j_aid_interest1.getText().toString());
                        dbhelper.updateInterestInDB(interest1);
                    } else {
                        Interest interest1 = new Interest(friendID,
                                et_j_aid_interest1.getText().toString());
                        dbhelper.addInterestToDB(interest1);
                    }
                }

                if (!et_j_aid_interest2.getText().toString().isEmpty()) {
                    if (interest_list.size() > 1) {
                        Interest interest2 = new Interest(interest_list.get(1).getInterestID(),
                                friendID,
                                et_j_aid_interest2.getText().toString());
                        dbhelper.updateInterestInDB(interest2);
                    } else {
                        Interest interest2 = new Interest(friendID,
                                et_j_aid_interest2.getText().toString());
                        dbhelper.addInterestToDB(interest2);
                    }
                }

                if (!et_j_aid_interest3.getText().toString().isEmpty()) {
                    if (interest_list.size() > 2) {
                        Interest interest3 = new Interest(interest_list.get(2).getInterestID(),
                                friendID,
                                et_j_aid_interest3.getText().toString());
                        dbhelper.updateInterestInDB(interest3);
                    } else {
                        Interest interest3 = new Interest(friendID,
                                et_j_aid_interest3.getText().toString());
                        dbhelper.addInterestToDB(interest3);
                    }
                }
                //startActivity(intent_j_mf_friendscreen);
                commMethodPrompt();

            }
        });

        interestViewBuilder.show();
    }


    private void commMethodView() {
        ArrayList<String> comm_type_list = new ArrayList<>();
        dbhelper.fillCommTypeList(comm_type_list);

        AlertDialog.Builder commMethodViewBuilder = new AlertDialog.Builder(ManageFriendScreen.this);
        View view = getLayoutInflater().inflate(R.layout.custom_cell_add_comm_methods_dialog, null);

        EditText et_j_acm_comm1 = view.findViewById(R.id.et_v_aid_interest1);
        EditText et_j_acm_comm2 = view.findViewById(R.id.et_v_aid_interest2);
        EditText et_j_acm_comm3 = view.findViewById(R.id.et_v_aid_interest3);

        Spinner spn_j_acm_type1 = view.findViewById(R.id.spn_v_acm_type1);
        Spinner spn_j_acm_type2 = view.findViewById(R.id.spn_v_acm_type2);
        Spinner spn_j_acm_type3 = view.findViewById(R.id.spn_v_acm_type3);

        ArrayAdapter<String> commTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, comm_type_list);
        spn_j_acm_type1.setAdapter(commTypeAdapter);
        spn_j_acm_type2.setAdapter(commTypeAdapter);
        spn_j_acm_type3.setAdapter(commTypeAdapter);

        commMethodViewBuilder.setTitle("Add Communication Methods");
        commMethodViewBuilder.setView(view);

        int friendID = passedFriend.getFriendID();
        ArrayList<CommMethod> comm_method_list;
        comm_method_list = dbhelper.getCommMethodsByID(friendID);

        //would implement setting dropdown, but not sure how to write query and running out of time

        if (comm_method_list.size() > 0) {
            et_j_acm_comm1.setText(comm_method_list.get(0).getCommMethodName());
        } else {
            et_j_acm_comm1.setText("");
        }
        if (comm_method_list.size() > 1) {
            et_j_acm_comm2.setText(comm_method_list.get(1).getCommMethodName());
        } else {
            et_j_acm_comm2.setText("");
        }
        if (comm_method_list.size() > 2) {
            et_j_acm_comm3.setText(comm_method_list.get(2).getCommMethodName());
        } else {
            et_j_acm_comm3.setText("");
        }

        commMethodViewBuilder.setNegativeButton("Cancel", null);
        commMethodViewBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (!et_j_acm_comm1.getText().toString().isEmpty())
                {
                    if (comm_method_list.size() > 0)
                    {
                        CommMethod commMethod1 = new CommMethod(comm_method_list.get(0).getCommMethodID(),
                                et_j_acm_comm1.getText().toString(),
                                comm_type_list.indexOf(spn_j_acm_type1.getSelectedItem().toString()) + 1
                        );
                        dbhelper.updateCommMethodInDB(commMethod1);
                        dbhelper.updateFriendCommMethod(friendID, commMethod1);
                    }
                    else
                    {
                        CommMethod commMethod1 = new CommMethod(et_j_acm_comm1.getText().toString(),
                                comm_type_list.indexOf(spn_j_acm_type1.getSelectedItem().toString()) + 1
                        );
                        dbhelper.addCommMethodToDB(commMethod1);
                        dbhelper.addFriendCommMethod(friendID, commMethod1);
                    }
                }

                if (!et_j_acm_comm2.getText().toString().isEmpty())
                {
                    if (comm_method_list.size() > 1)
                    {

                        CommMethod commMethod2 = new CommMethod(comm_method_list.get(1).getCommMethodID(),
                                et_j_acm_comm2.getText().toString(),
                                comm_type_list.indexOf(spn_j_acm_type2.getSelectedItem().toString()) + 1
                        );
                        dbhelper.updateCommMethodInDB(commMethod2);
                        dbhelper.updateFriendCommMethod(friendID, commMethod2);
                    }
                    else
                    {
                        CommMethod commMethod2 = new CommMethod(et_j_acm_comm2.getText().toString(),
                                comm_type_list.indexOf(spn_j_acm_type2.getSelectedItem().toString()) + 1
                        );
                        dbhelper.addCommMethodToDB(commMethod2);
                        dbhelper.addFriendCommMethod(friendID, commMethod2);
                    }
                }

                if (!et_j_acm_comm3.getText().toString().isEmpty())
                {
                    if (comm_method_list.size() > 2)
                    {
                        CommMethod commMethod3 = new CommMethod(comm_method_list.get(2).getCommMethodID(),
                                et_j_acm_comm3.getText().toString(),
                                comm_type_list.indexOf(spn_j_acm_type3.getSelectedItem().toString()) + 1
                        );
                        dbhelper.updateCommMethodInDB(commMethod3);
                        dbhelper.updateFriendCommMethod(friendID, commMethod3);
                    }
                    else
                    {
                        CommMethod commMethod3 = new CommMethod(et_j_acm_comm3.getText().toString(),
                                comm_type_list.indexOf(spn_j_acm_type3.getSelectedItem().toString()) + 1
                        );
                        dbhelper.addCommMethodToDB(commMethod3);
                        dbhelper.addFriendCommMethod(friendID, commMethod3);
                    }
                }

                startActivity(intent_j_mf_friendscreen);
            }
        });
        commMethodViewBuilder.show();
    }
}