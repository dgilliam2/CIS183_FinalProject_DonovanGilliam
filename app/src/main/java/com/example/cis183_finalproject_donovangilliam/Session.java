package com.example.cis183_finalproject_donovangilliam;

public class Session
{
    //add more things here
    private static String loggedInUser;

    public Session (String username)
    {
        loggedInUser = username;
    }

    public static String getLoggedInUser()
    {
        return loggedInUser;
    }

    public static void setLoggedInUser(String u)
    {
        loggedInUser = u;
    }
}
