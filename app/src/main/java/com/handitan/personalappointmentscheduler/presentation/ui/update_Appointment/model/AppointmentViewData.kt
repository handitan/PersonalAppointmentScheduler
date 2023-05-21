package com.handitan.personalappointmentscheduler.presentation.ui.update_Appointment.model

import com.handitan.personalappointmentscheduler.data.model.Appointment

data class AppointmentViewData(
    var id:Long,
    var description:String,
    var cityId:Long,
    var cityName:String,
    var date:Long,
    var timeHour:Int,
    var timeMinute:Int
) {
    fun modify(id:Long,description: String,cityId: Long,cityName: String, date: Long, hour:Int, minute:Int) {
        this.id = id
        this.description = description
        this.cityId = cityId
        this.cityName = cityName
        this.date = date
        this.timeHour = hour
        this.timeMinute = minute
    }

    fun toAppointment():Appointment {
        return Appointment(this.id,this.description,this.cityId,this.date,this.timeHour,this.timeMinute)
    }
}