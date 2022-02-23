package com.golftronics.golfball.ble;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class StatViewModel extends AndroidViewModel {

    private StatRepository statrepository;
    private LiveData<List<StatData>> allPutts;
    private LiveData<Integer> allLongPutts;
    public static int statMinDist;
    public static int statMaxDist;
    public long date;











    public StatViewModel(@NonNull Application application) {
        super(application);

        statrepository = new StatRepository(application);
        //allPutts = repository.getAllPutts();
        //allLongPutts = repository.getAllLongPutts();
    }

    public void insert(StatData statsegment){

        statrepository.insert(statsegment);

    }

    public void update(StatData statsegment){

        statrepository.update(statsegment);

    }

    public void delete(StatData statsegment){

        statrepository.delete(statsegment);

    }

    public void deleteAllStats(){


    }

    public MutableLiveData<Integer> statTotalAttemptsBySessionByDistanceRange = new MutableLiveData<>();

    public void statTotalAttemptsBySessionByDistanceRange(Integer input) {
        statTotalAttemptsBySessionByDistanceRange.setValue(input);
    }

    public LiveData<Integer> statTotalAttemptsBySessionByDistanceRange(int sessionNumber, int statMinDist, int statMaxDist){

        return statrepository.statTotalAttemptsBySessionByDistanceRange(sessionNumber, statMinDist, statMaxDist);
    }




    public MutableLiveData<Integer> statTotalMadeBySessionByDistanceRange = new MutableLiveData<>();

    public void statTotalMadeBySessionByDistanceRange(Integer input) {
        statTotalMadeBySessionByDistanceRange.setValue(input);
    }

    public LiveData<Integer> statTotalMadeBySessionByDistanceRange(int sessionNumber, int statMinDist, int statMaxDist){

        return statrepository.statTotalMadeBySessionByDistanceRange(sessionNumber, statMinDist, statMaxDist);
    }






    public MutableLiveData<Integer> statTotalPuttsByDistanceRange = new MutableLiveData<>();

    public void statTotalPuttsByDistanceRange(Integer input) {
        statTotalPuttsByDistanceRange.setValue(input);
        }

    public LiveData<Integer> statTotalAttemptsByDistanceRange(int statMinDist, int statMaxDist){

        return statrepository.statTotalAttemptsByDistanceRange(statMinDist, statMaxDist);
    }


    public MutableLiveData<Integer> statTotalMadePuttsByDistanceRange = new MutableLiveData<>();

    public void statTotalMadePuttsByDistanceRange(Integer input) {
        statTotalMadePuttsByDistanceRange.setValue(input);
    }

    public LiveData<Integer> statTotalMadeByDistanceRange(int statMinDist, int statMaxDist){

        return statrepository.statTotalMadeByDistanceRange(statMinDist, statMaxDist);
    }


    public MutableLiveData<Integer> statTotalPuttsByDistanceRangeByDateRange = new MutableLiveData<>();

    public void statTotalPuttsByDistanceRangeByDateRange(Integer input) {
        statTotalPuttsByDistanceRangeByDateRange.setValue(input);
    }

    public LiveData<Integer> statTotalAttemptsByDistanceRangeByDateRange(int statMinDist, int statMaxDist, long statDateMin, long statDateMax){

        return statrepository.statTotalAttemptsByDistanceRangeByDateRange(statMinDist, statMaxDist, statDateMin, statDateMax);
    }


    public MutableLiveData<Integer> statTotalMadePuttsByDistanceRangeByDateRange = new MutableLiveData<>();

    public void statTotalMadePuttsByDistanceRangeByDateRange(Integer input) {
        statTotalMadePuttsByDistanceRangeByDateRange.setValue(input);
    }

    public LiveData<Integer> statTotalMadeByDistanceRangeByDateRange(int statMinDist, int statMaxDist, long statDateMin, long statDateMax){

        return statrepository.statTotalMadeByDistanceRangeByDateRange(statMinDist, statMaxDist, statDateMin, statDateMax);
    }


    public MutableLiveData<Integer> statTotalPracticeTimeByDistanceRange = new MutableLiveData<>();

    public void statTotalPracticeTimeByDistanceRange(Integer input) {
        statTotalPracticeTimeByDistanceRange.setValue(input);
    }

    public LiveData<Double> statPracticeTimeByDistanceRange(int statMinDist, int statMaxDist){

        return statrepository.statPracticeTimeByDistanceRange(statMinDist, statMaxDist);
    }


    public MutableLiveData<Double> statTotalPracticeTimeByDistanceRangeByDateRange = new MutableLiveData<>();

    public void statTotalPracticeTimeByDistanceRangeByDateRange(Double input) {
        statTotalPracticeTimeByDistanceRangeByDateRange.setValue(input);
    }

    public LiveData<Double> statPracticeTimeByDistanceRangeByDateRange(int statMinDist, int statMaxDist, long statDateMin, long statDateMax){

        return statrepository.statPracticeTimeByDistanceRangeByDateRange(statMinDist, statMaxDist, statDateMin, statDateMax);
    }



}
