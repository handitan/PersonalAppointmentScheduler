package com.handitan.personalappointmentscheduler.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.handitan.personalappointmentscheduler.core.Constants.Companion.CITY_TABLE
import com.handitan.personalappointmentscheduler.data.model.City

@Dao
interface CityDao {
    @Query("SELECT * FROM $CITY_TABLE")
    fun getCities():List<City>

    @Insert
    fun insert(city:City)

    @Insert
    fun insertAll(cities: List<City>)



}