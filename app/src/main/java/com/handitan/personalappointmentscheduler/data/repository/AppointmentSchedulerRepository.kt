package com.handitan.personalappointmentscheduler.data.repository

import com.handitan.personalappointmentscheduler.data.model.Appointment

interface AppointmentSchedulerRepository {
    suspend fun getAllAppointments():List<Appointment>
    suspend fun addAppointment(newAppt:Appointment)
    suspend fun updateAppointment(updateAppt:Appointment)
    suspend fun deleteAppointment(removedAppt:Appointment)
}