package com.example.cis183_finalproject_donovangilliam;

public class CommType
{
    private int commTypeID;
    private String commTypeName;

    public CommType(int commTypeID, String commTypeName)
    {
        this.commTypeID = commTypeID;
        this.commTypeName = commTypeName;
    }

    public int getCommTypeID() {
        return commTypeID;
    }

    public void setCommTypeID(int commTypeID) {
        this.commTypeID = commTypeID;
    }

    public String getCommTypeName() {
        return commTypeName;
    }

    public void setCommTypeName(String commTypeName) {
        this.commTypeName = commTypeName;
    }
}
