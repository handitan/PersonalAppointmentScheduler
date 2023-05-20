package com.handitan.personalappointmentscheduler.presentation.ui.update_Appointment.model

import com.handitan.personalappointmentscheduler.data.model.Appointment

data class AppointmentViewData(
    var id:Long,
    var description:String,
    var cityId:Long,
    var cityName:String,
    var dateTime:Long
) {
    fun modify(id:Long,description: String,cityId: Long,cityName: String, dateTime: Long) {
        this.id = id
        this.description = description
        this.cityId = cityId
        this.cityName = cityName
        this.dateTime = dateTime
    }

    fun toAppointment():Appointment {
        return Appointment(this.id,this.description,this.cityId,this.dateTime)
    }
}