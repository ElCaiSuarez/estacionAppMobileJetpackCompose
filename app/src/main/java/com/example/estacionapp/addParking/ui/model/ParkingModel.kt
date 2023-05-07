package com.example.estacionapp.addParking.ui.model

data class ParkingModel(
    val id: Long = System.currentTimeMillis(),
    val name: String,
    //val locationId: Int,
    //val userId: Int,
    var selected: Boolean = false
)