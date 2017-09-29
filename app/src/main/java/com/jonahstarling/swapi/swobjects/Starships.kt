package com.jonahstarling.swapi.swobjects

import fragment.StarshipDetails
import java.util.*

/**
 * Created by Jonah on 9/23/2017.
 * Starships is a basic object in the Star Wars API
 * It represents one of the starships in the Star Wars Movies
 * It is different from Vehicles
 * Documentation for the Star Wars API Starships object can be found at:
 *  https://swapi.co/documentation#starships
 */

// Starships is an Object Declaration
// Object Declaration follows the Singleton Pattern
object Starships {

    val STARSHIPS: MutableList<Starship> = ArrayList()
    val STARSHIPS_MAP: MutableMap<String, Starship> = HashMap()

    fun addStarship(item: Starship) {
        Starships.STARSHIPS.add(item)
        STARSHIPS_MAP.put(item.id, item)
    }

    // Starship is a Data Class
    // A Data Class is designed to only hold data
    data class Starship(val starshipDetails: StarshipDetails) {
        val id = starshipDetails.id()
        val name = starshipDetails.name() as String
        val model = starshipDetails.model() as String
        val starshipClass = starshipDetails.starshipClass() as String
        val manufacturers = starshipDetails.manufacturers() as List<String>
        val costInCredits = starshipDetails.costInCredits().toString()
        val length = starshipDetails.length().toString()
        val crew = starshipDetails.crew() as String
        val passengers = starshipDetails.passengers() as String
        val maxAtmospheringSpeed = starshipDetails.maxAtmospheringSpeed().toString()
        val hyperdriveRating = starshipDetails.hyperdriveRating().toString()
        val mglt = starshipDetails.MGLT().toString()
        val cargoCapacity = starshipDetails.cargoCapacity().toString()
        val consumables = starshipDetails.consumables() as String

        override fun toString(): String = name
    }
}