package com.golftronics.golfball.ble;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class  PuttSwingViewModel extends AndroidViewModel {

    private PuttSwingRepository puttswingrepository;
    private LiveData<List<PuttSwingData>> allPutts;
    private LiveData<Integer> allLongPutts;
    public static int statMinDist;
    public static int statMaxDist;
    public long date;











    public PuttSwingViewModel(@NonNull Application application) {
        super(application);

        puttswingrepository = new PuttSwingRepository(application);
        //allPutts = repository.getAllPutts();
        //allLongPutts = repository.getAllLongPutts();
    }

    public void insert(PuttSwingData puttswingsegment){

        puttswingrepository.insert(puttswingsegment);

    }

    public void update(PuttSwingData puttswingsegment){

        puttswingrepository.update(puttswingsegment);

    }

    public void delete(PuttSwingData puttswingsegment){

        puttswingrepository.delete(puttswingsegment);

    }

    public void deleteAllPuttSwing(){


    }


    public MutableLiveData<Integer> accelerationAtTopOfBackswing = new MutableLiveData<>();

    public void accelerationAtTopOfBackswing(Integer input) {
        accelerationAtTopOfBackswing.setValue(input);
    }

    public LiveData<Integer> accelerationAtTopOfBackswing(){

        return puttswingrepository.accelerationAtTopOfBackswing();
    }


    public MutableLiveData<Integer> accelerationAtTopOfFollowThrough = new MutableLiveData<>();

    public void setAccelerationAtTopOfFollowThrough(Integer input) {
        accelerationAtTopOfFollowThrough.setValue(input);
    }

    public LiveData<Integer> accelerationAtTopOfFollowThrough(){

        return puttswingrepository.accelerationAtTopOfFollowThrough();
    }



    public MutableLiveData<Integer> accelerationAtContact = new MutableLiveData<>();

    public void setAccelerationAtContact(Integer input) {
        accelerationAtContact.setValue(input);
    }

    public LiveData<Integer> accelerationAtContact(){

        return puttswingrepository.accelerationAtContact();
    }




    public MutableLiveData<Integer> ballDistanceForContactTimeWindow = new MutableLiveData<>();

    public void setBallDistanceForContactTimeWindow(Integer input) {
        ballDistanceForContactTimeWindow.setValue(input);
    }

    public LiveData<Integer> ballDistanceForContactTimeWindow(int puttNumber){

        return puttswingrepository.ballDistanceForContactTimeWindow(puttNumber);
    }






    public MutableLiveData<Long> timeForContactWindow = new MutableLiveData<>();

    public void timeForContactWindow(Long input) {
        timeForContactWindow.setValue(input);
        }

    public LiveData<Long> timeForContactWindow(int puttNumber, int firstBallDistance){

        return puttswingrepository.timeForContactWindow(puttNumber, firstBallDistance);
    }




    public MutableLiveData<Long> timeAtTopOfBackswing = new MutableLiveData<>();

    public void timeAtTopOfBackswing(Long input) {
        timeAtTopOfBackswing.setValue(input);
    }

    public LiveData<Long> timeAtTopOfBackswing(){

        return puttswingrepository.timeAtTopOfBackswing();
    }



    public MutableLiveData<Integer> idAtFirstDistance = new MutableLiveData<>();

    public void tidAtFirstDistance(Integer input) {
        idAtFirstDistance.setValue(input);
    }

    public LiveData<Integer> idAtFirstDistance(int puttNumber, int firstDistance){

        return puttswingrepository.idAtFirstDistance(puttNumber, firstDistance);
    }




    public LiveData<Long> timeAtContact(){

        return puttswingrepository.timeAtContact();
    }





    public LiveData<Integer>  maxId(){

        return puttswingrepository.maxId();
    }

    public LiveData<Integer>  accelerometerForLoop(int Id){

        return puttswingrepository.accelerationForLoop(Id);
    }





}
