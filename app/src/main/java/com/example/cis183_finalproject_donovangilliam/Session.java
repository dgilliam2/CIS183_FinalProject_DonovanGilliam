package com.example.cis183_finalproject_donovangilliam;

public class Session
{
    //add more things here
    private static String loggedInUser;
    private static boolean loginLapseNotification = false;

    public Session (String username, boolean loginlapse)
    {
        loggedInUser = username;
        loginLapseNotification = loginlapse;
    }

    public static String getLoggedInUser()
    {
        return loggedInUser;
    }

    public static void setLoggedInUser(String u)
    {
        loggedInUser = u;
    }

    public static boolean isLoginLapseNotification() {
        return loginLapseNotification;
    }

    public static void setLoginLapseNotification(boolean loginLapseNotification) {
        Session.loginLapseNotification = loginLapseNotification;
    }
}
