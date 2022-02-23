package com.golftronics.golfball.ble;

public class LeaderboardItem {

    private String mplayerRank;
    private String mplayerName;
    private String mplayerScore;

    public LeaderboardItem(String playerRank, String playerName, String playerScore){

        mplayerRank = playerRank;
        mplayerName = playerName;
        mplayerScore = playerScore;

    }

    public String getMplayerRank(){
        return mplayerRank;
    }

    public String getMplayerName(){
        return mplayerName;
    }

    public String getMplayerScore(){
        return mplayerScore;
    }
}
