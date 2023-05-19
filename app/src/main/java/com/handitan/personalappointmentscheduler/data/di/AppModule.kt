package com.handitan.personalappointmentscheduler.data.di

import android.content.Context
import com.handitan.personalappointmentscheduler.data.dao.AppointmentDao
import com.handitan.personalappointmentscheduler.data.dao.CityDao
import com.handitan.personalappointmentscheduler.data.database.AppointmentSchedulerDb
import com.handitan.personalappointmentscheduler.data.repository.AppointmentSchedulerRepository
import com.handitan.personalappointmentscheduler.data.repository.AppointmentSchedulerRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideApptSchedulerDb(@ApplicationContext context:Context):AppointmentSchedulerDb {
        return AppointmentSchedulerDb.getInstance(context, CoroutineScope(SupervisorJob()+Dispatchers.Default) )
    }

    @Provides
    fun provideAppointmentDao(apptSchedulerDb:AppointmentSchedulerDb):AppointmentDao {
        return apptSchedulerDb.AppointmentDao()
    }

    @Provides
    fun provideCityDao(apptSchedulerDb: AppointmentSchedulerDb):CityDao {
        return apptSchedulerDb.CityDao()
    }

    @Provides
    fun provideApptSchedulerRepository(
        appointmentDao: AppointmentDao,
        cityDao: CityDao,
        apptSchedulerDb:AppointmentSchedulerDb
    ):AppointmentSchedulerRepository {
        return AppointmentSchedulerRepositoryImpl(appointmentDao,cityDao,apptSchedulerDb)
    }
}