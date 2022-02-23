package com.golftronics.golfball.ble;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "statdata_table")
public class StatData {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private long date;

    private int session;

    private String userID;

    private int number_of_putts;

    private double distance;

    private int total_made;

    private double practice_time;

    private String location;

    public StatData(long date, int session, String userID, int number_of_putts, double distance,
                    int total_made, double practice_time, String location) {

        this.date = date;
        this.session = session;
        this.userID = userID;
        this.number_of_putts = number_of_putts;
        this.distance = distance;
        this.total_made = total_made;
        this.practice_time = practice_time;
        this.location = location;

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

    public int getNumber_of_putts() {return number_of_putts;}

    public double getDistance() {
        return distance;
    }

    public int getTotal_made() {
        return total_made;
    }

    public double getPractice_time() {
        return practice_time;
    }

    public String getLocation() {
        return location;
    }


}
