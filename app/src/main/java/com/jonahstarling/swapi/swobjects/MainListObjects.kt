package com.jonahstarling.swapi.swobjects

import java.util.ArrayList
import java.util.HashMap

/**
 * Created by Jonah on 9/25/2017.
 */
object MainListObjects {
    val MLOBJECTS: MutableList<MLObject> = ArrayList()
    val MLOBJECTS_MAP: MutableMap<String, MLObject> = HashMap()

    fun addMLObject(item: MLObject) {
        MainListObjects.MLOBJECTS.add(item)
        MLOBJECTS_MAP.put(item.id, item)
    }

    data class MLObject(val id: String,
                        val name: String,
                        val objectType: String) {
        override fun toString(): String = name
    }
}