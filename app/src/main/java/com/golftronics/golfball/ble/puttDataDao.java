package com.golftronics.golfball.ble;

import android.provider.ContactsContract;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;



@Dao
public interface puttDataDao {

    @Insert
    void insert(PuttData puttsegment);

    @Update
    void update(PuttData puttsegment);

    @Delete
    void delete(PuttData puttsegment);

    @Query("DELETE FROM puttdata_table")
    void deleteAllPutts();


    /* the below query returns all rows/columns but can be modified for a custom query search later
    or another type of query can be added underneath*/


    @Query("SELECT COUNT(*) FROM puttdata_table WHERE (((target_distance BETWEEN :minDist AND :maxDist) AND velocity = 0 AND distance > 0) OR ((target_distance BETWEEN :minDist AND :maxDist)AND made = 1 AND distance > 0))")
    LiveData<Integer> getAllPuttsOverMinDist(int minDist, int maxDist);


    @Query("SELECT COUNT(*) FROM puttdata_table WHERE (target_distance BETWEEN :minDist AND :maxDist) AND made = 1")
    LiveData<Integer> getAllMadePutts(int minDist, int maxDist);


    @Query("SELECT COUNT(*) FROM puttdata_table WHERE date > :timeRange AND (target_distance BETWEEN :minDist AND :maxDist)AND made = 1")
    LiveData<Integer> getMadePutts(long timeRange, int minDist, int maxDist);


    @Query("SELECT COUNT(*) FROM puttdata_table WHERE date > :timeRange AND (((target_distance BETWEEN :minDist AND :maxDist)AND velocity = 0 AND distance > 0) OR ((target_distance BETWEEN :minDist AND :maxDist) AND made = 1 AND distance > 0)) ")
    LiveData<Integer> getPuttsOverMinDistByDate(long timeRange, int minDist, int maxDist);


    //query for distance, time range, and slope

    @Query("SELECT COUNT(*) FROM puttdata_table WHERE date > :timeRange AND (target_distance BETWEEN :minDist AND :maxDist)AND slope_direction = :slopeDirection AND made = 1")
    LiveData<Integer> getMadePuttsWithSlope(long timeRange, int minDist, int maxDist, String slopeDirection);


    @Query("SELECT COUNT(*) FROM puttdata_table WHERE date > :timeRange AND slope_direction = :slopeDirection AND distance > 0 AND (((target_distance BETWEEN :minDist AND :maxDist)AND velocity = 0) OR (((distance BETWEEN :minDist AND :maxDist)) AND made = 1 AND distance > 0)) ")
    LiveData<Integer> getPuttsOverMinDistByDateWithSlope(long timeRange, int minDist, int maxDist, String slopeDirection);


    //query for distance, time range, and stimp

    @Query("SELECT COUNT(*) FROM puttdata_table WHERE date > :timeRange AND (target_distance BETWEEN :minDist AND :maxDist)AND (stimp BETWEEN :stimpMin AND :stimpMax) AND made = 1")
    LiveData<Integer> getMadePuttsWithStimp(long timeRange, int minDist, int maxDist, double stimpMin, double stimpMax);


    @Query("SELECT COUNT(*) FROM puttdata_table WHERE date > :timeRange AND stimp BETWEEN :stimpMin AND :stimpMax AND distance > 0  AND (((target_distance BETWEEN :minDist AND :maxDist)AND velocity = 0) OR (((target_distance BETWEEN :minDist AND :maxDist)) AND made = 1 AND distance > 0)) ")
    LiveData<Integer> getPuttsOverMinDistByDateWithStimp(long timeRange, int minDist, int maxDist, double stimpMin, double stimpMax);



    //query for distance, time range, slope, and stimp

    @Query("SELECT COUNT(*) FROM puttdata_table WHERE date > :timeRange AND (target_distance BETWEEN :minDist AND :maxDist)AND slope_direction = :slopeDirection AND (stimp BETWEEN :stimpMin AND :stimpMax) AND made = 1")
    LiveData<Integer> getMadePuttsWithSlopeWithStimp(long timeRange, int minDist, int maxDist, String slopeDirection, double stimpMin, double stimpMax);


    @Query("SELECT COUNT(*) FROM puttdata_table WHERE date > :timeRange AND slope_direction = :slopeDirection AND distance > 0 AND (stimp BETWEEN :stimpMin AND :stimpMax) AND (((target_distance BETWEEN :minDist AND :maxDist)AND velocity = 0) OR ((distance BETWEEN :minDist AND :maxDist) AND made = 1 AND distance > 0)) ")
    LiveData<Integer> getPuttsOverMinDistByDateWithSlopeWithStimp(long timeRange, int minDist, int maxDist, String slopeDirection, double stimpMin, double stimpMax);


    @Query("SELECT made FROM puttdata_table WHERE id = (SELECT MAX(id) FROM puttdata_table)")
    LiveData<Integer> getLastEntryMadeStatus();


    @Query("SELECT date FROM puttdata_table WHERE date > :timePuttStart AND velocity = (SELECT MAX(velocity) FROM puttdata_table WHERE date > :timePuttStart)")
    LiveData<Long> getTimeMaxVelocity(long timePuttStart);


    @Query("SELECT date FROM puttdata_table WHERE distance = 0 AND id = (SELECT MAX(id) FROM puttdata_table)")
    LiveData<Long> getTimePuttStart();

    @Query("SELECT session_num FROM puttdata_table WHERE id = (SELECT MAX(id) FROM puttdata_table)")
    LiveData<Integer> getLastSessionNum();


    @Query("SELECT date FROM puttdata_table WHERE id = (SELECT MAX(id) FROM puttdata_table)")
    LiveData<Long> getTimePuttEnd();


    @Query("SELECT AVG(continuation_distance) FROM puttdata_table WHERE date >= :timePuttStart")
    LiveData<Float> getAccelYAverage(long timePuttStart);


    @Query("UPDATE puttdata_table SET made = 1 WHERE id = (SELECT MAX(id) FROM puttdata_table) ")
    void updateLastPuttMade();


    //query for velocity of putt made

    @Query("SELECT velocity FROM puttdata_table WHERE made = 1 AND id =(SELECT MAX(id) FROM puttdata_table)")
    LiveData<Double> getVelocityLastMade();



    //query for average velocity of made putts

    @Query("SELECT AVG(velocity) FROM puttdata_table WHERE made = 1 AND velocity > 0")
    LiveData<Float> getAverageVelocityPuttsMade();





    //int getAllLongPutts();

}
