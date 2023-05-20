package com.handitan.personalappointmentscheduler.data.repository

import com.handitan.personalappointmentscheduler.data.model.Appointment
import com.handitan.personalappointmentscheduler.data.model.AppointmentData
import com.handitan.personalappointmentscheduler.data.model.City

interface AppointmentSchedulerRepository {
    suspend fun getAllAppointments():List<AppointmentData>
    suspend fun getAppointment(apptId:Long):AppointmentData
    suspend fun addAppointment(newAppt:Appointment)
    suspend fun updateAppointment(updateAppt:Appointment)
    suspend fun deleteAppointment(removedAppt:Appointment)

    suspend fun getCities():List<City>
}