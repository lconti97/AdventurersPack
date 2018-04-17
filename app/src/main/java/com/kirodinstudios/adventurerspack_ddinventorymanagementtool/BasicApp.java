package com.kirodinstudios.adventurerspack_ddinventorymanagementtool;

import android.app.Application;

import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db.AppDatabase;


public class BasicApp extends Application {

    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, mAppExecutors);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase());
    }
}
