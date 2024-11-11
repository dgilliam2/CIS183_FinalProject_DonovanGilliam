package com.example.cis183_finalproject_donovangilliam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "AmieO.db";
    private static final String USERS_TABLE_NAME = "Users";
    private static final String FRIENDS_TABLE_NAME = "Friendship";
    private static final String FRIEND_INTERESTS_TABLE_NAME = "FriendInterests";
    private static final String INTERESTS_TABLE_NAME = "Interests";
    private static final String FRIEND_COMM_METHODS_TABLE_NAME = "FriendComms";
    private static final String COMM_METHODS_TABLE_NAME = "CommMethods";
    private static final String COMM_TYPES_TABLE_NAME = "CommTypes";

    //put version up here too so i can change it easier
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context c)
    {
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Setup
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " +
                USERS_TABLE_NAME +
                " (Username varchar(50) PRIMARY KEY, " +
                "Fname varchar(50), " +
                "Lname varchar(50), " +
                "Email varchar(50), " +
                "PhoneNum varchar(25), " +
                "Age integer, " +
                "Birthday date;");
        db.execSQL("CREATE TABLE " +
                FRIENDS_TABLE_NAME +
                " (MajorId integer PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "MajorName varchar(50), " +
                "MajorPrefix varchar(50));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + FRIENDS_TABLE_NAME + ";");

        onCreate(db);
    }

    public void initData()
    {
        //check the table count here so that it only initializes the db if both of these are empty
        //used to prevent it from refilling students when we delete them
        if (countRecordsFromTable(USERS_TABLE_NAME) == 0 && countRecordsFromTable(FRIENDS_TABLE_NAME) == 0)
        {
            initStudents();
            initMajors();
        }
    }

    private void initStudents()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO " + USERS_TABLE_NAME +
                "(Username," +
                "Fname, " +
                "Lname, " +
                "Email, " +
                "Age, " +
                "GPA, " +
                "MajorId) VALUES ('jmahn2','John', 'Mahn', 'jman@gmail.com', 19, 4.0, 1);");
        db.execSQL("INSERT INTO " + USERS_TABLE_NAME +
                "(Username," +
                "Fname, " +
                "Lname, " +
                "Email, " +
                "Age, " +
                "GPA, " +
                "MajorId) VALUES ('ahy3','Alex', 'Hyde', 'ahyde@gmail.com', 21, 2.7, 2);");
        db.execSQL("INSERT INTO " + USERS_TABLE_NAME +
                "(Username, " +
                "Fname, " +
                "Lname, " +
                "Email, " +
                "Age, " +
                "GPA, " +
                "MajorId) VALUES ('mc20mc','Matt', 'Cuda', 'mcuda@gmail.com', 20, 3.3, 3);");

        //close the database
        db.close();
    }

    private void initMajors()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO " + FRIENDS_TABLE_NAME +
                "(MajorId, " +
                "MajorName, " +
                "MajorPrefix) VALUES (1, 'App Development', 'CIS');");
        db.execSQL("INSERT INTO " + FRIENDS_TABLE_NAME +
                "(MajorId, " +
                "MajorName, " +
                "MajorPrefix) VALUES (2, 'General Psychology', 'PSYCH');");
        db.execSQL("INSERT INTO " + FRIENDS_TABLE_NAME +
                "(MajorId, " +
                "MajorName, " +
                "MajorPrefix) VALUES (3, 'Information Security', 'CIA');");

        db.close();
    }

    public int countRecordsFromTable(String tableName)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, tableName);
        db.close();
        return numRows;
    }
}