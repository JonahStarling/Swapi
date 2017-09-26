package com.jonahstarling.swapi.swobjects

import fragment.StarshipDetails
import fragment.VehicleDetails
import java.util.ArrayList
import java.util.HashMap

/**
 * Created by Jonah on 9/23/2017.
 */
object Vehicles {

    val VEHICLES: MutableList<Vehicle> = ArrayList()
    val VEHICLES_MAP: MutableMap<String, Vehicle> = HashMap()

    fun addVehicle(item: Vehicle) {
        Vehicles.VEHICLES.add(item)
        VEHICLES_MAP.put(item.id, item)
    }

    data class Vehicle(val vehicleDetails: VehicleDetails) {
        val id = vehicleDetails.id()
        val name = vehicleDetails.name() as String
        val model = vehicleDetails.model() as String
        val vehicleClass = vehicleDetails.vehicleClass() as String
        val manufacturers = vehicleDetails.manufacturers() as List<String>
        val costInCredits = vehicleDetails.costInCredits().toString()
        val length = vehicleDetails.length().toString()
        val crew = vehicleDetails.crew() as String
        val passengers = vehicleDetails.passengers() as String
        val maxAtmospheringSpeed = vehicleDetails.maxAtmospheringSpeed().toString()
        val cargoCapacity = vehicleDetails.cargoCapacity().toString()
        val consumables = vehicleDetails.consumables() as String

        override fun toString(): String = name
    }
}