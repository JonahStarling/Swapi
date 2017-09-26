package com.jonahstarling.swapi

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jonahstarling.swapi.swobjects.Films
import com.jonahstarling.swapi.swobjects.People
import com.jonahstarling.swapi.swobjects.Planets
import kotlinx.android.synthetic.main.activity_film_detail.*
import kotlinx.android.synthetic.main.film_detail.view.*
import kotlinx.android.synthetic.main.person_detail.view.*
import kotlinx.android.synthetic.main.planet_detail.view.*


/**
 * A fragment representing a single Film detail screen.
 * This fragment is either contained in a [FilmListActivity]
 * in two-pane mode (on tablets) or a [DetailActivity]
 * on handsets.
 */
class DetailFragment : Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    private var mFilm: Films.Film? = null
    private var mPerson: People.Person? = null
    private var mPlanet: Planets.Planet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments.containsKey(ARG_ITEM_ID) && arguments.containsKey(OBJ_TYPE)) {
            when (arguments.getString(OBJ_TYPE)) {
                "Film" -> {
                    mFilm = Films.FILM_MAP[arguments.getString(ARG_ITEM_ID)]
                    mFilm?.let {
                        activity.toolbar_layout?.title = it.title
                    }
                }
                "Person" -> {
                    mPerson = People.PEOPLE_MAP[arguments.getString(ARG_ITEM_ID)]
                    mPerson?.let {
                        activity.toolbar_layout?.title = it.name
                    }
                }
                "Planet" -> {
                    mPlanet = Planets.PLANETS_MAP[arguments.getString(ARG_ITEM_ID)]
                    mPlanet?.let {
                        activity.toolbar_layout?.title = it.name
                    }
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var rootView: View
        when (arguments.getString(OBJ_TYPE)) {
            "Film" -> {
                rootView = inflater.inflate(R.layout.film_detail, container, false)
                mFilm?.let {
                    rootView.releaseDate.text = it.releaseDate
                    rootView.director.text = it.director
                    var producersText = ""
                    for (name in it.producers) {
                        producersText += name + ", "
                    }
                    producersText = producersText.dropLast(2)
                    rootView.producers.text = producersText
                    rootView.openingCrawl.text = it.openingCrawl
                }
            }
            "Person" -> {
                rootView = inflater.inflate(R.layout.person_detail, container, false)
                mPerson?.let {
                    rootView.birthYear.text = it.birthYear
                    rootView.eyeColor.text = it.eyeColor
                    rootView.gender.text = it.gender
                    rootView.hairColor.text = it.hairColor
                    rootView.heightString.text = it.height
                    rootView.mass.text = it.mass
                    rootView.skinColor.text = it.skinColor
                }
            }
            "Planet" -> {
                rootView = inflater.inflate(R.layout.planet_detail, container, false)
                mPlanet?.let {
                    rootView.diameter.text = it.diameter
                    rootView.rotationPeriod.text = it.rotationPeriod
                    rootView.orbitalPeriod.text = it.orbitalPeriod
                    rootView.gravity.text = it.gravity
                    rootView.population.text = it.population
                    var climatesText = ""
                    for (climate in it.climates) {
                        climatesText += climate + ", "
                    }
                    climatesText = climatesText.dropLast(2)
                    rootView.climates.text = climatesText
                    var terrainsText = ""
                    for (terrain in it.terrains) {
                        terrainsText += terrain + ", "
                    }
                    terrainsText = terrainsText.dropLast(2)
                    rootView.terrains.text = terrainsText
                    rootView.surfaceWater.text = it.surfaceWater
                }
            }
            else -> rootView = inflater.inflate(R.layout.film_detail, container, false)
        }
        return rootView

    }



    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
        const val OBJ_TYPE = "object_type"
    }
}
