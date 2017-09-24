package com.jonahstarling.swapi.swobjects

import java.util.ArrayList
import java.util.HashMap

/**
 * Created by Jonah on 9/23/2017.
 */
object Films {

    val FILMS: MutableList<Film> = ArrayList()
    val FILM_MAP: MutableMap<String, Film> = HashMap()

    private val COUNT = 7

    init {
        // Add some sample items.
    //Fuck me
        for (i in 1..COUNT) {
            addFilm(createFilm(i))
        }
    }

    private fun addFilm(item: Film) {
        Films.FILMS.add(item)
        FILM_MAP.put(item.id, item)
    }

    private fun createFilm(position: Int): Film {
        return Film(position.toString(), "Film " + position)
    }

    /**
     * A dummy item representing a piece of content.
     */
    data class Film(val id: String, val content: String) {
        override fun toString(): String = content
    }
}