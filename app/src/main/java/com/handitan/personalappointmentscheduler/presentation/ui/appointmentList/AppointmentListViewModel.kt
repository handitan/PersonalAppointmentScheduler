package com.handitan.personalappointmentscheduler.presentation.ui.appointmentList

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewModelScope
import com.handitan.personalappointmentscheduler.data.model.Appointment
import com.handitan.personalappointmentscheduler.data.model.AppointmentData
import com.handitan.personalappointmentscheduler.data.repository.AppointmentSchedulerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class AppointmentListViewModel @Inject constructor(
    private val repo:AppointmentSchedulerRepository
):ViewModel() {

    lateinit var apptListStateFlow:StateFlow<List<AppointmentData>>
        private set

    init {
        setupAllAppointments()
    }

    fun deleteAppointment(apptData:AppointmentData) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteAppointment(apptData.toAppointment())
        }
    }

    private fun setupAllAppointments() {
        apptListStateFlow = repo.getAllAppointments().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
    }
}