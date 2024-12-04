package com.example.cis183_finalproject_donovangilliam;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
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


    // TO-DO: ADD DIALOG BOX THAT WILL SHOW UP AFTER FRIEND HAS BEEN ADDED. USER WILL BE ABLE TO
    // ENTER UP TO THREE COMM METHODS THEY WANT TO ADD TO THE FRIEND.
    // DONE THIS WAY BECAUSE I CANNOT FIGURE OUT HOW TO ADD COMM METHODS AND THE FRIEND AT THE SAME
    // TIME, AS COMM METHODS DEPEND ON FRIEND ID, WHICH IS AUTO INCREMENTED.
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_friend_screen);

    }
}