package com.example.pdp_kurs.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Kurs: Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @ColumnInfo(name = "kurs_name")
    var kr_name: String? = null

    @ColumnInfo(name = "kurs_description")
    var kr_des: String? = null
}