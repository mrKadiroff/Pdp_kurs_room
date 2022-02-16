package com.example.pdp_kurs.entity

import androidx.room.*
import java.io.Serializable
import java.util.*


@Entity
class Guruh : Serializable{
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @ColumnInfo(name = "gr_nomi")
    var gr_name: String? = null

    @ColumnInfo(name = "gr_kurs")
    var gr_kurs_id: Int? = null

    @ColumnInfo(name = "gr_mentors")
    var gr_mentor: String? = null

    @ColumnInfo(name = "gr_vaqt")
    var gr_time: String? = null

    @ColumnInfo(name = "gr_day")
    var gr_day: String? = null

    @ColumnInfo(name = "gr_open")
    var gr_open: String? = null


}