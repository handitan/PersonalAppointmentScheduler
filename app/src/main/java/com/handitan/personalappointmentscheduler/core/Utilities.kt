package com.handitan.personalappointmentscheduler.core

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class Utilities {
    companion object {
        fun changeToDateString(dateVal:Long):String {
            val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
            return sdf.format(dateVal)
        }

        fun verifyApptFieldsFilledOut(description: String, cityName:String, apptDateStr:String, apptHour:Int):Boolean {
            return (!(description.isBlank() && description.isEmpty()) &&
                    !(cityName.isBlank() && cityName.isEmpty()) &&
                    !(apptDateStr.isBlank() && apptDateStr.isEmpty()) &&
                    !(apptHour == 0))
        }
    }
}
