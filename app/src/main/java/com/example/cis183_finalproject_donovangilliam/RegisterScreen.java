package com.example.cis183_finalproject_donovangilliam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegisterScreen extends AppCompatActivity {
    DatabaseHelper dbhelper;

    EditText et_j_rs_username;
    EditText et_j_rs_fname;
    EditText et_j_rs_lname;

    DatePicker dp_j_rs_datepicker;

    Button btn_j_rs_register;

    Intent intent_j_rs_main;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_screen);

        et_j_rs_username = findViewById(R.id.et_v_rs_username);
        et_j_rs_fname = findViewById(R.id.et_v_rs_fname);
        et_j_rs_lname = findViewById(R.id.et_v_rs_lname);

        dp_j_rs_datepicker = findViewById(R.id.dp_v_rs_datepicker);

        btn_j_rs_register = findViewById(R.id.btn_v_rs_register);

        intent_j_rs_main = new Intent(RegisterScreen.this, MainActivity.class);

        dbhelper = new DatabaseHelper(this);

        registerConfirmListener();
    }

    public void registerConfirmListener()
    {
        btn_j_rs_register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (!et_j_rs_username.getText().toString().isEmpty()
                        && !et_j_rs_fname.getText().toString().isEmpty()
                        && !et_j_rs_lname.getText().toString().isEmpty()) {
                    if (dbhelper.findUserInDB(et_j_rs_username.getText().toString()))
                    {
                        Toast.makeText(RegisterScreen.this, "User already exists.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(dp_j_rs_datepicker.getYear(),
                                dp_j_rs_datepicker.getMonth(),
                                dp_j_rs_datepicker.getDayOfMonth());
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                        String birthday = sdf.format(calendar.getTime());
                        String currentDate = sdf.format(new Date());

                        Calendar today = Calendar.getInstance();
                        // calc age
                        int age = today.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
                        User user = new User(et_j_rs_username.getText().toString(),
                                et_j_rs_fname.getText().toString(),
                                et_j_rs_lname.getText().toString(),
                                age,
                                birthday,
                                currentDate);
                        dbhelper.addUserToDB(user);
                        Toast.makeText(RegisterScreen.this, "User added.", Toast.LENGTH_SHORT).show();
                        startActivity(intent_j_rs_main);

                    }
                }
            }
        });
    }
}