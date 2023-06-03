package com.handitan.personalappointmentscheduler.presentation.ui.update_Appointment

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.handitan.personalappointmentscheduler.presentation.ui.update_Appointment.components.UpdateAppointmentContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateAppointmentScreen(
    updateApptViewModel:UpdateAppointmentViewModel = hiltViewModel(),
    apptId:Long,
    navigateBack:()->Unit
) {
    LaunchedEffect(Unit) {
        updateApptViewModel.getAppointment(apptId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Update Appointment")},
                navigationIcon = {
                    IconButton(onClick = { navigateBack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = {
            UpdateAppointmentContent(
                it,
                updateApptViewModel,
                navigateBack)
        }
    )
}