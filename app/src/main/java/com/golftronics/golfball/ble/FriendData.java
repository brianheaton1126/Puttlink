package com.golftronics.golfball.ble;

public class FriendData {

    String friendName;
    String status;

    public FriendData(String friendName, String status) {
        this.friendName = friendName;
        this.status = status;
    }

    public FriendData() {
    }

    public String getfriendName() {
        return friendName;
    }

    public String getfriendStatus() {
        return status;
    }
}
