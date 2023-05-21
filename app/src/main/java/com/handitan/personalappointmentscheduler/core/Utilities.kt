package com.handitan.personalappointmentscheduler.core

import java.text.SimpleDateFormat
import java.util.Locale

class Utilities {
    companion object {
        fun changeToDateString(dateVal:Long):String {
            val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
            return sdf.format(dateVal)
        }
    }
}
