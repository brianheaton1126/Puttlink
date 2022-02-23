package com.golftronics.golfball.ble;

public class MinMaxDist {

    float attempted;
    float max;
    float min;
    long sessionNum;
    float totalDist;


    public MinMaxDist(float attempted, float max, float min, long sessionNum, float totalDist) {

        this.attempted = attempted;
        this.max = max;
        this.min = min;
        this.sessionNum = sessionNum;
        this.totalDist = totalDist;

    }

    public MinMaxDist() {
    }

    public float getAttempted() {return attempted;}

    public float getMin() { return min; }

    public float getMax() {return max;}

    public long getSessionNum() {return sessionNum;}

    public float getTotalDist() {return totalDist;}


}
