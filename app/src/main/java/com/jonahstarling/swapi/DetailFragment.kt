package com.jonahstarling.swapi

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jonahstarling.swapi.swobjects.*
import kotlinx.android.synthetic.main.activity_film_detail.*
import kotlinx.android.synthetic.main.film_detail.view.*
import kotlinx.android.synthetic.main.person_detail.view.*
import kotlinx.android.synthetic.main.planet_detail.view.*
import kotlinx.android.synthetic.main.species_detail.view.*
import kotlinx.android.synthetic.main.starship_vehicle_detail.view.*


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
    private var mSpecies: Species.SPObject? = null
    private var mStarship: Starships.Starship? = null
    private var mVehicle: Vehicles.Vehicle? = null

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
                "Species" -> {
                    mSpecies = Species.SPECIES_MAP[arguments.getString(ARG_ITEM_ID)]
                    mSpecies?.let {
                        activity.toolbar_layout?.title = it.name
                    }
                }
                "Starship" -> {
                    mStarship = Starships.STARSHIPS_MAP[arguments.getString(ARG_ITEM_ID)]
                    mStarship?.let {
                        activity.toolbar_layout?.title = it.name
                    }
                }
                "Vehicle" -> {
                    mVehicle = Vehicles.VEHICLES_MAP[arguments.getString(ARG_ITEM_ID)]
                    mVehicle?.let {
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
                    rootView.producers.text = convertStringListToString(it.producers)
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
                    rootView.climates.text = convertStringListToString(it.climates)
                    rootView.terrains.text = convertStringListToString(it.terrains)
                    rootView.surfaceWater.text = it.surfaceWater
                }
            }
            "Species" -> {
                rootView = inflater.inflate(R.layout.species_detail, container, false)
                mSpecies?.let {
                    rootView.classification.text = it.classification
                    rootView.designation.text = it.designation
                    rootView.averageHeight.text = it.averageHeight
                    rootView.averageLifespan.text = it.averageLifespan
                    rootView.eyeColors.text = convertStringListToString(it.eyeColors)
                    rootView.hairColors.text = convertStringListToString(it.hairColors)
                    rootView.skinColors.text = convertStringListToString(it.skinColors)
                    rootView.language.text = it.language
                }
            }
            "Starship" -> {
                rootView = inflater.inflate(R.layout.starship_vehicle_detail, container, false)
                mStarship?.let {
                    rootView.model.text = it.model
                    rootView.starshipClass.text = it.starshipClass
                    rootView.vehicleClassTitle.visibility = View.GONE
                    rootView.vehicleClass.visibility = View.GONE
                    rootView.manufacturers.text = convertStringListToString(it.manufacturers)
                    rootView.costInCredits.text = it.costInCredits
                    rootView.length.text = it.length
                    rootView.crew.text = it.crew
                    rootView.passengers.text = it.passengers
                    rootView.maxAtmospheringSpeed.text = it.maxAtmospheringSpeed
                    rootView.hyperdriveRating.text = it.hyperdriveRating
                    rootView.mglt.text = it.mglt
                    rootView.cargoCapacity.text = it.cargoCapacity
                    rootView.consumables.text = it.consumables
                }
            }
            "Vehicle" -> {
                rootView = inflater.inflate(R.layout.starship_vehicle_detail, container, false)
                mVehicle?.let {
                    rootView.model.text = it.model
                    rootView.starshipClassTitle.visibility = View.GONE
                    rootView.starshipClass.visibility = View.GONE
                    rootView.vehicleClass.text = it.vehicleClass
                    rootView.manufacturers.text = convertStringListToString(it.manufacturers)
                    rootView.costInCredits.text = it.costInCredits
                    rootView.length.text = it.length
                    rootView.crew.text = it.crew
                    rootView.passengers.text = it.passengers
                    rootView.maxAtmospheringSpeed.text = it.maxAtmospheringSpeed
                    rootView.hyperdriveRatingTitle.visibility = View.GONE
                    rootView.hyperdriveRating.visibility = View.GONE
                    rootView.mgltTitle.visibility = View.GONE
                    rootView.mglt.visibility = View.GONE
                    rootView.cargoCapacity.text = it.cargoCapacity
                    rootView.consumables.text = it.consumables
                }
            }
            else -> rootView = inflater.inflate(R.layout.film_detail, container, false)
        }
        return rootView

    }

    fun convertStringListToString(listOfStrings: List<String>): String {
        var finishedText = ""
        for (singleString in listOfStrings) {
            finishedText += singleString + ", "
        }
        finishedText = finishedText.dropLast(2)
        return finishedText
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
