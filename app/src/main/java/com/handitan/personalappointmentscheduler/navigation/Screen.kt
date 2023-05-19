package com.handitan.personalappointmentscheduler.navigation

import com.handitan.personalappointmentscheduler.core.Constants

sealed class Screen (val route:String) {
    object AppointmentListScreen:Screen(Constants.ALLAPPOINTMENT_SCREEN)
    object UpdateAppointmentScreen:Screen(Constants.UPDATEAPPOINTMENT_SCREEN)
}
