package com.example.cis183_finalproject_donovangilliam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    private static final int DATABASE_VERSION = 10;

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
                "Age integer, " +
                "Birthday date);");

        //Gender: 0 - Male 1 - Female 2 - Other
        db.execSQL("CREATE TABLE " +
                FRIENDS_TABLE_NAME +
                " (FriendID integer PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "Fname varchar(50), " +
                "Lname varchar(50), " +
                "Gender integer, " +
                "Email varchar(50), " +
                "Age integer, " +
                "Birthday varchar(50), " +
                "PhoneNum varchar(50), " +
                "ClosenessLevel integer NOT NULL, " +
                "CommMethod integer, " +
                "TiedUser varchar(50), " +
                "FOREIGN KEY (TiedUser) REFERENCES " +
                USERS_TABLE_NAME +
                " (Username));");

        db.execSQL("CREATE TABLE " +
                FRIEND_INTERESTS_TABLE_NAME +
                " (FriendInterestID integer PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "InterestID integer, " +
                "FOREIGN KEY (FriendInterestID) REFERENCES " +
                FRIENDS_TABLE_NAME +
                " (FriendID), " +
                "FOREIGN KEY (InterestID) REFERENCES " +
                INTERESTS_TABLE_NAME +
                " (InterestID));");

        db.execSQL("CREATE TABLE " +
                INTERESTS_TABLE_NAME +
                " (InterestID integer PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "InterestName varchar(50));");

        db.execSQL("CREATE TABLE " +
                FRIEND_COMM_METHODS_TABLE_NAME +
                " (FriendCommMethodID integer PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "FriendCommID integer, " +
                "CommMethodID integer, " +
                "FOREIGN KEY (FriendCommID) REFERENCES " +
                FRIENDS_TABLE_NAME +
                " (FriendID), " +
                "FOREIGN KEY (CommMethodID) REFERENCES " +
                COMM_METHODS_TABLE_NAME +
                " (CommMethodID));");

        db.execSQL("CREATE TABLE " +
                COMM_METHODS_TABLE_NAME +
                " (CommMethodID integer PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "CommMethodName varchar(50), " +
                "CommTypeID integer, " +
                "FOREIGN KEY (CommTypeID) REFERENCES " +
                COMM_TYPES_TABLE_NAME +
                " (CommTypeID));");

        db.execSQL("CREATE TABLE " +
                COMM_TYPES_TABLE_NAME +
                " (CommTypeID integer PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "CommTypeName varchar(50));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + FRIENDS_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + FRIEND_INTERESTS_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + INTERESTS_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + FRIEND_COMM_METHODS_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + COMM_METHODS_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + COMM_TYPES_TABLE_NAME + ";");



        onCreate(db);
    }

    public void initFunc()
    {
        //check the table count here so that it only initializes the db if both of these are empty
        //used to prevent it from refilling if friends are deleted, since we won't allow users to be deleted
        if (countRecordsFromTable(USERS_TABLE_NAME) == 0 && countRecordsFromTable(FRIENDS_TABLE_NAME) == 0)
        {
            initData();
        }
    }

    private void initData()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("INSERT INTO " + USERS_TABLE_NAME +
                "(Username," +
                "Fname, " +
                "Lname, " +
                "Age, " +
                "Birthday) VALUES ('jmahn2','John', 'Mahn', 21, '1/07/2003');");
        db.execSQL("INSERT INTO " + USERS_TABLE_NAME +
                "(Username," +
                "Fname, " +
                "Lname, " +
                "Age, " +
                "Birthday) VALUES ('ioi59','Issac', 'Ingham', 26, '6/14/1998');");

        db.execSQL("INSERT INTO " + FRIENDS_TABLE_NAME +
                " (Fname, " +
                "Lname, " +
                "Email, " +
                "Gender, " +
                "Age, " +
                "Birthday, " +
                "PhoneNum, " +
                "ClosenessLevel, " +
                "CommMethod, " +
                "TiedUser) VALUES ('Emile', 'Smile', 0, 'emiles@example.com', 22, '6/20/2002', '1-734-048-1732', 4, 2, 'jmahn2');");
        db.execSQL("INSERT INTO " + FRIENDS_TABLE_NAME +
                " (Fname, " +
                "Lname, " +
                "Email, " +
                "Gender, " +
                "Age, " +
                "Birthday, " +
                "PhoneNum, " +
                "ClosenessLevel, " +
                "CommMethod, " +
                "TiedUser) VALUES ('Elle', 'Vaan', 1, 'ellev@example.com', 23, '3/22/2001', '1-732-073-1331', 2, 1, 'jmahn2');");
        db.execSQL("INSERT INTO " + FRIENDS_TABLE_NAME +
                " (Fname, " +
                "Lname, " +
                "Email, " +
                "Gender, " +
                "Age, " +
                "Birthday, " +
                "PhoneNum, " +
                "ClosenessLevel, " +
                "CommMethod, " +
                "TiedUser) VALUES ('Ayame', 'Oso', 1, 'ayso@example.com', 19, '2/17/2005', '1-732-023-1323', 2, 1, 'ioi59');");
        db.execSQL("INSERT INTO " + FRIENDS_TABLE_NAME +
                " (Fname, " +
                "Lname, " +
                "Email, " +
                "Gender, " +
                "Age, " +
                "Birthday, " +
                "PhoneNum, " +
                "ClosenessLevel, " +
                "CommMethod, " +
                "TiedUser) VALUES ('Larry', 'Boiud', 1, 'lbud@example.com', 20, '6/26/2004', '1-712-273-1334', 3, 2, 'ioi59');");

        db.execSQL("INSERT INTO " + INTERESTS_TABLE_NAME +
                " (InterestName) VALUES ('Music');");
        db.execSQL("INSERT INTO " + INTERESTS_TABLE_NAME +
                " (InterestName) VALUES ('Gaming');");

        db.execSQL("INSERT INTO " + FRIEND_INTERESTS_TABLE_NAME +
                " (FriendInterestID, InterestID) VALUES (1, 1);");
        db.execSQL("INSERT INTO " + FRIEND_INTERESTS_TABLE_NAME +
                " (FriendInterestID, InterestID) VALUES (2, 2);");

        db.execSQL("INSERT INTO " + COMM_TYPES_TABLE_NAME +
                " (CommTypeName) VALUES ('IRC');");
        db.execSQL("INSERT INTO " + COMM_TYPES_TABLE_NAME +
                " (CommTypeName) VALUES ('Social Media');");

        db.execSQL("INSERT INTO " + COMM_METHODS_TABLE_NAME +
                " (CommMethodName, CommTypeID) VALUES ('Discord', 1);");
        db.execSQL("INSERT INTO " + COMM_METHODS_TABLE_NAME +
                " (CommMethodName, CommTypeID) VALUES ('Twitter', 2);");
        db.execSQL("INSERT INTO " + COMM_METHODS_TABLE_NAME +
                " (CommMethodName, CommTypeID) VALUES ('Bluesky', 2);");

        db.execSQL("INSERT INTO " + FRIEND_COMM_METHODS_TABLE_NAME +
                " (FriendCommID, CommMethodID) VALUES (1, 1);");
        db.execSQL("INSERT INTO " + FRIEND_COMM_METHODS_TABLE_NAME +
                " (FriendCommID, CommMethodID) VALUES (1, 2);");
        db.execSQL("INSERT INTO " + FRIEND_COMM_METHODS_TABLE_NAME +
                " (FriendCommID, CommMethodID) VALUES (2, 2);");
        db.execSQL("INSERT INTO " + FRIEND_COMM_METHODS_TABLE_NAME +
                " (FriendCommID, CommMethodID) VALUES (2, 1);");
        db.execSQL("INSERT INTO " + FRIEND_COMM_METHODS_TABLE_NAME +
                " (FriendCommID, CommMethodID) VALUES (3, 3);");
        db.execSQL("INSERT INTO " + FRIEND_COMM_METHODS_TABLE_NAME +
                " (FriendCommID, CommMethodID) VALUES (3, 2);");
        db.execSQL("INSERT INTO " + FRIEND_COMM_METHODS_TABLE_NAME +
                " (FriendCommID, CommMethodID) VALUES (4, 3);");
        db.execSQL("INSERT INTO " + FRIEND_COMM_METHODS_TABLE_NAME +
                " (FriendCommID, CommMethodID) VALUES (4, 1);");

        //close the database
        db.close();
    }

    public int countRecordsFromTable(String tableName)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, tableName);
        db.close();
        return numRows;
    }

    public void fillFriendsList(String username, ArrayList<Friend> al)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + FRIENDS_TABLE_NAME + " WHERE TiedUser = '" + username + "';";
        Cursor cursor = db.rawQuery(select, null);
        //clear to reset list per user
        al.clear();
        if (cursor != null)
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                int friendID = cursor.getInt(0);
                String fname = cursor.getString(1);
                String lname = cursor.getString(2);
                String email = cursor.getString(3);
                int gender = cursor.getInt(4);
                int age = cursor.getInt(5);
                String birthday = cursor.getString(6);
                String phoneNum = cursor.getString(7);
                int closenessLevel = cursor.getInt(8);
                int commMethod = cursor.getInt(9);
                String tiedUser = cursor.getString(10);
                Friend friend = new Friend(friendID, fname, lname, email, gender, age, birthday, phoneNum, closenessLevel, commMethod, tiedUser);
                al.add(friend);
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
    }

    //return as arraylist instead of straight string so we can manipulate stuff easier later if needed
    public ArrayList<String> getCommMethodsByID(int id)
    {
        ArrayList<String> al = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT " + COMM_METHODS_TABLE_NAME + ".CommMethodName " +
                "FROM " + COMM_METHODS_TABLE_NAME +
                " INNER JOIN " + FRIEND_COMM_METHODS_TABLE_NAME +
                " ON " + FRIEND_COMM_METHODS_TABLE_NAME + ".CommMethodID = " +
                COMM_METHODS_TABLE_NAME + ".CommMethodID " +
                "WHERE " + FRIEND_COMM_METHODS_TABLE_NAME + ".FriendCommID = " + id + ";";
        Cursor cursor = db.rawQuery(select, null);

        if (cursor != null)
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                String methodName = cursor.getString(0);
                Log.d("Name: ", methodName + id);
                al.add(methodName);
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();

        return al;
    }

    public Boolean findUserInDB(String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + USERS_TABLE_NAME + " WHERE Username = '" + username + "';";
        Cursor cursor = db.rawQuery(select, null);
        if (cursor.moveToFirst())
        {
            db.close();
            return true;
        }
        else
        {
            cursor.close();
            db.close();
            return false;
        }
    }
}