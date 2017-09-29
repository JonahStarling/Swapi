package com.jonahstarling.swapi.swobjects

import fragment.FilmDetails
import java.util.*

/**
 * Created by Jonah on 9/23/2017.
 * A Film is a basic object in the Star Wars API
 * It represents one of the Star Wars Movies made
 * Currently only the first six movies are supported by the Star Wars GraphQL
 * Documentation for the Star Wars API Film object can be found at:
 *  https://swapi.co/documentation#films
 */

// Films is an Object
// Object Declaration follows the Singleton Pattern
object Films {

    val FILMS: MutableList<Film> = ArrayList()
    val FILM_MAP: MutableMap<String, Film> = HashMap()

    fun addFilm(item: Film) {
        Films.FILMS.add(item)
        FILM_MAP.put(item.id, item)
    }

    // Film is a Data Class
    // A Data Class is designed to only hold data
    data class Film(val filmDetails: FilmDetails) {
        val id = filmDetails.id()
        val title = filmDetails.title() as String
        val openingCrawl = filmDetails.openingCrawl() as String
        val releaseDate = filmDetails.releaseDate() as String
        val director = filmDetails.director() as String
        val producers = filmDetails.producers() as List<String>
        override fun toString(): String = title
    }
}