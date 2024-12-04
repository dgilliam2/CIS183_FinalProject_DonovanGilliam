package com.example.cis183_finalproject_donovangilliam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "AmieO.db";
    private static final String USERS_TABLE_NAME = "Users";
    private static final String FRIENDS_TABLE_NAME = "Friendship";
    private static final String FRIEND_INTERESTS_TABLE_NAME = "FriendInterests";
    private static final String INTERESTS_TABLE_NAME = "Interests";
    private static final String FRIEND_COMM_METHODS_TABLE_NAME = "FriendComms";
    private static final String COMM_METHODS_TABLE_NAME = "CommMethods";
    private static final String COMM_TYPES_TABLE_NAME = "CommTypes";

    // put version up here too so i can change it easier
    private static final int DATABASE_VERSION = 24;

    public DatabaseHelper(Context c) {
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Setup
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " +
                USERS_TABLE_NAME +
                " (Username varchar(50) PRIMARY KEY, " +
                "Fname varchar(50), " +
                "Lname varchar(50), " +
                "Age integer, " +
                "Birthday varchar(50), " +
                "LastLogin varchar(50));");

        // Gender:
        // 0 - Male
        // 1 - Female
        // 2 - Other
        // IsMarked:
        // 0 - Unmarked
        // 1 - Marked
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
                "TiedUser varchar(50), " +
                "IsMarked integer," +
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
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + FRIENDS_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + FRIEND_INTERESTS_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + INTERESTS_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + FRIEND_COMM_METHODS_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + COMM_METHODS_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + COMM_TYPES_TABLE_NAME + ";");


        onCreate(db);
    }

    public void initFunc() {
        // check the table count here so that it only initializes the db if both of these are empty
        // used to prevent it from refilling if friends are deleted, since we won't allow users to be deleted
        if (countRecordsFromTable(USERS_TABLE_NAME) == 0 && countRecordsFromTable(FRIENDS_TABLE_NAME) == 0) {
            initData();
        }
    }

    private void initData() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("INSERT INTO " + USERS_TABLE_NAME +
                "(Username," +
                "Fname, " +
                "Lname, " +
                "Age, " +
                "Birthday," +
                "LastLogin) VALUES ('jmahn2','John', 'Mahn', 21, '1/07/2003', '11/27/2024');");
        db.execSQL("INSERT INTO " + USERS_TABLE_NAME +
                "(Username," +
                "Fname, " +
                "Lname, " +
                "Age, " +
                "Birthday, " +
                "LastLogin) VALUES ('ioi59','Issac', 'Ingham', 26, '6/14/1998', '11/23/2024');");

        db.execSQL("INSERT INTO " + FRIENDS_TABLE_NAME +
                " (Fname, " +
                "Lname, " +
                "Gender, " +
                "Email, " +
                "Age, " +
                "Birthday, " +
                "PhoneNum, " +
                "ClosenessLevel, " +
                "TiedUser, " +
                "IsMarked) VALUES ('Emile', 'Smile', 0, 'emiles@example.com', 22, '6/20/2002', '1-734-048-1732', 4, 'jmahn2', 0);");
        db.execSQL("INSERT INTO " + FRIENDS_TABLE_NAME +
                " (Fname, " +
                "Lname, " +
                "Gender, " +
                "Email, " +
                "Age, " +
                "Birthday, " +
                "PhoneNum, " +
                "ClosenessLevel, " +
                "TiedUser, " +
                "IsMarked) VALUES ('Elle', 'Vaan', 1, 'ellev@example.com', 23, '3/22/2001', '1-732-073-1331', 0, 'jmahn2', 1);");
        db.execSQL("INSERT INTO " + FRIENDS_TABLE_NAME +
                " (Fname, " +
                "Lname, " +
                "Gender, " +
                "Email, " +
                "Age, " +
                "Birthday, " +
                "PhoneNum, " +
                "ClosenessLevel, " +
                "TiedUser, " +
                "IsMarked) VALUES ('Ayame', 'Oso', 2, 'ayso@example.com', 19, '2/17/2005', '1-732-023-1323', 2, 'ioi59', 1);");
        db.execSQL("INSERT INTO " + FRIENDS_TABLE_NAME +
                " (Fname, " +
                "Lname, " +
                "Gender, " +
                "Email, " +
                "Age, " +
                "Birthday, " +
                "PhoneNum, " +
                "ClosenessLevel, " +
                "TiedUser, " +
                "IsMarked) VALUES ('Larry', 'Boiud', 0, 'lbud@example.com', 20, '6/26/2004', '1-712-273-1334', 3, 'ioi59', 1);");

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
        db.execSQL("INSERT INTO " + COMM_TYPES_TABLE_NAME +
                " (CommTypeName) VALUES ('Social Media');");
        db.execSQL("INSERT INTO " + COMM_TYPES_TABLE_NAME +
                " (CommTypeName) VALUES ('Real Life');");

        db.execSQL("INSERT INTO " + COMM_METHODS_TABLE_NAME +
                " (CommMethodName, CommTypeID) VALUES ('Discord', 1);");
        db.execSQL("INSERT INTO " + COMM_METHODS_TABLE_NAME +
                " (CommMethodName, CommTypeID) VALUES ('Twitter', 2);");
        db.execSQL("INSERT INTO " + COMM_METHODS_TABLE_NAME +
                " (CommMethodName, CommTypeID) VALUES ('Bluesky', 2);");
        db.execSQL("INSERT INTO " + COMM_METHODS_TABLE_NAME +
                " (CommMethodName, CommTypeID) VALUES ('School', 3);");

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

        db.close();
    }

    public int countRecordsFromTable(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, tableName);
        db.close();
        return numRows;
    }

    public void fillFriendsList(String username, ArrayList<Friend> al) {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + FRIENDS_TABLE_NAME + " WHERE TiedUser = '" + username + "';";
        Cursor cursor = db.rawQuery(select, null);
        // clear to reset list per user
        al.clear();
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int friendID = cursor.getInt(0);
                String fname = cursor.getString(1);
                String lname = cursor.getString(2);
                int gender = cursor.getInt(3);
                String email = cursor.getString(4);
                int age = cursor.getInt(5);
                String birthday = cursor.getString(6);
                String phoneNum = cursor.getString(7);
                int closenessLevel = cursor.getInt(8);
                String tiedUser = cursor.getString(9);
                int isMarked = cursor.getInt(10);
                Friend friend = new Friend(friendID, fname, lname, email, gender, age, birthday, phoneNum, closenessLevel,tiedUser, isMarked);
                al.add(friend);
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
    }

    // return as arraylist instead of straight string so we can manipulate stuff easier later if needed
    public ArrayList<String> getCommMethodsByID(int id) {
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
            while (!cursor.isAfterLast()) {
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

    public Boolean findUserInDB(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + USERS_TABLE_NAME + " WHERE Username = '" + username + "';";
        Cursor cursor = db.rawQuery(select, null);
        if (cursor.moveToFirst())
        {
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }

    public boolean lastLoginThreeDaysElapsed(String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        String lastLoginString;
        Date currentDate = new Date();
        String select = "SELECT LastLogin FROM " + USERS_TABLE_NAME + " WHERE Username = '" + username + "';";
        Cursor cursor = db.rawQuery(select, null);
        if (cursor != null)
        {
            cursor.moveToFirst();
            lastLoginString = cursor.getString(0);
            Log.d("Last Login: ", lastLoginString);
            // could maybe do Date lastLoginDate = format.parse(cursor.getString(0)) but might be weird idk
            try
            {
                Date lastLoginDate = format.parse(lastLoginString);

                long differenceMili = currentDate.getTime() - lastLoginDate.getTime();

                long daysElapsed = (differenceMili / (1000 * 60 * 60 * 24));

                if (daysElapsed >= 3)
                {
                    cursor.close();
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
            catch (ParseException e)
            {
                e.printStackTrace();
            }
            cursor.close();
        }
        db.close();
        return false;
    }

    public void updateLastLogin(String username)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        String currentDate = format.format(new Date());

        ContentValues cv = new ContentValues();
        cv.put("LastLogin", currentDate);
        db.update(USERS_TABLE_NAME, cv, "Username = ?", new String[]{username});
        db.close();
    }

    public int getMostRecentFriendIDForUser(String username)
    {
        int recentID = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT FriendID FROM " + FRIENDS_TABLE_NAME + "WHERE TiedUser ='" + username + "'";

        Cursor cursor = db.rawQuery(select, null);

        if (cursor != null)
        {
            cursor.moveToFirst();
            recentID = cursor.getInt(0);
            cursor.close();
        }

        db.close();
        return recentID;

    }

    // DATA MANIP
    public void addUserToDB(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        String currentDate = format.format(new Date());
        ContentValues cv = new ContentValues();
        cv.put("Username", user.getUsername());
        cv.put("Fname", user.getFname());
        cv.put("Lname", user.getLname());
        cv.put("Age", user.getAge());
        cv.put("Birthday", user.getBirthday());
        cv.put("LastLogin", currentDate);
        db.insert(USERS_TABLE_NAME, null, cv);
        db.close();
    }

    public void addFriendToDB(Friend friend)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Fname", friend.getFname());
        cv.put("Lname", friend.getLname());
        cv.put("Gender", friend.getGender());
        cv.put("Email", friend.getEmail());
        cv.put("Age", friend.getAge());
        cv.put("Birthday", friend.getBirthday());
        cv.put("PhoneNum", friend.getPhoneNum());
        cv.put("ClosenessLevel", friend.getClosenessLevel());
        cv.put("TiedUser", friend.getTiedUser());
        cv.put("IsMarked", friend.isMarked());
        db.insert(FRIENDS_TABLE_NAME, null, cv);
        db.close();
    }

    // only admins will be allowed to add new interests, as it would not make sense to
    // show interests a user added for all users.
    public void addInterestToDB(Interest interest)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("InterestName", interest.getInterestName());

        db.insert(INTERESTS_TABLE_NAME, null, cv);
        db.close();
    }

    public void addCommMethodToDB(CommMethod commMethod)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("CommMethodName", commMethod.getCommMethodName());
        cv.put("CommTypeID", commMethod.getCommTypeID());

        db.insert(COMM_METHODS_TABLE_NAME, null, cv);
        db.close();
    }

    public void updateUserInDB(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("Fname", user.getFname());
        cv.put("Lname", user.getLname());
        cv.put("Age", user.getAge());
        cv.put("Birthday", user.getBirthday());

        db.update(USERS_TABLE_NAME, cv, "Username = ?", new String[]{user.getUsername()});
        db.close();
    }

    public void updateFriendInDB(Friend friend)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("Fname", friend.getFname());
        cv.put("Lname", friend.getLname());
        cv.put("Gender", friend.getGender());
        cv.put("Email", friend.getEmail());
        cv.put("Age", friend.getAge());
        cv.put("Birthday", friend.getBirthday());
        cv.put("PhoneNum", friend.getPhoneNum());
        cv.put("ClosenessLevel", friend.getClosenessLevel());
        cv.put("IsMarked", friend.isMarked());

        db.update(FRIENDS_TABLE_NAME, cv, "FriendID = ?", new String[]{String.valueOf(friend.getFriendID())});
        db.close();
    }

    public void updateInterestInDB(Interest interest)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("InterestName", interest.getInterestName());

        db.update(INTERESTS_TABLE_NAME, cv, "InterestID = ?", new String[]{String.valueOf(interest.getInterestID())});
        db.close();
    }

    public void updateCommMethodInDB(int commMethodID, CommMethod updatedCommMethod)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("CommMethodName", updatedCommMethod.getCommMethodName());
        cv.put("CommTypeID", updatedCommMethod.getCommTypeID());

        db.update(COMM_METHODS_TABLE_NAME, cv, "CommMethodID = ?", new String[]{String.valueOf(commMethodID)});
        db.close();
    }

    // this also removes all of a user's friends, and [their interests] <- add this
    public void removeUserFromDB(String username)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(USERS_TABLE_NAME,"Username = ?", new String[]{username});
        db.delete(FRIENDS_TABLE_NAME,"TiedUser = ?", new String[]{username});
        db.close();
    }

    // removes a friend and their interests from the DB
    public void removeFriendFromDB(int friendID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FRIEND_INTERESTS_TABLE_NAME, "FriendInterestID = ?", new String[]{String.valueOf(friendID)});
        db.delete(FRIENDS_TABLE_NAME, "FriendID = ?", new String[]{String.valueOf(friendID)});
        db.close();
    }

    public void filterFriends(String statement, ArrayList<Friend> al)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(statement, null);

        al.clear();
        if (cursor != null)
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                int friendID = cursor.getInt(0);
                String fname = cursor.getString(1);
                String lname = cursor.getString(2);
                int gender = cursor.getInt(3);
                String email = cursor.getString(4);
                int age = cursor.getInt(5);
                String birthday = cursor.getString(6);
                String phoneNum = cursor.getString(7);
                int closenessLevel = cursor.getInt(8);
                String tiedUser = cursor.getString(9);
                int isMarked = cursor.getInt(10);
                Friend friend = new Friend(friendID, fname, lname, email, gender, age, birthday, phoneNum, closenessLevel,tiedUser, isMarked);
                al.add(friend);
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
    }
}