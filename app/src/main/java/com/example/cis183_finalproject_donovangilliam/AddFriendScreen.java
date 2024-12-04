package com.example.cis183_finalproject_donovangilliam;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddFriendScreen extends AppCompatActivity
{
    ArrayList<CommMethod> comm_method_list = new ArrayList<>();
    DatabaseHelper dbhelper;

    EditText et_j_af_fname;
    EditText et_j_af_lname;
    EditText et_j_af_email;
    EditText et_j_af_phonenum;

    Spinner spn_j_af_gender;
    Spinner spn_j_af_closeness;

    DatePicker dp_j_af_datepicker;

    Button btn_j_af_addcomm;

    CheckBox cb_j_af_marked;

    // add friend to DB, then prompt user to add comm method info if they want via an alertdialog box
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_friend_screen);


        et_j_af_email = findViewById(R.id.et_v_af_email);
        et_j_af_fname = findViewById(R.id.et_v_af_fname);
        et_j_af_lname = findViewById(R.id.et_v_af_lname);
        et_j_af_phonenum = findViewById(R.id.et_v_af_phonenum);

        spn_j_af_gender = findViewById(R.id.spn_v_af_gender);
        spn_j_af_closeness = findViewById(R.id.spn_v_af_closeness);

        dp_j_af_datepicker = findViewById(R.id.dp_v_af_datepicker);

        btn_j_af_addcomm = findViewById(R.id.btn_v_af_addfriend);

        cb_j_af_marked = findViewById(R.id.cb_v_af_marked);


    }

    private void addFriendButtonListener()
    {
        btn_j_af_addcomm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddFriendScreen.this);

            }
        });
    }
}