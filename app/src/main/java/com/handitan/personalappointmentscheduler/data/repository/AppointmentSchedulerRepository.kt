package com.handitan.personalappointmentscheduler.data.repository

import com.handitan.personalappointmentscheduler.data.model.Appointment
import com.handitan.personalappointmentscheduler.data.model.AppointmentData

interface AppointmentSchedulerRepository {
    suspend fun getAllAppointments():List<AppointmentData>
    suspend fun addAppointment(newAppt:Appointment)
    suspend fun updateAppointment(updateAppt:Appointment)
    suspend fun deleteAppointment(removedAppt:Appointment)
}