package com.handitan.personalappointmentscheduler.data.model

data class AppointmentData(
    val id:Long,
    val description:String,
    val cityId:Long,
    val cityName:String,
    val dateTime:Long
)
