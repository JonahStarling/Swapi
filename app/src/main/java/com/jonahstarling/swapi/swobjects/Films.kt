package com.jonahstarling.swapi.swobjects

import fragment.FilmDetails
import java.util.*

/**
 * Created by Jonah on 9/23/2017.
 */
object Films {

    val FILMS: MutableList<Film> = ArrayList()
    val FILM_MAP: MutableMap<String, Film> = HashMap()

    fun addFilm(item: Film) {
        Films.FILMS.add(item)
        FILM_MAP.put(item.id, item)
    }

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