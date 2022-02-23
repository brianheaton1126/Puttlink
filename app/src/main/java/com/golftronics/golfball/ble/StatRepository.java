package com.golftronics.golfball.ble;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class StatRepository {

    private statDataDao statDao;
    //private LiveData<List<StatData>> allPutts;
    //private LiveData<Integer> allLongPutts;


    public StatRepository(Application application){

        StatDatabase database = StatDatabase.getInstance(application);
        statDao = database.statdatadao();
        //allPutts = puttDao.getAllPutts();
        //allLongPutts = puttDao.getAllLongPutts();
    }

    public void insert(StatData statsegment){

        new InsertStatAsyncTask(statDao).execute(statsegment);

    }

    public void update(StatData statsegment){

        new UpdateStatAsyncTask(statDao).execute(statsegment);

    }

    public void delete(StatData statsegment){

        new DeleteStatAsyncTask(statDao).execute(statsegment);

    }

    public void deleteAllStats(PuttData statsegment){

        new DeleteAllStatAsyncTask(statDao).execute();


    }

    public LiveData<Integer> statTotalAttemptsBySessionByDistanceRange(int sessionNumber, int statMinDist, int statMaxDist){

        return statDao.statTotalAttemptsBySessionByDistanceRange(sessionNumber, statMinDist, statMaxDist);
    }


    public LiveData<Integer> statTotalMadeBySessionByDistanceRange(int sessionNumber, int statMinDist, int statMaxDist){

        return statDao.statTotalMadeBySessionByDistanceRange(sessionNumber, statMinDist, statMaxDist);
    }




    public LiveData<Integer> statTotalAttemptsByDistanceRange(int statMinDist, int statMaxDist){

        return statDao.statTotalAttemptsByDistanceRange(statMinDist, statMaxDist);
    }


    public LiveData<Integer> statTotalMadeByDistanceRange(int statMinDist, int statMaxDist){

        return statDao.statTotalMadeByDistanceRange(statMinDist, statMaxDist);
    }


    public LiveData<Integer> statTotalAttemptsByDistanceRangeByDateRange(int statMinDist, int statMaxDist, long statDateMin, long statDateMax ){

        return statDao.statTotalAttemptsByDistanceRangeByDateRange(statMinDist, statMaxDist, statDateMin, statDateMax);
    }


    public LiveData<Integer> statTotalMadeByDistanceRangeByDateRange(int statMinDist, int statMaxDist, long statDateMin, long statDateMax ){

        return statDao.statTotalMadeByDistanceRangeByDateRange(statMinDist, statMaxDist, statDateMin, statDateMax);
    }

    public LiveData<Double> statPracticeTimeByDistanceRange(int statMinDist, int statMaxDist){

        return statDao.statPracticeTimeByDistanceRange(statMinDist, statMaxDist);
    }


    public LiveData<Double> statPracticeTimeByDistanceRangeByDateRange(int statMinDist, int statMaxDist, long statDateMin, long statDateMax){

        return statDao.statPracticeTimeByDistanceRangeByDateRange(statMinDist, statMaxDist, statDateMin, statDateMax);
    }




    ////


    private static class InsertStatAsyncTask extends AsyncTask<StatData,Void,Void>{

        private statDataDao statDao;

        private InsertStatAsyncTask(statDataDao statDao){

            this.statDao = statDao;
        }

        @Override
        protected Void doInBackground(StatData... statData) {

            statDao.insert(statData[0]);

            return null;

        }
    }

    private static class UpdateStatAsyncTask extends AsyncTask<StatData,Void,Void>{

        private statDataDao statDao;

        private UpdateStatAsyncTask(statDataDao statDao){

            this.statDao = statDao;
        }

        @Override
        protected Void doInBackground(StatData... statData) {

            statDao.update(statData[0]);

            return null;

        }
    }

    private static class DeleteStatAsyncTask extends AsyncTask<StatData,Void,Void>{

        private statDataDao statDao;

        private DeleteStatAsyncTask(statDataDao statDao){

            this.statDao = statDao;
        }

        @Override
        protected Void doInBackground(StatData... statData) {

            statDao.delete(statData[0]);

            return null;

        }
    }


    private static class DeleteAllStatAsyncTask extends AsyncTask<Void,Void,Void>{

        private statDataDao statDao;

        private DeleteAllStatAsyncTask(statDataDao statDao){

            this.statDao = statDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            statDao.deleteAllStats();

            return null;

        }
    }




}
