package com.handitan.personalappointmentscheduler.presentation.ui.appointmentList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.handitan.personalappointmentscheduler.data.model.AppointmentData
import com.handitan.personalappointmentscheduler.presentation.ui.appointmentList.components.AppointmentCard

@Composable
fun AppointmentListScreen(
    appointmentListViewModel: AppointmentListViewModel = hiltViewModel(),
    navigateToUpdateApptScreen:(Long)->Unit
) {
    LaunchedEffect(Unit) {
        appointmentListViewModel.getAllAppointments()
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
      items(appointmentListViewModel.appointmentList,key = { it.id }) {
          AppointmentCard(
              it,
              navigateToUpdateApptScreen)
      }
    }
}