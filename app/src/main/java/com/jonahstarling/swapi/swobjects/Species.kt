package com.jonahstarling.swapi.swobjects

import fragment.SpeciesDetails
import java.util.ArrayList
import java.util.HashMap

/**
 * Created by Jonah on 9/23/2017.
 */
object Species {

    val SPECIES: MutableList<SPObject> = ArrayList()
    val SPECIES_MAP: MutableMap<String, SPObject> = HashMap()

    fun addSpecies(item: SPObject) {
        Species.SPECIES.add(item)
        SPECIES_MAP.put(item.id, item)
    }

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