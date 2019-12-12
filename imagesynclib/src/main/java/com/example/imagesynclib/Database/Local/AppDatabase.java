package com.example.imagesynclib.Database.Local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {mGeneric.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract GenericDao genericDao();
}