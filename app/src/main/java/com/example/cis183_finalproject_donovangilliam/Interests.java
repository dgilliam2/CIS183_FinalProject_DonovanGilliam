package com.example.cis183_finalproject_donovangilliam;

public class Interests
{
    private int interestID;
    private String interestName;

    // Constructor
    public Interests(int interestID, String interestName)
    {
        this.interestID = interestID;
        this.interestName = interestName;
    }

    public int getInterestID() {
        return interestID;
    }

    public void setInterestID(int interestID) {
        this.interestID = interestID;
    }

    public String getInterestName() {
        return interestName;
    }

    public void setInterestName(String interestName) {
        this.interestName = interestName;
    }
}