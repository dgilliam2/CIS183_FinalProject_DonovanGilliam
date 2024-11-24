package com.example.cis183_finalproject_donovangilliam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity
{
    EditText et_j_m_username;

    Button btn_j_m_login;
    Button btn_j_m_register;

    Intent intent_j_m_friendlist;
    DatabaseHelper dbhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        et_j_m_username = findViewById(R.id.et_v_m_username);

        btn_j_m_login = findViewById(R.id.btn_v_m_login);
        btn_j_m_register = findViewById(R.id.btn_v_m_register);

        intent_j_m_friendlist = new Intent(MainActivity.this, FriendScreen.class);

        dbhelper = new DatabaseHelper(this);

        dbhelper.initFunc();
        loginListener();
    }

    private void loginListener()
    {
        btn_j_m_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (dbhelper.findUserInDB(et_j_m_username.getText().toString()))
                {
                    Session.setLoggedInUser(et_j_m_username.getText().toString());
                    startActivity(intent_j_m_friendlist);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}