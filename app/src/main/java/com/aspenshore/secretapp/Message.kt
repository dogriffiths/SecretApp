package com.aspenshore.secretapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Message(
    @PrimaryKey(autoGenerate = true) val uid: Int?,
    @ColumnInfo(name = "contents") val contents: String?
)
