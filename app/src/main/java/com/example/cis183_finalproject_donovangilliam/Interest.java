package com.example.cis183_finalproject_donovangilliam;

public class Interest
{
    private int interestID;
    private int friendID;
    private String interestName;

    public Interest(int interestID, int friendID, String interestName)
    {
        this.interestID = interestID;
        this.friendID = friendID;
        this.interestName = interestName;
    }

    public Interest(int friendID, String interestName)
    {
        this.friendID = friendID;
        this.interestName = interestName;
    }

    public int getInterestID() {
        return interestID;
    }

    public void setInterestID(int interestID) {
        this.interestID = interestID;
    }

    public int getFriendID() {
        return friendID;
    }

    public void setFriendID(int friendID) {
        this.friendID = friendID;
    }

    public String getInterestName() {
        return interestName;
    }

    public void setInterestName(String interestName) {
        this.interestName = interestName;
    }
}
