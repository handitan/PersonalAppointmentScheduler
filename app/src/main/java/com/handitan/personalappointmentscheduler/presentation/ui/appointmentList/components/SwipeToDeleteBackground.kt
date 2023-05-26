package com.handitan.personalappointmentscheduler.presentation.ui.appointmentList.components

import android.graphics.drawable.Icon
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDeleteBackground(dismissState:DismissState) {
    val direction = dismissState.dismissDirection ?: return
    val color by animateColorAsState(
        when (dismissState.targetValue) {
            DismissValue.DismissedToStart -> Color.Red
            else -> {Color.LightGray}
        }
    )
    val alignment = when(direction) {
        DismissDirection.EndToStart -> Alignment.CenterEnd
        else -> Alignment.CenterStart
    }
    val icon = Icons.Filled.Delete

    val scale by animateFloatAsState(
        if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
    )

    Box (
        Modifier
            .fillMaxSize()
            .padding(5.dp)
            .background(color),
        contentAlignment = alignment
        ) {

        Icon(imageVector = icon,
            modifier = Modifier
                .scale(scale)
                .padding(10.dp),
            contentDescription = "Deletion")
    }
}