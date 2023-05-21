package com.handitan.personalappointmentscheduler.presentation.ui.appointmentList

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.handitan.personalappointmentscheduler.data.model.AppointmentData
import com.handitan.personalappointmentscheduler.data.repository.AppointmentSchedulerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AppointmentListViewModel @Inject constructor(
    private val repo:AppointmentSchedulerRepository
):ViewModel() {
    var appointmentList = mutableStateListOf<AppointmentData>()
        private set

    fun deleteAppointment(apptData:AppointmentData) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteAppointment(apptData.toAppointment())
            withContext(Dispatchers.Main) {
                appointmentList.remove(apptData)
            }
        }
    }

    fun getAllAppointments() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = repo.getAllAppointments()
                withContext(Dispatchers.Main) {
                    data?.let {
                        appointmentList.clear()
                        appointmentList.addAll(data)
                    }
                }
            }
            catch (e:Exception) {

            }
        }
    }
}