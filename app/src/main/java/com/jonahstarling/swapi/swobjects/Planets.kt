package com.jonahstarling.swapi.swobjects

import fragment.PlanetDetails
import java.util.ArrayList
import java.util.HashMap

/**
 * Created by Jonah on 9/23/2017.
 */
object Planets {

    val PLANETS: MutableList<Planet> = ArrayList()
    val PLANETS_MAP: MutableMap<String, Planet> = HashMap()

    fun addPlanet(item: Planet) {
        Planets.PLANETS.add(item)
        PLANETS_MAP.put(item.id, item)
    }

    data class Planet(val planetDetails: PlanetDetails) {
        val id = planetDetails.id()
        val name = planetDetails.name() as String
        val diameter = planetDetails.diameter().toString()
        val rotationPeriod = planetDetails.rotationPeriod().toString()
        val orbitalPeriod = planetDetails.orbitalPeriod().toString()
        val gravity = planetDetails.gravity() as String
        val population = planetDetails.population().toString()
        val climates = planetDetails.climates() as List<String>
        val terrains = planetDetails.terrains() as List<String>
        val surfaceWater = planetDetails.surfaceWater().toString()
        override fun toString(): String = name
    }
}