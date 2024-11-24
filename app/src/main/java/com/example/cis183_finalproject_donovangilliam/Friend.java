package com.example.cis183_finalproject_donovangilliam;

public class Friend
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
    private int commMethod;
    private String tiedUser;

    public Friend(int friendID,
                  String fname,
                  String lname,
                  String email,
                  int gender,
                  int age,
                  String birthday,
                  String phoneNum,
                  int closenessLevel,
                  int commMethod,
                  String tiedUser)
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
        this.commMethod = commMethod;
        this.tiedUser = tiedUser;
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

    public int getCommMethod() {
        return commMethod;
    }

    public void setCommMethod(int commMethod) {
        this.commMethod = commMethod;
    }

    public String getTiedUser() {
        return tiedUser;
    }

    public void setTiedUser(String tiedUser) {
        this.tiedUser = tiedUser;
    }

    //redo this maybe
    public String closenessString(int i)
    {
        if (i == 1)
        {
            return "Not Close";
        }
        else if (i == 2)
        {
            return "Acquainted";
        }
        else if (i == 3)
        {
            return "Friend";
        }
        else if (i == 4)
        {
            return "Close Friend";
        }
        else if (i == 5)
        {
            return "Best Friend";
        }
        else
        {
            return "";
        }
    }
}
