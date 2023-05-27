package com.handitan.personalappointmentscheduler.presentation.ui.add_Appointment

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
import com.handitan.personalappointmentscheduler.presentation.ui.add_Appointment.components.AddAppointmentContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAppointmentScreen(
    addApptViewModel: AddAppointmentViewModel = hiltViewModel(),
    navigateBack:()->Unit
) {
    LaunchedEffect(Unit) {
        addApptViewModel.getCities()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "New Appointment") },
                navigationIcon = {
                    IconButton(onClick = { navigateBack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = {
            AddAppointmentContent(
                it,
                addApptViewModel,
                navigateBack)
        }
    )
}