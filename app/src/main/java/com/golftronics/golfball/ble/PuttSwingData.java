package com.golftronics.golfball.ble;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "puttswingdata_table")
public class PuttSwingData {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private long date;

    private int session;

    private String userID;

    private int acceleration;

    private double ball_distance;

    private int ball_contact_flag;



    public PuttSwingData(long date, int session, String userID, int acceleration, double ball_distance,
                         int ball_contact_flag) {

        this.date = date;
        this.session = session;
        this.userID = userID;
        this.acceleration = acceleration;
        this.ball_distance = ball_distance;
        this.ball_contact_flag = ball_contact_flag;


    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {

        return id;
    }

    public long getDate() {


        return date;
    }

    public int getSession(){
        return session;
    }

    public String getUserID() {
        return userID;
    }

    public int getAcceleration() {return acceleration;}

    public double getBall_distance() {
        return ball_distance;
    }

    public int getBall_contact_flag() {
        return ball_contact_flag;
    }




}
