package com.golftronics.golfball.ble;

public class SessionData {

    float sessionNum;
    float makeFB;

    public SessionData(float sessionNum, float makeFB) {
        this.sessionNum = sessionNum;
        this.makeFB = makeFB;
    }

    public SessionData() {
    }

    public float getsessionNum() {
        return sessionNum;
    }

    public float getmakeFB() {
        return makeFB;
    }
}
