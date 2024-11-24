package com.example.cis183_finalproject_donovangilliam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class FriendScreen extends AppCompatActivity
{
    Button btn_j_fs_addfriend;

    ListView lv_j_fs_friendlist;

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

        lv_j_fs_friendlist = findViewById(R.id.lv_v_fs_friendlist);
        lv_j_fs_friendlist.setAdapter(adapter);
        intent_j_fs_addfriend = new Intent(FriendScreen.this, AddFriendScreen.class);
        intent_j_fs_managefriend = new Intent(FriendScreen.this, ManageFriendScreen.class);

        dbhelper = new DatabaseHelper(FriendScreen.this);
        dbhelper.fillFriendsList(Session.getLoggedInUser(), friend_list);
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

            }
        });
    }
}