package com.golftronics.golfball.ble;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

public class PuttSwingRepository {

    private puttswingDataDao swingDao;
    //private LiveData<List<StatData>> allPutts;
    //private LiveData<Integer> allLongPutts;


    public PuttSwingRepository(Application application){

        PuttSwingDatabase database = PuttSwingDatabase.getInstance(application);
        swingDao = database.puttswingdatadao();
        //allPutts = puttDao.getAllPutts();
        //allLongPutts = puttDao.getAllLongPutts();
    }

    public void insert(PuttSwingData puttswingsegment){

        new InsertPuttSwingAsyncTask(swingDao).execute(puttswingsegment);

    }

    public void update(PuttSwingData puttswingsegment){

        new UpdatePuttSwingAsyncTask(swingDao).execute(puttswingsegment);

    }

    public void delete(PuttSwingData puttswingsegment){

        new DeletePuttSwingAsyncTask(swingDao).execute(puttswingsegment);

    }

    public void deleteAllPuttSwingData(PuttSwingData puttswingsegment){

        new DeleteAllPuttSwingAsyncTask(swingDao).execute();


    }


    public LiveData<Integer> accelerationAtTopOfBackswing(){

        return swingDao.accelerationAtTopOfBackswing();
    }


    public LiveData<Integer> accelerationAtTopOfFollowThrough(){

        return swingDao.accelerationAtTopOfFollowThrough();
    }


    public LiveData<Integer> accelerationAtContact(){

        return swingDao.accelerationAtContact();
    }



    public LiveData<Integer> ballDistanceForContactTimeWindow(int puttNumber){

        return swingDao.ballDistanceForContactTimeWindow(puttNumber);
    }




    public LiveData<Long> timeForContactWindow(int puttNumber, int firstBallDistance){

        return swingDao.timeForContactWindow(puttNumber, firstBallDistance);
    }


    public LiveData<Long> timeAtTopOfBackswing(){

        return swingDao.timeAtTopOfBackswing();
    }

    public LiveData<Integer> idAtFirstDistance(int puttNumber, int firstDistance){

        return swingDao.idAtFirstDistance(puttNumber, firstDistance);
    }

    public LiveData<Long> timeAtContact(){

        return swingDao.timeAtContact();
    }

    public LiveData<Integer> maxId(){

        return swingDao.maxId();
    }

    public LiveData<Integer> accelerationForLoop(int Id){

        return swingDao.accelerationForLoop(Id);
    }







    ////


    private static class InsertPuttSwingAsyncTask extends AsyncTask<PuttSwingData,Void,Void>{

        private puttswingDataDao swingDao;

        private InsertPuttSwingAsyncTask(puttswingDataDao swingDao){

            this.swingDao = swingDao;
        }

        @Override
        protected Void doInBackground(PuttSwingData... swingData) {

            swingDao.insert(swingData[0]);

            return null;

        }
    }

    private static class UpdatePuttSwingAsyncTask extends AsyncTask<PuttSwingData,Void,Void>{

        private puttswingDataDao swingDao;

        private UpdatePuttSwingAsyncTask(puttswingDataDao swingDao){

            this.swingDao = swingDao;
        }

        @Override
        protected Void doInBackground(PuttSwingData... swingData) {

            swingDao.update(swingData[0]);

            return null;

        }
    }

    private static class DeletePuttSwingAsyncTask extends AsyncTask<PuttSwingData,Void,Void>{

        private puttswingDataDao swingDao;

        private DeletePuttSwingAsyncTask(puttswingDataDao swingDao){

            this.swingDao = swingDao;
        }

        @Override
        protected Void doInBackground(PuttSwingData... swingData) {

            swingDao.delete(swingData[0]);

            return null;

        }
    }


    private static class DeleteAllPuttSwingAsyncTask extends AsyncTask<Void,Void,Void>{

        private puttswingDataDao swingDao;

        private DeleteAllPuttSwingAsyncTask(puttswingDataDao swingDao){

            this.swingDao = swingDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            swingDao.deleteAllPuttSwingData();

            return null;

        }
    }




}
