package com.golftronics.golfball.ble;

public class GridPointData {


    float x;
    float y;
    String result;
    double distance;
    int target_distance;
    String miss_side;
    String pace;
    String slope;
    double roll_time;
    double pure_roll;
    double max_velocity;
    int video;
    //int sessionNum;
    //long time;

    public GridPointData(float x, float y, String result, double distance, int target_distance,
                         String miss_side, String pace, String slope, double roll_time, double pure_roll,
                         double max_velocity, int video) {

        this.x = x;
        this.y = y;
        this.result = result;
        this.distance = distance;
        this.target_distance = target_distance;
        this.miss_side = miss_side;
        this.pace = pace;
        this.slope = slope;
        this.roll_time = roll_time;
        this.pure_roll = pure_roll;
        this.max_velocity = max_velocity;
        this.video = video;
        //this.attempted = attempted;
        //this.made = made;
        //this.sessionNum = sessionNum;
        //this.time = time;
    }

    public GridPointData() {
    }


    public float getX() {return x;}

    public float getY() {return y;}

    public String getResult() {return result;}

    public double getDistance() {return distance;}

    public int getTarget_distance() {return target_distance;}

    public String getMiss_side() {return miss_side;}

    public String getPace() {return pace;}

    public String getSlope() {return slope;}

    public double getRoll_time() {return roll_time;}

    public double getPure_roll() {return pure_roll;}

    public double getMax_velocity() {return max_velocity;}

    public int getVideo() {return video;}

    //public double getAttempted() { return attempted; }

    //public double getMade() {return made;}

    //public int getSessionNum() {return sessionNum;}

    //public long getTime() {return time;}
}
