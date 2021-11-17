package com.example.taskmaster;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 2)
public abstract class TaskDatabase extends RoomDatabase {

//    private static final String DB_NAME = "repoDatabase.db";
//    private static volatile RepoDatabase instance;
//
//    static synchronized RepoDatabase getInstance(Context context) {
//        if (instance == null) {
//            instance = create(context);
//        }
//        return instance;
//    }
//
//    private RepoDatabase() {};
//
//    private static RepoDatabase create(final Context context) {
//        return Room.databaseBuilder(
//                context,
//                RepoDatabase.class,
//                DB_NAME).build();
//    }

    public abstract TaskDao taskDao();
}