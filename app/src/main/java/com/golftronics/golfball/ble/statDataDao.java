package com.golftronics.golfball.ble;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


@Dao
public interface statDataDao {

    @Insert
    void insert(StatData statsegment);

    @Update
    void update(StatData statsegment);

    @Delete
    void delete(StatData statsegment);

    @Query("DELETE FROM statdata_table")
    void deleteAllStats();


    /* the below query returns all rows/columns but can be modified for a custom query search later
    or another type of query can be added underneath*/


    @Query("SELECT number_of_putts FROM statdata_table WHERE session = :sessionNumber AND (distance BETWEEN :statMinDist AND :statMaxDist)")
    LiveData<Integer> statTotalAttemptsBySessionByDistanceRange(int sessionNumber, int statMinDist, int statMaxDist);

    @Query("SELECT total_made FROM statdata_table WHERE session = :sessionNumber AND (distance BETWEEN :statMinDist AND :statMaxDist)")
    LiveData<Integer> statTotalMadeBySessionByDistanceRange(int sessionNumber, int statMinDist, int statMaxDist);




    @Query("SELECT number_of_putts FROM statdata_table WHERE (distance BETWEEN :statMinDist AND :statMaxDist)")
    LiveData<Integer> statTotalAttemptsByDistanceRange(int statMinDist, int statMaxDist);

    @Query("SELECT total_made FROM statdata_table WHERE (distance BETWEEN :statMinDist AND :statMaxDist)")
    LiveData<Integer> statTotalMadeByDistanceRange(int statMinDist, int statMaxDist);

    @Query("SELECT number_of_putts FROM statdata_table WHERE (distance BETWEEN :statMinDist AND :statMaxDist) AND date > :statDateMin AND date < :statDateMax")
    LiveData<Integer> statTotalAttemptsByDistanceRangeByDateRange(int statMinDist, int statMaxDist, long statDateMin, long statDateMax);

    @Query("SELECT total_made FROM statdata_table WHERE (distance BETWEEN :statMinDist AND :statMaxDist) AND date > :statDateMin AND date < :statDateMax")
    LiveData<Integer> statTotalMadeByDistanceRangeByDateRange(int statMinDist, int statMaxDist, long statDateMin, long statDateMax);


    @Query("SELECT practice_time FROM statdata_table WHERE (distance BETWEEN :statMinDist AND :statMaxDist)")
    LiveData<Double> statPracticeTimeByDistanceRange(int statMinDist, int statMaxDist);

    @Query("SELECT practice_time FROM statdata_table WHERE (distance BETWEEN :statMinDist AND :statMaxDist) AND date > :statDateMin AND date < :statDateMax")
    LiveData<Double> statPracticeTimeByDistanceRangeByDateRange(int statMinDist, int statMaxDist, long statDateMin, long statDateMax);







    //int getAllLongPutts();

}
