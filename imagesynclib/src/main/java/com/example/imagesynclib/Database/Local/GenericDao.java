package com.example.imagesynclib.Database.Local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GenericDao {
    @Query("SELECT * FROM GENERIC")
    List<mGeneric> getAll();

    @Insert
    void insert(mGeneric generic);

    @Delete
    void delete(mGeneric generic);

    @Update
    void update(mGeneric generic);
}
