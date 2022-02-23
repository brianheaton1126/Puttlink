package com.golftronics.golfball.ble;

public class StatSample {
    private long timeStamp;
    private int session;
    private String userID;
    private int numberOfPutts;
    private double distance;
    private int totalMade;
    private double practiceTime;
    private String location;


    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getSession() {
        return session;
    }

    public void setSession(int session) {
        this.session = session;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getNumberOfPutts() {
        return numberOfPutts;
    }

    public void setNumberOfPutts(int numberOfPutts) {
        this.numberOfPutts = numberOfPutts;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getTotalMade() {
        return totalMade;
    }

    public void setTotalMade(int totalMade) {
        this.totalMade = totalMade;
    }

    public double getPracticeTime() {
        return practiceTime;
    }

    public void setPracticeTime(double practiceTime) {
        this.practiceTime = practiceTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "StatSample{" +
                "date=" + session +
                ", userID='" + userID + '\'' +
                ", numberOfPutts=" + numberOfPutts +
                ", distance=" + distance +
                ", totalMade=" + totalMade +
                ", practiceTime=" + practiceTime +
                ", location='" + location + '\'' +
                '}';
    }
}
