package com.jonahstarling.swapi.swobjects

import fragment.PersonDetails
import java.util.*

/**
 * Created by Jonah on 9/23/2017.
 */
object People {

    val PEOPLE: MutableList<Person> = ArrayList()
    val PEOPLE_MAP: MutableMap<String, Person> = HashMap()

    fun addPerson(item: Person) {
        People.PEOPLE.add(item)
        PEOPLE_MAP.put(item.id, item)
    }

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