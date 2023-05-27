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

        fun convertTimeToString(hourVal:Int,minuteVal:Int):String {
            if (hourVal == 0) return ""

            var minuteStr = "$minuteVal"
            if (minuteVal < 10) {
                minuteStr = "0$minuteVal"
            }

            var hourStr = "$hourVal"
            if (hourVal > 12) {
                hourStr = "${hourVal-12}"
            }

            var timeIndicator = "PM"
            if (hourVal < 12) {
                timeIndicator = "AM"
            }

            return "$hourStr:$minuteStr $timeIndicator"
        }
    }
}
