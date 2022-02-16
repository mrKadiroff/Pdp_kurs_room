package com.example.pdp_kurs.dao

import androidx.room.*
import com.example.pdp_kurs.entity.Guruh
import com.example.pdp_kurs.entity.Mentor


@Dao
interface GuruhDao {

    @Query("select * from guruh")
    fun getAllGroup(): List<Guruh>

    @Insert
    fun addGuruh(guruh: Guruh)

    @Delete
    fun deleteGuruh(guruh: Guruh)

    @Update
    fun updateGuruh(guruh: Guruh)
}