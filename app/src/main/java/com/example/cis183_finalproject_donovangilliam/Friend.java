package com.example.cis183_finalproject_donovangilliam;

import java.io.Serializable;

public class Friend implements Serializable
{
    private int friendID;
    private String fname;
    private String lname;
    private String email;
    private int gender;
    private int age;
    private String birthday;
    private String phoneNum;
    private int closenessLevel;
    private String tiedUser;
    // 0 - unmarked
    // 1 - marked
    private int isMarked;

    // used for obtaining friend info from db
    public Friend(int friendID,
                  String fname,
                  String lname,
                  String email,
                  int gender,
                  int age,
                  String birthday,
                  String phoneNum,
                  int closenessLevel,
                  String tiedUser,
                  int isMarked)
    {
        this.friendID = friendID;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.birthday = birthday;
        this.phoneNum = phoneNum;
        this.closenessLevel = closenessLevel;
        this.tiedUser = tiedUser;
        this.isMarked = isMarked;
    }

    // used for adding new friends
    public Friend(
                  String fname,
                  String lname,
                  String email,
                  int gender,
                  int age,
                  String birthday,
                  String phoneNum,
                  int closenessLevel,
                  String tiedUser,
                  int isMarked)
    {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.birthday = birthday;
        this.phoneNum = phoneNum;
        this.closenessLevel = closenessLevel;
        this.tiedUser = tiedUser;
        this.isMarked = isMarked;
    }

    public int getFriendID() {
        return friendID;
    }

    public void setFriendID(int friendID) {
        this.friendID = friendID;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getClosenessLevel() {
        return closenessLevel;
    }

    public void setClosenessLevel(int closenessLevel) {
        this.closenessLevel = closenessLevel;
    }

    public String getTiedUser() {
        return tiedUser;
    }

    public void setTiedUser(String tiedUser) {
        this.tiedUser = tiedUser;
    }

    public int isMarked() {
        return isMarked;
    }

    public void setMarked(int marked) {
        isMarked = marked;
    }

    //redo this maybe
    public String closenessString(int i)
    {
        if (i == 0)
        {
            return "Not Close";
        }
        else if (i == 1)
        {
            return "Acquainted";
        }
        else if (i == 2)
        {
            return "Friend";
        }
        else if (i == 3)
        {
            return "Close Friend";
        }
        else if (i == 4)
        {
            return "Best Friend";
        }
        else
        {
            return "";
        }
    }
}
