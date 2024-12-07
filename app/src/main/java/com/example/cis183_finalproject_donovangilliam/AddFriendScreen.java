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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddFriendScreen extends AppCompatActivity
{
    DatabaseHelper dbhelper;

    EditText et_j_af_fname;
    EditText et_j_af_lname;
    EditText et_j_af_email;
    EditText et_j_af_phonenum;

    Spinner spn_j_af_gender;
    Spinner spn_j_af_closeness;

    DatePicker dp_j_af_datepicker;

    Button btn_j_af_confirm;

    CheckBox cb_j_af_marked;

    Intent intent_j_af_friendscreen;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_friend_screen);

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

        et_j_af_email = findViewById(R.id.et_v_af_email);
        et_j_af_fname = findViewById(R.id.et_v_af_fname);
        et_j_af_lname = findViewById(R.id.et_v_af_lname);
        et_j_af_phonenum = findViewById(R.id.et_v_af_phonenum);

        spn_j_af_gender = findViewById(R.id.spn_v_af_gender);
        spn_j_af_closeness = findViewById(R.id.spn_v_af_closeness);

        dp_j_af_datepicker = findViewById(R.id.dp_v_af_datepicker);

        btn_j_af_confirm = findViewById(R.id.btn_v_af_confirm);

        cb_j_af_marked = findViewById(R.id.cb_v_af_marked);

        intent_j_af_friendscreen = new Intent(AddFriendScreen.this, FriendScreen.class);

        spn_j_af_gender.setAdapter(genderAdapter);
        spn_j_af_closeness.setAdapter(closenessAdapter);

        dbhelper = new DatabaseHelper(this);

        addFriendButtonListener();
    }

    private void addFriendButtonListener()
    {
        btn_j_af_confirm.setOnClickListener(new View.OnClickListener()
        {
            // aaaaaaaaaaaaaaaaaaaaaaaaa
            @Override
            public void onClick(View view)
            {
                if (!et_j_af_fname.getText().toString().isEmpty()
                        && !et_j_af_lname.getText().toString().isEmpty()
                        && !et_j_af_email.getText().toString().isEmpty()
                        && !et_j_af_phonenum.getText().toString().isEmpty())
                {
                    addFriendPrompt();
                }
                else
                {
                    Toast.makeText(AddFriendScreen.this, "Please fill out all fields", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // add friend to DB, then prompt user to add interests if they want via
    // an alertdialog box
    private void addFriendPrompt()
    {
        AlertDialog.Builder addFriendBuilder = new AlertDialog.Builder(AddFriendScreen.this);
        addFriendBuilder.setTitle("Confirm Addition");
        addFriendBuilder.setMessage("Are you sure you would like to add this friend?");
        addFriendBuilder.setNegativeButton("No", null);
        addFriendBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                if (!et_j_af_fname.getText().toString().isEmpty()
                        && !et_j_af_lname.getText().toString().isEmpty()
                        && !et_j_af_email.getText().toString().isEmpty()
                        && !et_j_af_phonenum.getText().toString().isEmpty())
                {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(dp_j_af_datepicker.getYear(),
                            dp_j_af_datepicker.getMonth(),
                            dp_j_af_datepicker.getDayOfMonth());
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    String birthday = sdf.format(calendar.getTime());

                    Calendar today = Calendar.getInstance();
                    // calc age
                    int age = today.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);

                    Friend friend = new Friend(et_j_af_fname.getText().toString(),
                            et_j_af_lname.getText().toString(),
                            et_j_af_email.getText().toString(),
                            spn_j_af_gender.getSelectedItemPosition(),
                            age,
                            birthday,
                            et_j_af_phonenum.getText().toString(),
                            spn_j_af_closeness.getSelectedItemPosition(),
                            Session.getLoggedInUser(),
                            // convert boolean to int
                            // true result = 1, false result = 0
                            cb_j_af_marked.isChecked() ? 1 : 0
                    );
                    dbhelper.addFriendToDB(friend);
                    interestPrompt();
                }
            }
        });
        addFriendBuilder.show();
    }

    private void interestPrompt()
    {
        AlertDialog.Builder addInterestBuilder = new AlertDialog.Builder(AddFriendScreen.this);
        addInterestBuilder.setTitle("Add Interests");
        addInterestBuilder.setMessage("Friend added. Please add interests for this friend.");
        addInterestBuilder.setPositiveButton("Continue", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                addInterestView();
            }
        });
        addInterestBuilder.show();
    }

    private void commMethodPrompt()
    {
        AlertDialog.Builder addCommsBuilder = new AlertDialog.Builder(AddFriendScreen.this);
        addCommsBuilder.setTitle("Add Communication Methods");
        addCommsBuilder.setMessage("Please add communication methods for this friend.");
        addCommsBuilder.setPositiveButton("Continue", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                commMethodView();
            }
        });
        addCommsBuilder.show();
    }

    private void addInterestView()
    {
        AlertDialog.Builder interestViewBuilder = new AlertDialog.Builder(AddFriendScreen.this);
        View view = getLayoutInflater().inflate(R.layout.custom_cell_add_interests_dialog, null);

        EditText et_j_aid_interest1 = view.findViewById(R.id.et_v_aid_interest1);
        EditText et_j_aid_interest2 = view.findViewById(R.id.et_v_aid_interest2);
        EditText et_j_aid_interest3 = view.findViewById(R.id.et_v_aid_interest3);

        interestViewBuilder.setTitle("Add Interests");
        interestViewBuilder.setView(view);

        interestViewBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                int friendID = dbhelper.getMostRecentFriendIDForUser(Session.getLoggedInUser());

                if (!et_j_aid_interest1.getText().toString().isEmpty())
                {
                    Interest interest1 = new Interest(friendID, et_j_aid_interest1.getText().toString());
                    dbhelper.addInterestToDB(interest1);
                }

                if (!et_j_aid_interest2.getText().toString().isEmpty())
                {
                    Interest interest2 = new Interest(friendID, et_j_aid_interest2.getText().toString());
                    dbhelper.addInterestToDB(interest2);
                }

                if (!et_j_aid_interest3.getText().toString().isEmpty())
                {
                    Interest interest3 = new Interest(friendID, et_j_aid_interest3.getText().toString());
                    dbhelper.addInterestToDB(interest3);
                }
                commMethodPrompt();
            }
        });

        interestViewBuilder.show();
    }

    private void commMethodView()
    {
        ArrayList<String> comm_type_list = new ArrayList<>();
        dbhelper.fillCommTypeList(comm_type_list);

        AlertDialog.Builder commMethodViewBuilder = new AlertDialog.Builder(AddFriendScreen.this);
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

        commMethodViewBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                int friendID = dbhelper.getMostRecentFriendIDForUser(Session.getLoggedInUser());

                if (!et_j_acm_comm1.getText().toString().isEmpty())
                {
                    CommMethod commMethod1 = new CommMethod(
                            et_j_acm_comm1.getText().toString(),
                            comm_type_list.indexOf(spn_j_acm_type1.getSelectedItem().toString()) + 1
                    );
                    dbhelper.addCommMethodToDB(commMethod1);
                    dbhelper.addFriendCommMethod(friendID, commMethod1);
                }

                if (!et_j_acm_comm2.getText().toString().isEmpty())
                {
                    CommMethod commMethod2 = new CommMethod(
                            et_j_acm_comm2.getText().toString(),
                            comm_type_list.indexOf(spn_j_acm_type2.getSelectedItem().toString()) + 1
                    );
                    dbhelper.addCommMethodToDB(commMethod2);
                    dbhelper.addFriendCommMethod(friendID, commMethod2);
                }

                if (!et_j_acm_comm3.getText().toString().isEmpty())
                {
                    CommMethod commMethod3 = new CommMethod(
                            et_j_acm_comm3.getText().toString(),
                            comm_type_list.indexOf(spn_j_acm_type3.getSelectedItem().toString()) + 1
                    );
                    dbhelper.addCommMethodToDB(commMethod3);
                    dbhelper.addFriendCommMethod(friendID, commMethod3);
                }
                Toast.makeText(AddFriendScreen.this,"Friend successfully added", Toast.LENGTH_LONG).show();
                startActivity(intent_j_af_friendscreen);
            }
        });
        commMethodViewBuilder.show();
    }
}