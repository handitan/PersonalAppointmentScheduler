package com.handitan.personalappointmentscheduler.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.handitan.personalappointmentscheduler.data.dao.AppointmentDao
import com.handitan.personalappointmentscheduler.data.dao.CityDao
import com.handitan.personalappointmentscheduler.data.model.Appointment
import com.handitan.personalappointmentscheduler.data.model.City
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Appointment::class,City::class], version = 2)
abstract class TestAppointmentSchedulerDb : RoomDatabase(){
    abstract fun AppointmentDao():AppointmentDao
    abstract fun CityDao():CityDao

    private class AppointmentSchedulerDbCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            instance?.let { database ->
                scope.launch {
                    val cityDao = database.CityDao()

                    val city1 = City(0,"Austin")
                    val city2 = City(0,"Baltimore")
                    val city3 = City(0, "Cleveland")
                    val city4 = City(0,"Dallas")
                    val city5 = City(0,"El Paso")
                    val city6 = City(0,"Fresno")
                    val city7 = City(0,"Georgetown")
                    cityDao.insert(city1)
                    cityDao.insert(city2)
                    cityDao.insert(city3)
                    cityDao.insert(city4)
                    cityDao.insert(city5)
                    cityDao.insert(city6)
                    cityDao.insert(city7)

                }

            }
        }
    }

    companion object {
        private const val DB_NAME = "TestAppointmentScheduler-db"
        @Volatile
        private var instance:TestAppointmentSchedulerDb? = null

        fun getInstance(
            context:Context,
            scope:CoroutineScope
        ):TestAppointmentSchedulerDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context,scope).also {instance = it}
            }
        }

        private fun buildDatabase(context:Context,scope:CoroutineScope):TestAppointmentSchedulerDb {
            return Room.inMemoryDatabaseBuilder(
                context,
                TestAppointmentSchedulerDb::class.java
            ).fallbackToDestructiveMigration()
                .addCallback(AppointmentSchedulerDbCallback(scope))
                .build()
        }
    }
}