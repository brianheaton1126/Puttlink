package com.golftronics.golfball.ble;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PuttRepository {

    private puttDataDao puttDao;
    private LiveData<List<PuttData>> allPutts;
    private LiveData<Integer> allLongPutts;


    public PuttRepository(Application application){

        PuttDatabase database = PuttDatabase.getInstance(application);
        puttDao = database.puttdatadao();
        //allPutts = puttDao.getAllPutts();
        //allLongPutts = puttDao.getAllLongPutts();
    }

    public void insert(PuttData puttsegment){

        new InsertPuttAsyncTask(puttDao).execute(puttsegment);

    }

    public void update(PuttData puttsegment){

        new UpdatePuttAsyncTask(puttDao).execute(puttsegment);

    }

    public void delete(PuttData puttsegment){

        new DeletePuttAsyncTask(puttDao).execute(puttsegment);

    }

    public void deleteAllPutts(PuttData puttsegment){

        new DeleteAllPuttAsyncTask(puttDao).execute();


    }


    public void updateLastPuttMade(){
        new UpdateLastPuttMadeAsyncTask(puttDao).execute();
    }


    public LiveData<Integer> getAllPuttsOverMinDist(int minDist, int maxDist){

        return puttDao.getAllPuttsOverMinDist(minDist, maxDist);
    }

    /*public LiveData<Integer> getAllLongPutts() {


        return puttDao.getAllLongPutts();

    }*/

    public LiveData<Integer> getAllMadePutts(int minDist, int maxDist) {


        return puttDao.getAllMadePutts(minDist, maxDist);}




    public LiveData<Integer> getMadePutts(long timeRange, int minDist, int maxDist) {


        return puttDao.getMadePutts(timeRange, minDist, maxDist);}





    public LiveData<Integer> getPuttsOverMinDistByDate(long timeRange, int minDist, int maxDist) {


        return puttDao.getPuttsOverMinDistByDate(timeRange, minDist, maxDist);}







     // read database for distance, time range, and slope

    public LiveData<Integer> getMadePuttsWithSlope(long timeRange, int minDist, int maxDist, String slopeDirection) {


        return puttDao.getMadePuttsWithSlope(timeRange, minDist, maxDist, slopeDirection);}





    public LiveData<Integer> getPuttsOverMinDistByDateWithSlope(long timeRange, int minDist, int maxDist, String slopeDirection) {


        return puttDao.getPuttsOverMinDistByDateWithSlope(timeRange, minDist, maxDist, slopeDirection);}



    //read database for distance, time range, and stimp

    public LiveData<Integer> getMadePuttsWithStimp(long timeRange, int minDist, int maxDist, double stimpMin, double stimpMax) {


        return puttDao.getMadePuttsWithStimp(timeRange, minDist, maxDist, stimpMin, stimpMax);}



    public LiveData<Integer> getPuttsOverMinDistByDateWithStimp(long timeRange, int minDist, int maxDist, double stimpMin, double stimpMax) {


        return puttDao.getPuttsOverMinDistByDateWithStimp(timeRange,minDist,maxDist,stimpMin,stimpMax);}



    // read database for distance, time range, slope, and stimp

    public LiveData<Integer> getMadePuttsWithSlopeWithStimp(long timeRange, int minDist, int maxDist, String slopeDirection, double stimpMin, double stimpMax) {


        return puttDao.getMadePuttsWithSlopeWithStimp(timeRange, minDist, maxDist, slopeDirection, stimpMin, stimpMax);}





    public LiveData<Integer> getPuttsOverMinDistByDateWithSlopeWithStimp(long timeRange, int minDist, int maxDist, String slopeDirection, double stimpMin, double stimpMax) {


        return puttDao.getPuttsOverMinDistByDateWithSlopeWithStimp(timeRange, minDist, maxDist, slopeDirection, stimpMin, stimpMax);}



    // read made status of last entry

    // read database for distance, time range, slope, and stimp

    public LiveData<Integer> getLastEntryMadeStatus() {


        return puttDao.getLastEntryMadeStatus();}






    // read velocity of last made putt

    public LiveData<Double> getVelocityLastMade() {


        return puttDao.getVelocityLastMade();}


    // read average velocity of made putts

    public LiveData<Float> getAverageVelocityPuttsMade() {


        return puttDao.getAverageVelocityPuttsMade();}




    public LiveData<Long> getTimeMaxVelocity(long timePuttStart) {


        return puttDao.getTimeMaxVelocity(timePuttStart);}



    public LiveData<Long> getTimePuttStart() {


        return puttDao.getTimePuttStart();}



    public LiveData<Long> getTimePuttEnd() {


        return puttDao.getTimePuttEnd();}




    public LiveData<Float> getAccelYAverage(long timePuttStart) {


        return puttDao.getAccelYAverage(timePuttStart);}




    public LiveData<Integer> getLastSessionNum() {


        return puttDao.getLastSessionNum();}


















    ////


    private static class InsertPuttAsyncTask extends AsyncTask<PuttData,Void,Void>{

        private puttDataDao puttDao;

        private InsertPuttAsyncTask(puttDataDao puttDao){

            this.puttDao = puttDao;
        }

        @Override
        protected Void doInBackground(PuttData... puttData) {

            puttDao.insert(puttData[0]);

            return null;

        }
    }

    private static class UpdatePuttAsyncTask extends AsyncTask<PuttData,Void,Void>{

        private puttDataDao puttDao;

        private UpdatePuttAsyncTask(puttDataDao puttDao){

            this.puttDao = puttDao;
        }

        @Override
        protected Void doInBackground(PuttData... puttData) {

            puttDao.update(puttData[0]);

            return null;

        }
    }

    private static class DeletePuttAsyncTask extends AsyncTask<PuttData,Void,Void>{

        private puttDataDao puttDao;

        private DeletePuttAsyncTask(puttDataDao puttDao){

            this.puttDao = puttDao;
        }

        @Override
        protected Void doInBackground(PuttData... puttData) {

            puttDao.delete(puttData[0]);

            return null;

        }
    }


    private static class DeleteAllPuttAsyncTask extends AsyncTask<Void,Void,Void>{

        private puttDataDao puttDao;

        private DeleteAllPuttAsyncTask(puttDataDao puttDao){

            this.puttDao = puttDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            puttDao.deleteAllPutts();

            return null;

        }
    }

    private static class UpdateLastPuttMadeAsyncTask extends AsyncTask<PuttData,Void,Void>{

        private puttDataDao puttDao;

        private UpdateLastPuttMadeAsyncTask(puttDataDao puttDao){

            this.puttDao = puttDao;
        }

        @Override
        protected Void doInBackground(PuttData... puttData) {

            puttDao.updateLastPuttMade();

            return null;

        }
    }


}
