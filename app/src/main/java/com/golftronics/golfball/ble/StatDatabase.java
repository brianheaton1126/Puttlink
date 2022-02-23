package com.golftronics.golfball.ble;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = StatData.class, version = 2)
public abstract class StatDatabase extends RoomDatabase {

    private static StatDatabase instance;

    public abstract statDataDao statdatadao();

    public static synchronized StatDatabase getInstance(Context context){

        if (instance == null){

            instance = Room.databaseBuilder(context.getApplicationContext(),
                    StatDatabase.class,"stat_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static Callback roomCallback = new Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{

        private statDataDao statdatadao;

        private PopulateDbAsyncTask(StatDatabase db){

           statdatadao = db.statdatadao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            statdatadao.insert(new StatData(2221, 1,"Brian",10,
                    2.2,2,1.0,"home"));

            statdatadao.insert(new StatData(2221, 1,"Brian",20,
                    4.4,10,1.5,"home"));

            statdatadao.insert(new StatData(2221, 1,"Brian",30,
                    8.8,15,2.0,"home"));


            return null;
        }
    }
}
