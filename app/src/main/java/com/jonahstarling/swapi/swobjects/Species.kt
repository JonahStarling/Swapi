package com.jonahstarling.swapi.swobjects

import fragment.SpeciesDetails
import java.util.*

/**
 * Created by Jonah on 9/23/2017.
 * Species is a basic object in the Star Wars API
 * It represents one of the species in the Star Wars Movies
 * Documentation for the Star Wars API Species object can be found at:
 *  https://swapi.co/documentation#species
 */

// Species is an Object Declaration
// Object Declaration follows the Singleton Pattern
object Species {

    val SPECIES: MutableList<SPObject> = ArrayList()
    val SPECIES_MAP: MutableMap<String, SPObject> = HashMap()

    fun addSpecies(item: SPObject) {
        Species.SPECIES.add(item)
        SPECIES_MAP.put(item.id, item)
    }

    // SPObject is a Data Class
    // A Data Class is designed to only hold data
    data class SPObject(val speciesDetails: SpeciesDetails) {
        val id = speciesDetails.id()
        val name = speciesDetails.name() as String
        val classification = speciesDetails.classification() as String
        val designation = speciesDetails.designation() as String
        val averageHeight = speciesDetails.averageHeight().toString()
        val averageLifespan = speciesDetails.averageLifespan().toString()
        val eyeColors = speciesDetails.eyeColors() as List<String>
        val hairColors = speciesDetails.hairColors() as List<String>
        val skinColors = speciesDetails.skinColors() as List<String>
        val language = speciesDetails.language() as String
        override fun toString(): String = name
    }
}