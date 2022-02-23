package com.golftronics.golfball.ble;

public class AverageData {


    int attempted;
    int made;


    public AverageData(int attempted, int made) {

        this.attempted = attempted;
        this.made = made;

    }

    public AverageData() {
    }



    public int getAttempted() { return attempted; }

    public int getMade() {return made;}


}
