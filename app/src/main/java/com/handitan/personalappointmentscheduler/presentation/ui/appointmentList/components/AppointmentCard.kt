package com.handitan.personalappointmentscheduler.presentation.ui.appointmentList.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.handitan.personalappointmentscheduler.core.Utilities
import com.handitan.personalappointmentscheduler.data.model.AppointmentData

@Composable
fun AppointmentCard(
    currentAppt:AppointmentData,
    deleteAppt:(AppointmentData)->Unit,
    navigateToUpdateApptScreen:(Long)->Unit
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(15.dp)
        .clickable {
            navigateToUpdateApptScreen(currentAppt.id)
        },
        elevation = CardDefaults.cardElevation()
    ) {
        Column() {
            Text(text = currentAppt.description)
            Text(text = currentAppt.cityName)
            Text(text = Utilities.changeToDateString(currentAppt.date))
            Text(text = currentAppt.hour.toString() + ":" + currentAppt.minute.toString())
            IconButton(
                onClick = {
                    deleteAppt(currentAppt)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete Appointment"
                )
            }
        }
    }
}