package com.jonahstarling.swapi.swobjects

import fragment.VehicleDetails
import java.util.*

/**
 * Created by Jonah on 9/23/2017.
 * Vehicles is a basic object in the Star Wars API
 * It represents one of the vehicles in the Star Wars Movies
 * It is different from Starships
 * Documentation for the Star Wars API Vehicles object can be found at:
 *  https://swapi.co/documentation#vehicles
 */

// Vehicles is an Object Declaration
// Object Declaration follows the Singleton Pattern
object Vehicles {

    val VEHICLES: MutableList<Vehicle> = ArrayList()
    val VEHICLES_MAP: MutableMap<String, Vehicle> = HashMap()

    fun addVehicle(item: Vehicle) {
        Vehicles.VEHICLES.add(item)
        VEHICLES_MAP.put(item.id, item)
    }

    // Vehicle is a Data Class
    // A Data Class is designed to only hold data
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