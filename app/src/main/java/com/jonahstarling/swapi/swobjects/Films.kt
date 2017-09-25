package com.jonahstarling.swapi.swobjects

import java.util.ArrayList
import java.util.HashMap

/**
 * Created by Jonah on 9/23/2017.
 */
object Films {

    val FILMS: MutableList<Film> = ArrayList()
    val FILM_MAP: MutableMap<String, Film> = HashMap()

    init {
        //init what?
    }

    fun addFilm(item: Film) {
        Films.FILMS.add(item)
        FILM_MAP.put(item.id, item)
    }

    /**
     * A dummy item representing a piece of content.
     */
    data class Film(val id: String,
                    val title: String,
                    val openingCrawl: String,
                    val releaseDate: String,
                    val director: String,
                    val producers: Array<String>) {
        override fun toString(): String = title
    }
}