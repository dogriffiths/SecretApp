package com.aspenshore.secretapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Message.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MessageDao messageDao();
}
