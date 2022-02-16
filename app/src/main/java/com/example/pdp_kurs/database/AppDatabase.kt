package com.example.pdp_kurs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pdp_kurs.dao.GuruhDao
import com.example.pdp_kurs.dao.KursDao
import com.example.pdp_kurs.dao.MentorDao
import com.example.pdp_kurs.dao.TalabaDao
import com.example.pdp_kurs.entity.Guruh
import com.example.pdp_kurs.entity.Kurs
import com.example.pdp_kurs.entity.Mentor
import com.example.pdp_kurs.entity.Talaba

@Database(entities = [Kurs::class,Mentor::class,Guruh::class,Talaba::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun kursDao(): KursDao
    abstract fun mentorDao(): MentorDao
    abstract fun guruhDao(): GuruhDao
    abstract fun talabaDao(): TalabaDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (instance == null){
                instance = Room.databaseBuilder(context,AppDatabase::class.java,"pdp.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}