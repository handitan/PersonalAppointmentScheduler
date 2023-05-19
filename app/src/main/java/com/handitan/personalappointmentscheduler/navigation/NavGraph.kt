package com.handitan.personalappointmentscheduler.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.handitan.personalappointmentscheduler.presentation.ui.appointmentList.AppointmentListScreen

@Composable
fun NavGraph(
    navController:NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.AppointmentListScreen.route
    ) {
        composable(
            route = Screen.AppointmentListScreen.route
        ) {
            AppointmentListScreen()
        }
    }
}