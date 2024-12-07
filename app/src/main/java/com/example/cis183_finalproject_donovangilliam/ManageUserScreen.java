
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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ManageUserScreen extends AppCompatActivity {
    DatabaseHelper dbhelper;

    EditText et_j_mu_fname;
    EditText et_j_mu_lname;

    DatePicker dp_j_mu_datepicker;

    Button btn_j_mu_confirm;

    Intent intent_j_mu_friendscreen;

    private User passedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_user_screen);

        et_j_mu_fname = findViewById(R.id.et_v_mu_fname);
        et_j_mu_lname = findViewById(R.id.et_v_mu_lname);

        dp_j_mu_datepicker = findViewById(R.id.dp_v_mu_datepicker);

        btn_j_mu_confirm = findViewById(R.id.btn_v_mu_register);

        intent_j_mu_friendscreen = new Intent(ManageUserScreen.this, FriendScreen.class);

        dbhelper = new DatabaseHelper(this);
        Intent cameFrom = getIntent();

        if (cameFrom.getSerializableExtra("PassedUser") != null)
        {
            passedUser = (User) cameFrom.getSerializableExtra("PassedUser");

            et_j_mu_fname.setText(passedUser.getFname());
            et_j_mu_lname.setText(passedUser.getLname());
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

            Date birthday;
            Calendar calendar = Calendar.getInstance();
            try
            {
                birthday = sdf.parse(passedUser.getBirthday());
                calendar.setTime(birthday);
                dp_j_mu_datepicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }

        confirmEditListener();
    }

    public void confirmEditListener()
    {
        btn_j_mu_confirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (!et_j_mu_fname.getText().toString().isEmpty()
                        && !et_j_mu_lname.getText().toString().isEmpty())
                {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(dp_j_mu_datepicker.getYear(),
                            dp_j_mu_datepicker.getMonth(),
                            dp_j_mu_datepicker.getDayOfMonth());
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    String birthday = sdf.format(calendar.getTime());
                    String currentDate = sdf.format(new Date());

                    Calendar today = Calendar.getInstance();
                    // calc age
                    int age = today.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
                    User user = new User(Session.getLoggedInUser(),
                            et_j_mu_fname.getText().toString(),
                            et_j_mu_lname.getText().toString(),
                            age,
                            birthday,
                            currentDate);
                    dbhelper.updateUserInDB(user);
                    Toast.makeText(ManageUserScreen.this, "User added.", Toast.LENGTH_SHORT).show();
                    startActivity(intent_j_mu_friendscreen);
                }
            }
        });
    }
}