package com.example.cis183_finalproject_donovangilliam;

public class User
{
    private String username;
    private String fname;
    private String lname;
    private int age;
    private String birthday;
    private String lastlogin;

    public User()
    {

    }

    public User(String username, String fname, String lname, int age, String birthday, String lastlogin)
    {
        this.username = username;
        this.fname = fname;
        this.lname = lname;
        this.age = age;
        this.birthday = birthday;
        this.lastlogin = lastlogin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(String lastlogin) {
        this.lastlogin = lastlogin;
    }
}