package com.example.pdp_kurs.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Talaba : Serializable{
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @ColumnInfo(name = "tal_fam")
    var tl_surname: String? = null

    @ColumnInfo(name = "tal_ism")
    var tl_name: String? = null

    @ColumnInfo(name = "tal_otch")
    var tl_otchestva: String? = null

    @ColumnInfo(name = "sana")
    var tl_sana: String? = null

    @ColumnInfo(name = "tal_guruh")
    var tl_group: Int? = null
}