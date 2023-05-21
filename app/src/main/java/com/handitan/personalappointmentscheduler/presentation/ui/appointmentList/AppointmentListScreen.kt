package com.handitan.personalappointmentscheduler.presentation.ui.appointmentList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.handitan.personalappointmentscheduler.data.model.AppointmentData
import com.handitan.personalappointmentscheduler.presentation.ui.appointmentList.components.AppointmentCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentListScreen(
    appointmentListViewModel: AppointmentListViewModel = hiltViewModel(),
    navigateToUpdateApptScreen:(Long)->Unit,
    navigateToAddApptScreen:()->Unit
) {
    LaunchedEffect(Unit) {
        appointmentListViewModel.getAllAppointments()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Appointments")},
//                navigationIcon = {
//                    IconButton(onClick = { navigateBack() }) {
//                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
//                    }
//                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                IconButton(onClick = {
                    navigateToAddApptScreen()
                }) {
                    Icon(Icons.Filled.Add, contentDescription = "Add")
                }
            }
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                items(appointmentListViewModel.appointmentList,key = { it.id }) {
                    AppointmentCard(
                        it,
                        navigateToUpdateApptScreen)
                }
            }
        }
    )
}