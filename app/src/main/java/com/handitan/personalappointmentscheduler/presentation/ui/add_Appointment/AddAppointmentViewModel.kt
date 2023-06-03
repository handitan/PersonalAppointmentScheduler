package com.handitan.personalappointmentscheduler.presentation.ui.add_Appointment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.handitan.personalappointmentscheduler.core.Utilities
import com.handitan.personalappointmentscheduler.data.model.City
import com.handitan.personalappointmentscheduler.data.repository.AppointmentSchedulerRepository
import com.handitan.personalappointmentscheduler.presentation.ui.update_Appointment.model.AppointmentViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddAppointmentViewModel @Inject constructor(
    private val repo: AppointmentSchedulerRepository
):ViewModel() {

    var currentApptViewData by mutableStateOf(AppointmentViewData(0,"",0,"",0,0,0))
        private set

    var cityList = mutableStateListOf<City>()
        private set

    var savedDateStr by mutableStateOf("")
        private set

    init {
        getCities()
    }

    private fun getCities() {
        viewModelScope.launch(Dispatchers.IO) {
            val citiesData = repo.getCities()
            withContext(Dispatchers.Main) {
                cityList.clear()
                cityList.addAll(citiesData)
            }
        }
    }

    fun updateDescription(description:String) {
        currentApptViewData = currentApptViewData.copy(description = description)
    }

    fun updateCityName(cityName:String) {
        val foundCity = cityList.findLast { it -> it.name == cityName }
        if (foundCity != null) {
            currentApptViewData =
                currentApptViewData.copy(cityName = foundCity.name, cityId = foundCity.id)
        }
    }

    fun addAppointment() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addAppointment(currentApptViewData.toAppointment())
        }
    }

    fun updateAppointmentDate(date:Long){
        viewModelScope.launch(Dispatchers.IO) {
            currentApptViewData =
                currentApptViewData.copy(date = date)
            withContext(Dispatchers.Main) {
                savedDateStr = Utilities.changeToDateString(date)
            }
        }
    }

    fun updateAppointmentTime(hour:Int,minute:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            currentApptViewData =
                currentApptViewData.copy(timeHour = hour, timeMinute = minute)
        }
    }
}