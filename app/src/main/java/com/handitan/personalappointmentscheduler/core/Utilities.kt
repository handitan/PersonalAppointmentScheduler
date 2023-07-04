package com.handitan.personalappointmentscheduler.core

import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.handitan.personalappointmentscheduler.R
import java.text.SimpleDateFormat
import java.util.Date
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

        fun createDatePicker(setDate :Long,titleText:String):MaterialDatePicker<Long> {
            return MaterialDatePicker
                .Builder
                .datePicker()
                .setTheme(R.style.MaterialCalendarTheme)
                .setSelection(setDate)
                .setTitleText(titleText)
                .build()
        }

        fun createTimePicker(hour:Int, minute:Int, titleText:String):MaterialTimePicker {
            return MaterialTimePicker
                .Builder()
                .setTheme(R.style.MaterialTimePickerTheme)
                .setHour(hour)
                .setMinute(minute)
                .setTitleText(titleText)
                .build()
        }
    }
}
