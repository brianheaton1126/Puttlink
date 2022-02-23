package com.golftronics.golfball.ble;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


@Dao
public interface puttswingDataDao {

    @Insert
    void insert(PuttSwingData puttswingsegment);

    @Update
    void update(PuttSwingData puttswingsegment);

    @Delete
    void delete(PuttSwingData puttswingsegment);

    @Query("DELETE FROM puttswingdata_table")
    void deleteAllPuttSwingData();


    /* the below query returns all rows/columns but can be modified for a custom query search later
    or another type of query can be added underneath*/


    //@Query("SELECT MAX(acceleration) FROM puttswingdata_table WHERE session = :puttNumber AND date BETWEEN (:contactTime -1500) AND :contactTime")
    //LiveData<Integer> accelerationAtTopOfBackswing(int puttNumber, long contactTime);

    @Query("SELECT MIN(ball_distance) FROM puttswingdata_table WHERE session = :puttNumber AND ball_distance != 0  ")
    LiveData<Integer> ballDistanceForContactTimeWindow(int puttNumber);

    @Query("SELECT id FROM puttswingdata_table WHERE session = :puttNumber AND ball_distance = :firstDistance ")
    LiveData<Integer> idAtFirstDistance(int puttNumber, int firstDistance);

    @Query("SELECT date FROM puttswingdata_table WHERE session = :puttNumber AND ball_distance = :firstBallDistance")
    LiveData<  Long> timeForContactWindow(int puttNumber, int firstBallDistance);

    @Query("SELECT date FROM puttswingdata_table WHERE acceleration = (SELECT MAX(acceleration) FROM puttswingdata_table WHERE date > ((SELECT MAX(date) FROM puttswingdata_table WHERE ball_contact_flag = 1)-2000) ) AND date > ((SELECT MAX(date) FROM puttswingdata_table WHERE ball_contact_flag = 1)-2000) ")
    LiveData<Long> timeAtTopOfBackswing();

    //@Query("SELECT date FROM puttswingdata_table WHERE session = :puttNumber AND id = (:contactId - 1)")
    //LiveData<Long> timeAtContact(int puttNumber, int contactId);

    @Query("SELECT MAX(date) FROM puttswingdata_table WHERE ball_contact_flag = 1")
    LiveData<Long> timeAtContact();

    @Query("SELECT acceleration FROM puttswingdata_table WHERE ball_contact_flag = 1 AND date = (SELECT MAX(date) FROM puttswingdata_table WHERE ball_contact_flag = 1) ")
    LiveData<Integer> accelerationAtContact();

    @Query("SELECT MAX(acceleration) FROM puttswingdata_table WHERE date > ((SELECT MAX(date) FROM puttswingdata_table WHERE ball_contact_flag = 1)-2000)")
    LiveData<Integer> accelerationAtTopOfBackswing();

    @Query("SELECT MIN(acceleration) FROM puttswingdata_table WHERE date > (SELECT MAX(date) FROM puttswingdata_table WHERE ball_contact_flag = 1)")
    LiveData<Integer> accelerationAtTopOfFollowThrough();


    @Query("SELECT MAX(id) FROM puttswingdata_table")
    LiveData<Integer> maxId();

    @Query("SELECT acceleration FROM puttswingdata_table WHERE id = :Id")
    LiveData<Integer> accelerationForLoop(int Id);











    //int getAllLongPutts();

}
