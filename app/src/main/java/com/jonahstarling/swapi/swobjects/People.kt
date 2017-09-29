package com.jonahstarling.swapi.swobjects

import fragment.PersonDetails
import java.util.*

/**
 * Created by Jonah on 9/23/2017.
 * People is a basic object in the Star Wars API
 * It represents one of the characters in the Star Wars Movies
 * Documentation for the Star Wars API People object can be found at:
 *  https://swapi.co/documentation#people
 */

// People is an Object Declaration
// Object Declaration follows the Singleton Pattern
object People {

    val PEOPLE: MutableList<Person> = ArrayList()
    val PEOPLE_MAP: MutableMap<String, Person> = HashMap()

    fun addPerson(item: Person) {
        People.PEOPLE.add(item)
        PEOPLE_MAP.put(item.id, item)
    }

    // People is a Data Class
    // A Data Class is designed to only hold data
    data class Person(val personDetails: PersonDetails) {
        val id = personDetails.id()
        val name = personDetails.name() as String
        val birthYear = personDetails.birthYear() as String
        val eyeColor = personDetails.eyeColor() as String
        val gender = personDetails.gender() as String
        val hairColor = personDetails.hairColor() as String
        val height = personDetails.height().toString()
        val mass = personDetails.mass().toString()
        val skinColor = personDetails.skinColor() as String
        override fun toString(): String = name
    }
}