package com.example.pdp_kurs.dao

import androidx.room.*
import com.example.pdp_kurs.entity.Kurs
import com.example.pdp_kurs.entity.Mentor

@Dao
interface MentorDao {

    @Query("select * from mentor")
    fun getAllMentor(): List<Mentor>

    @Insert
    fun addMentor(mentor: Mentor)

    @Query(
        "SELECT * FROM kurs " +
                "JOIN mentor ON kurs.id = mentor.kurs_id"
    )
    fun loadKursAndMentorNames(): Map<Kurs, List<Mentor>>

    @Delete
    fun deleteMentor(mentor: Mentor)

    @Query("select * from mentor where id=:id")
    fun getMentorById(id:Int) : Mentor

    @Query("select id from mentor where men_famil=:surname and men_ism=:name")
    fun getMentorByTitleId(surname:String, name: String) : Int

    @Update
    fun updateMentor(mentor: Mentor)

//    @Query("SELECT * FROM mentor WHERE id = mentor.kurs_id")
//    fun getAllMentorsByKursId(id: Int): List<Mentor>
}