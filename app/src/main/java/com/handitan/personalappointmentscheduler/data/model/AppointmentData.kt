package com.handitan.personalappointmentscheduler.data.model

data class AppointmentData(
    val id:Long,
    val description:String,
    val cityId:Long,
    val cityName:String,
    val date:Long,
    val hour:Int,
    val minute:Int
) {
    fun toAppointment():Appointment{
        return Appointment(
            id,
            description,
            cityId,
            date,
            hour,
            minute)
    }
}
