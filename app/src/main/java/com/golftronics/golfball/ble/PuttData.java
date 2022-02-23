package com.golftronics.golfball.ble;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "puttdata_table")
public class PuttData {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private long date;

    private String userID;

    private int session_num;

    private int putt_number;

    private double velocity;

    private double distance;

    private int acceleration;


    private double stimp;

    private boolean made;


    private double continuation_distance;


    private double target_distance;


    private int y_accel;


    private String slope_direction;


    public PuttData(long date, String userID, int session_num, int putt_number, double velocity, double distance,
                    int acceleration, double stimp, boolean made, double continuation_distance,
                    double target_distance, int y_accel, String slope_direction) {

        this.date = date;
        this.userID = userID;
        this.session_num = session_num;
        this.putt_number = putt_number;
        this.velocity = velocity;
        this.distance = distance;
        this.acceleration = acceleration;
        this.stimp = stimp;
        this.made = made;
        this.continuation_distance = continuation_distance;
        this.target_distance = target_distance;
        this.y_accel = y_accel;
        this.slope_direction = slope_direction;
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

    public String getUserID() {
        return userID;
    }

    public int getSession_num() {
        return session_num;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getDistance() {
        return distance;
    }

    public boolean isMade() {
        return made;
    }

    public int getAcceleration() {
        return acceleration;
    }

    public double getStimp() {
        return stimp;
    }

    public int getPutt_number() {
        return putt_number;
    }

    public double getContinuation_distance() {
        return continuation_distance;
    }

    public double getTarget_distance() {
        return target_distance;
    }

    public int getY_accel() { return y_accel; }

    public String getSlope_direction() {
        return slope_direction;
    }
}
