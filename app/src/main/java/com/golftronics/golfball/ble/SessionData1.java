package com.golftronics.golfball.ble;

public class SessionData1 {


    double attempted;
    double made;
    int sessionNum;
    long time;

    public SessionData1(double attempted, double made, int sessionNum, long time) {

        this.attempted = attempted;
        this.made = made;
        this.sessionNum = sessionNum;
        this.time = time;
    }

    public SessionData1() {
    }



    public double getAttempted() { return attempted; }

    public double getMade() {return made;}

    public int getSessionNum() {return sessionNum;}

    public long getTime() {return time;}
}
