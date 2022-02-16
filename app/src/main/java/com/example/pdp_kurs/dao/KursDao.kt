package com.example.pdp_kurs.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pdp_kurs.entity.Kurs

@Dao
interface KursDao {

    @Query("select * from kurs")
    fun getAllNews(): List<Kurs>

    @Insert
    fun addNews(kurs: Kurs)
}