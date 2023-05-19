package com.handitan.personalappointmentscheduler.data.repository

import com.handitan.personalappointmentscheduler.data.dao.AppointmentDao
import com.handitan.personalappointmentscheduler.data.dao.CityDao
import com.handitan.personalappointmentscheduler.data.database.AppointmentSchedulerDb
import com.handitan.personalappointmentscheduler.data.model.Appointment
import com.handitan.personalappointmentscheduler.data.model.AppointmentData

class AppointmentSchedulerRepositoryImpl(
    private val apptDao:AppointmentDao,
    private val cityDao: CityDao,
    private val apptSchedulerDb: AppointmentSchedulerDb
) : AppointmentSchedulerRepository {

    override suspend fun getAllAppointments():List<AppointmentData> {
        return apptDao.getAppointments()
    }

    override suspend fun addAppointment(newAppt: Appointment) {
        apptDao.insert(newAppt)
    }

    override suspend fun updateAppointment(updateAppt: Appointment) {
        apptDao.update(updateAppt)
    }

    override suspend fun deleteAppointment(removedAppt: Appointment) {
        apptDao.delete(removedAppt)
    }

}