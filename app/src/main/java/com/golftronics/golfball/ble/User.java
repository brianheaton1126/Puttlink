package com.golftronics.golfball.ble;

public class User {

    public String name, email, password, username, handicap;

    public User(){

    }

    public User(String name, String email, String password, String username, String handicap){

        this.name = name;
        this.email = email;
        this.password = password;
        this.username = username;
        this.handicap = handicap;

    }

}
