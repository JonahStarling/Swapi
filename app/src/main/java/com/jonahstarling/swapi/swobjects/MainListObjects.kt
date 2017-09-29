package com.jonahstarling.swapi.swobjects

import java.util.*

/**
 * Created by Jonah on 9/25/2017.
 * Main List Objects is an object class to help with management on the home line up
 * This allows us to sort and search through the objects of various object types
 */

// Main List Objects is an Object Declaration
// Object Declaration follows the Singleton Pattern
object MainListObjects {

    val MLOBJECTS: MutableList<MLObject> = ArrayList()
    val MLOBJECTS_MAP: MutableMap<String, MLObject> = HashMap()

    fun addMLObject(item: MLObject) {
        MainListObjects.MLOBJECTS.add(item)
        MLOBJECTS_MAP.put(item.id, item)
    }

    // MLObject is a Data Class
    // A Data Class is designed to only hold data
    data class MLObject(val id: String,
                        val name: String,
                        val objectType: String) {
        override fun toString(): String = name
    }
}