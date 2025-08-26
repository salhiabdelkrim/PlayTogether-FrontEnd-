package com.example.playtogether.db

import androidx.room.Dao
import androidx.room.Query
import com.example.playtogether.model.Sport

@Dao
interface SportDao {
    @Query("SELECT * FROM Sport")
    suspend fun getAllSports(): List<Sport>
}