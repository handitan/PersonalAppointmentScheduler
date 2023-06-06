package com.handitan.personalappointmentscheduler.data.repository

import com.handitan.personalappointmentscheduler.data.dao.AppointmentDao
import com.handitan.personalappointmentscheduler.data.dao.CityDao
import com.handitan.personalappointmentscheduler.data.database.AppointmentSchedulerDb
import com.handitan.personalappointmentscheduler.data.model.Appointment
import com.handitan.personalappointmentscheduler.data.model.AppointmentData
import com.handitan.personalappointmentscheduler.data.model.City
import kotlinx.coroutines.flow.Flow

class AppointmentSchedulerRepositoryImpl(
    private val apptDao:AppointmentDao,
    private val cityDao: CityDao
) : AppointmentSchedulerRepository {

    override fun getAllAppointments():Flow<List<AppointmentData>> {
        return apptDao.getAppointments()
    }

    override suspend fun getAppointment(apptId: Long): AppointmentData {
        return apptDao.getAppointment(apptId)
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

    override suspend fun getCities():List<City> {
        return cityDao.getCities()
    }
}