package com.handitan.personalappointmentscheduler.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.handitan.personalappointmentscheduler.core.Constants.Companion.APPOINTMENT_TABLE
import com.handitan.personalappointmentscheduler.core.Constants.Companion.CITY_TABLE
import com.handitan.personalappointmentscheduler.data.model.Appointment
import com.handitan.personalappointmentscheduler.data.model.AppointmentData
import kotlinx.coroutines.flow.Flow

@Dao
interface AppointmentDao {
    @Query("SELECT " +
            "A.id," +
            "A.date," +
            "A.description," +
            "A.cityId," +
            "A.hour," +
            "A.minute," +
            "C.name AS cityName " +
            "FROM $APPOINTMENT_TABLE AS A " +
            "INNER JOIN $CITY_TABLE AS C " +
            "ON A.cityId = C.id")
    fun getAppointments(): Flow<List<AppointmentData>>

    @Query("SELECT " +
            "A.id," +
            "A.date," +
            "A.description," +
            "A.cityId," +
            "A.hour," +
            "A.minute," +
            "C.name AS cityName " +
            "FROM $APPOINTMENT_TABLE AS A " +
            "INNER JOIN $CITY_TABLE AS C " +
            "ON A.cityId = C.id WHERE A.id = :apptId")
    fun getAppointment(apptId:Long):AppointmentData

    @Insert
    fun insert(appointment:Appointment)

    @Update
    fun update(appointment: Appointment)

    @Delete
    fun delete(appointment: Appointment)
}