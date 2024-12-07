package com.example.cis183_finalproject_donovangilliam;

public class CommMethod
{
    private int commMethodID;
    private String commMethodName;
    private int commTypeID;

    public CommMethod(String commMethodName, int commTypeID)
    {
        this.commMethodName = commMethodName;
        this.commTypeID = commTypeID;
    }

    public int getCommMethodID() {
        return commMethodID;
    }

    public void setCommMethodID(int commMethodID) {
        this.commMethodID = commMethodID;
    }

    public String getCommMethodName() {
        return commMethodName;
    }

    public void setCommMethodName(String commMethodName) {
        this.commMethodName = commMethodName;
    }

    public int getCommTypeID() {
        return commTypeID;
    }

    public void setCommTypeID(int commTypeID) {
        this.commTypeID = commTypeID;
    }
}
