package com.example.pdp_kurs.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Mentor {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @ColumnInfo(name = "men_ism")
    var ment_name: String? = null

    @ColumnInfo(name = "men_famil")
    var ment_famil: String? = null

    @ColumnInfo(name = "men_otch")
    var ment_otchest: String? = null

    @ColumnInfo(name = "kurs_id")
    var couseId: Int? = null

    @ColumnInfo(name = "guruh_id")
    var guruhId: String? = null
}