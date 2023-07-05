package com.handitan.personalappointmentscheduler.data.dao

import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.handitan.personalappointmentscheduler.data.database.TestAppointmentSchedulerDb
import com.handitan.personalappointmentscheduler.data.model.Appointment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.AfterClass
import org.junit.Assert
import org.junit.Before
import org.junit.BeforeClass
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import java.io.IOException
import java.util.Calendar

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class AppointmentDaoTest {
    companion object {
        private lateinit var database:TestAppointmentSchedulerDb

        @BeforeClass
        @JvmStatic
        fun setUpClass() {
            Log.i("TESTCITY", "BeforeClass")
            val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
            database = TestAppointmentSchedulerDb.getInstance(ApplicationProvider.getApplicationContext(),
                scope)
            database.setupTestPrepopulateDB()
        }

        @AfterClass
        @JvmStatic
        @Throws(IOException::class)
        fun tearDownClass() {
            Log.i("TESTCITY", "AfterClass")
            database.close()
        }
    }

    @Test
    fun test1_queryAllCity_allCitiesWereReturnedCorrectly() = runBlocking {
        val myCities = database.CityDao().getCities()
        val cityList = listOf("Austin",
                            "Baltimore",
                            "Cleveland",
                            "Dallas",
                            "El Paso",
                            "Fresno",
                            "Georgetown")
        for (city in myCities) {
            if (!cityList.contains(city.name)) {
                assertThat("No matching city is found: ${city.name}", false)
            }
        }
    }

    @Test
    fun test2_addNewAppointment_appointmentWasAddedCorrectly() = runBlocking {
        val descriptionVal = "TEST123"
        val currDateTime = Calendar.getInstance().timeInMillis
        val hour = 13
        val minute = 0
        var cityID = 1L

        val newAppointment = Appointment(
            0,descriptionVal,cityID,currDateTime,hour,minute
        )

        database.AppointmentDao().insert(newAppointment)

        database.AppointmentDao().getAppointments().first().let {
            if (it.isNotEmpty()) {
                val appt = it[0]
                assertThat("Description doesn't match",descriptionVal == appt.description)
                assertThat("Date in ms doesn't match",currDateTime == appt.date)
                assertThat("City ID doesn't match",cityID == appt.cityId)
                assertThat("Hour doesn't match",hour == appt.hour)
                assertThat("Minute doesn't match",minute == appt.minute)

                database.AppointmentDao().delete(appt.toAppointment())
            } else {
                assertThat("Appointment list shouldn't be empty",false)
            }
        }
    }

    @Test
    fun test3_updateExistingAppointment_apptWasUpdatedSuccessfully() = runBlocking {
        val updatedDesc = "UPDATED12345"

        database.AppointmentDao().insert(Appointment(
            0, "ABC123", 2L, Calendar.getInstance().timeInMillis, 1, 10
        ))

        database.AppointmentDao().getAppointments().first().let {
            val apptData = it[0]
            database.AppointmentDao().update(Appointment(
                apptData.id,
                updatedDesc,
                apptData.cityId,
                apptData.date,
                apptData.hour,
                apptData.minute
            ))
        }

        database.AppointmentDao().getAppointments().first().let {
            val apptData = it[0]
            assertThat("Updated description doesn't match: ${apptData.description}", apptData.description == updatedDesc)
            database.AppointmentDao().delete(apptData.toAppointment())
        }
    }

    @Test
    fun test4_deleteExistingAppointments_appointmentListIsEmpty() = runBlocking {
        database.AppointmentDao().insert(Appointment(
            0, "TEST12345", 2L, Calendar.getInstance().timeInMillis, 14, 15
        ))

        database.AppointmentDao().insert(Appointment(
            0, "TEST6789", 3L, Calendar.getInstance().timeInMillis, 15, 30
        ))

        database.AppointmentDao().getAppointments().first().let {
            if (it.size != 2) {
                assertThat("List should be equal to 2: ${it.size}", false)
            }
            val mutableList = it.toMutableList()
            if (mutableList.isNotEmpty()) {
               while (mutableList.size > 0) {
                   val apptData = mutableList.removeLast()
                   database.AppointmentDao().delete(apptData.toAppointment())
               }
            } else {
                assertThat("Appointment list shouldn't be empty", false)
            }
        }

        database.AppointmentDao().getAppointments().first().let {
            assertThat("Appointment list should be empty", it.isEmpty())
        }

    }

}