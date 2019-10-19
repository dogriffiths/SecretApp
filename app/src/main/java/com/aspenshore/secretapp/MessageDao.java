package com.aspenshore.secretapp;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface MessageDao {
    @Insert
    void insertAll(Message... messages);
}
