package com.golftronics.golfball.ble;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = PuttSwingData.class, version = 1)
public abstract class PuttSwingDatabase extends RoomDatabase {

    private static PuttSwingDatabase instance;

    public abstract puttswingDataDao puttswingdatadao();

    public static synchronized PuttSwingDatabase getInstance(Context context){

        if (instance == null){

            instance = Room.databaseBuilder(context.getApplicationContext(),
                    PuttSwingDatabase.class,"puttswing_database")
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

        private puttswingDataDao puttswingdatadao;

        private PopulateDbAsyncTask(PuttSwingDatabase db){

           puttswingdatadao = db.puttswingdatadao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            puttswingdatadao.insert(new PuttSwingData(2221, 1,"Brian", 1,
                    2.2,0));

            puttswingdatadao.insert(new PuttSwingData(2221, 1,"Brian",2,
                    4.4,0));

            puttswingdatadao.insert(new PuttSwingData(2221, 1,"Brian",3,
                    8.8,1));


            return null;
        }
    }
}
