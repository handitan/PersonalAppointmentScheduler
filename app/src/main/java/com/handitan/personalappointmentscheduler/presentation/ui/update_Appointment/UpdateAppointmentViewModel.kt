package com.handitan.personalappointmentscheduler.presentation.ui.update_Appointment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.handitan.personalappointmentscheduler.data.model.AppointmentData
import com.handitan.personalappointmentscheduler.data.model.City
import com.handitan.personalappointmentscheduler.data.repository.AppointmentSchedulerRepository
import com.handitan.personalappointmentscheduler.presentation.ui.update_Appointment.model.AppointmentViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UpdateAppointmentViewModel @Inject constructor(
    private val repo:AppointmentSchedulerRepository
):ViewModel() {

    var currentApptViewData by mutableStateOf(AppointmentViewData(0,"",0,"",0))
        private set

    var cityList = mutableStateListOf<City>()
        private set

    fun getAppointment(apptId:Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val apptData = repo.getAppointment(apptId)
            withContext(Dispatchers.Main) {
                currentApptViewData.modify(apptData.id,apptData.description,apptData.cityId,apptData.cityName,apptData.dateTime)
            }
        }
    }

    fun getCities() {
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
        var foundCity = cityList.findLast { it -> it.name == cityName }
        if (foundCity != null) {
            currentApptViewData =
                currentApptViewData.copy(cityName = foundCity.name, cityId = foundCity.id)
        }
    }

    fun updateAppointment() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateAppointment(currentApptViewData.toAppointment())
        }
    }
}