package com.handitan.personalappointmentscheduler.presentation.ui.appointmentList.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.handitan.personalappointmentscheduler.core.Utilities
import com.handitan.personalappointmentscheduler.data.model.AppointmentData

@Composable
fun AppointmentCard(
    currentAppt:AppointmentData,
    navigateToUpdateApptScreen:(Long)->Unit
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 5.dp, start = 8.dp, end = 8.dp)
        .clickable {
            navigateToUpdateApptScreen(currentAppt.id)
        },
        elevation = CardDefaults.cardElevation()
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = currentAppt.description,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis)
            Text(text = "Location: ${currentAppt.cityName}",
                modifier = Modifier.padding(top = 5.dp))
            Text(text = "Date: ${Utilities.changeToDateString(currentAppt.date)}")
            Text(text = "Time: ${Utilities.convertTimeToString(currentAppt.hour,currentAppt.minute)}")
        }
    }
}