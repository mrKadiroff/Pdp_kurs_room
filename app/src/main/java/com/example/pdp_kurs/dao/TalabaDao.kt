package com.example.pdp_kurs.dao

import androidx.room.*
import com.example.pdp_kurs.entity.Guruh
import com.example.pdp_kurs.entity.Mentor
import com.example.pdp_kurs.entity.Talaba


@Dao
interface TalabaDao {

    @Query("select * from talaba")
    fun getAllTalaba(): List<Talaba>

    @Insert
    fun addTalaba(talaba: Talaba)

    @Delete
    fun deleteTalaba(talaba: Talaba)

    @Update
    fun updateTalaba(talaba: Talaba)
}