package com.example.cis183_finalproject_donovangilliam;

public class Interest
{
    private int interestID;
    private String interestName;

    public Interest(int interestID, String interestName)
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
