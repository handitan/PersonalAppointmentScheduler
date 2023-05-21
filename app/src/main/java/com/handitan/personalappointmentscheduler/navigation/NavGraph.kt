package com.handitan.personalappointmentscheduler.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.handitan.personalappointmentscheduler.presentation.ui.add_Appointment.AddAppointmentScreen
import com.handitan.personalappointmentscheduler.presentation.ui.appointmentList.AppointmentListScreen
import com.handitan.personalappointmentscheduler.presentation.ui.update_Appointment.UpdateAppointmentScreen

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
            AppointmentListScreen(
                navigateToUpdateApptScreen = { apptId ->
                    navController.navigate(route = Screen.UpdateAppointmentScreen.route + "/$apptId")
                },
                navigateToAddApptScreen = {
                    navController.navigate(route = Screen.AddAppointmentScreen.route)
                }
            )
        }
        composable(
            route = Screen.AddAppointmentScreen.route
        ) {
            AddAppointmentScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = Screen.UpdateAppointmentScreen.route + "/{apptId}",
            arguments = listOf(
                navArgument("apptId") {
                    type = NavType.LongType
                }
            )
        ) {
            val appointmentId = it.arguments?.getLong("apptId") ?: 1
            UpdateAppointmentScreen(
                apptId = appointmentId,
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}