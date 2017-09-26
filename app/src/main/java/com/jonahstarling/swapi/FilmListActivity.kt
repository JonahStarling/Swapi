package com.jonahstarling.swapi

import SWFilmsQuery
import SWPeopleQuery
import SWPlanetsQuery
import SWSpeciesQuery
import SWStarshipsQuery
import SWVehiclesQuery
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.jonahstarling.swapi.swobjects.*
import fragment.*
import kotlinx.android.synthetic.main.activity_film_list.*
import kotlinx.android.synthetic.main.film_list.*
import kotlinx.android.synthetic.main.film_list_content.view.*
import okhttp3.OkHttpClient




/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [DetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class FilmListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var mTwoPane: Boolean = false
    var mainListItems: MutableList<MainListObjects.MLObject> = ArrayList()
    var mSelectedItems: MutableList<String> = ArrayList()
    lateinit var mAdapter: SimpleItemRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        if (film_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true
        }

        val BASE_URL = "http://10.0.2.2:50187/"
        val okHttpClient = OkHttpClient.Builder().build()
        val apolloClient = ApolloClient.builder().serverUrl(BASE_URL).okHttpClient(okHttpClient).build();
        callFilmQuery(apolloClient)
        callPeopleQuery(apolloClient)
        callPlanetsQuery(apolloClient)
        callSpeciesQuery(apolloClient)
        callStarshipsQuery(apolloClient)
        callVehiclesQuery(apolloClient)

        if (mainListItems.size < 1) {
            mainListItems.addAll(MainListObjects.MLOBJECTS)
        }

        setupRecyclerView(film_list)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.film_list_menu, menu)
        val searchItem = menu?.findItem(R.id.app_bar_search)
        val searchView = MenuItemCompat.getActionView(searchItem) as android.widget.SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                callSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                callSearch(newText)
                return true
            }

            fun callSearch(query: String) {
                val tempList: MutableList<MainListObjects.MLObject> = ArrayList()
                for (swobject in mainListItems) {
                    val name = swobject.name as CharSequence
                    val objectType = swobject.objectType as CharSequence
                    if (name.contains(query, true) || objectType.contains(query, true)) {
                        tempList.add(swobject)
                    }
                }
                mainListItems.clear()
                mainListItems.addAll(tempList)
                mAdapter.updateList(tempList)
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_filter -> {
                showFilterDialog()
                return true
            }
            R.id.app_bar_search -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun showFilterDialog() {
        val alertDialog = AlertDialog.Builder(this)
        val selections = arrayOf("Film", "Person", "Planet", "Species", "Starship", "Vehicle")
        val prevSelections = booleanArrayOf(false,false,false,false,false,false)
        for (selection in mSelectedItems) {
            when (selection) {
                "Film" -> prevSelections[0] = true
                "Person" -> prevSelections[1] = true
                "Planet" -> prevSelections[2] = true
                "Species" -> prevSelections[3] = true
                "Starship" -> prevSelections[4] = true
                "Vehicle" -> prevSelections[5] = true
            }
        }
        alertDialog.setMultiChoiceItems(selections, prevSelections) { _, which, isChecked ->
            var selectionValue = selections[which]
            if (isChecked) {
                mSelectedItems.add(selectionValue)
            } else if (mSelectedItems.contains(selectionValue)) {
                mSelectedItems.remove(selectionValue)
            }
        }
        alertDialog.setPositiveButton("Filter") { _, _ ->
            val tempList: MutableList<MainListObjects.MLObject> = ArrayList()
            for (swobject in MainListObjects.MLOBJECTS) {
                val objectType = swobject.objectType
                if (mSelectedItems.contains(objectType)) {
                    tempList.add(swobject)
                }
            }
            mainListItems.clear()
            mainListItems.addAll(tempList)
            mAdapter.updateList(tempList)
        }
        alertDialog.setNegativeButton("Reset") { _, _ ->
            val tempList: MutableList<MainListObjects.MLObject> = ArrayList()
            for (swobject in MainListObjects.MLOBJECTS) {
                val objectType = swobject.objectType
                if (objectType == "Film") {
                    tempList.add(swobject)
                }
            }
            mainListItems.clear()
            mainListItems.addAll(tempList)
            mAdapter.updateList(mainListItems)
        }
        alertDialog.setTitle("Filter")
        alertDialog.show()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        mAdapter = SimpleItemRecyclerViewAdapter(this, mainListItems, mTwoPane)
        recyclerView.adapter = mAdapter
    }

    class SimpleItemRecyclerViewAdapter(private val mParentActivity: FilmListActivity,
                                        private val mMLObjects: MutableList<MainListObjects.MLObject>,
                                        private val mTwoPane: Boolean) :
            RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val mOnClickListener: View.OnClickListener

        init {
            mOnClickListener = View.OnClickListener { v ->
                val item = v.tag as MainListObjects.MLObject
                if (mTwoPane) {
                    val fragment = DetailFragment().apply {
                        arguments = Bundle()
                        arguments.putString(DetailFragment.ARG_ITEM_ID, item.id)
                        arguments.putString(DetailFragment.OBJ_TYPE, item.objectType)
                    }
                    mParentActivity.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.film_detail_container, fragment)
                            .commit()
                } else {
                    val intent = Intent(v.context, DetailActivity::class.java).apply {
                        val extrasBundle = Bundle()
                        extrasBundle.putString(DetailFragment.ARG_ITEM_ID, item.id)
                        extrasBundle.putString(DetailFragment.OBJ_TYPE, item.objectType)
                        putExtra("extrasBundle", extrasBundle)
                    }
                    v.context.startActivity(intent)
                }
            }
        }

        fun updateList(newItems: List<MainListObjects.MLObject>) {
            mMLObjects.clear()
            mMLObjects.addAll(newItems)
            notifyDataSetChanged()
            if (mMLObjects.isEmpty()) {
                val emptySnackbar = Snackbar.make(mParentActivity.findViewById<RecyclerView>(R.id.film_list), "No Results! Try changing your filter/search settings.", Snackbar.LENGTH_LONG)
                emptySnackbar.show();
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.film_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = mMLObjects[position]
            var imgId = 0
            when (item.objectType) {
                "Film" -> imgId = R.drawable.ic_local_movies_black_48dp
                "Person" -> imgId = R.drawable.ic_person_black_48dp
                "Planet" -> imgId = R.drawable.ic_public_black_48dp
                "Species" -> imgId = R.drawable.ic_people_black_48dp
                "Starship" -> imgId = R.drawable.ic_hot_tub_black_48dp
                "Vehicle" -> imgId =R.drawable.ic_airport_shuttle_black_48dp
            }
            holder.mImageView.setImageResource(imgId)
            holder.mTitleView.text = item.name
            with (holder.itemView) {
                tag = item
                setOnClickListener(mOnClickListener)
            }
        }

        override fun getItemCount(): Int {
            return mMLObjects.size
        }

        inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
            val mTitleView: TextView = mView.title
            val mImageView: ImageView = mView.image
        }
    }


// --------------------------- Query Logic and Calls ---------------------------------------------
    // Should be moved to separate file/class

    fun callFilmQuery(apolloClient: ApolloClient) {
        val filmQuery = SWFilmsQuery.builder().build()
        if (Films.FILMS.size < 1) {
            val filmCall = apolloClient.query(filmQuery)
            filmCall.enqueue(object : ApolloCall.Callback<SWFilmsQuery.Data>() {
                override fun onResponse(response: Response<SWFilmsQuery.Data>) {
                    val data = response.data()
                    val allFilms = data?.allFilms()
                    val films = allFilms?.films()
                    var i = 0
                    if (films != null) {
                        while (i < films.size) {
                            val filmDetails = films[i]?.fragments()?.filmDetails() as FilmDetails
                            val newFilm = Films.Film(filmDetails)
                            Films.addFilm(newFilm)
                            MainListObjects.addMLObject(MainListObjects.MLObject(newFilm.id, newFilm.title, "Film"))
                            mainListItems.add(MainListObjects.MLObject(newFilm.id, newFilm.title, "Film"))
                            i += 1
                        }
                    }
                }

                override fun onFailure(e: ApolloException) {
                    Log.w("SWAPI-FILM_DATA", e.localizedMessage + ": " + e.cause.toString())
                }
            })
        }
    }

    fun callPeopleQuery(apolloClient: ApolloClient) {
        val peopleQuery = SWPeopleQuery.builder().build()
        if (People.PEOPLE.size < 1) {
            val peopleCall = apolloClient.query(peopleQuery)
            peopleCall.enqueue(object : ApolloCall.Callback<SWPeopleQuery.Data>() {
                override fun onResponse(response: Response<SWPeopleQuery.Data>) {
                    val data = response.data()
                    val allPeople = data?.allPeople()
                    val people = allPeople?.people()
                    var i = 0
                    if (people != null) {
                        while (i < people.size) {
                            val personDetails = people[i]?.fragments()?.personDetails() as PersonDetails
                            val newPerson = People.Person(personDetails)
                            People.addPerson(newPerson)
                            MainListObjects.addMLObject(MainListObjects.MLObject(newPerson.id, newPerson.name, "Person"))
                            i += 1
                        }
                    }
                }

                override fun onFailure(e: ApolloException) {
                    Log.w("SWAPI-PEOPLE_DATA", e.localizedMessage + ": " + e.cause.toString())
                }
            })
        }
    }

    fun callPlanetsQuery(apolloClient: ApolloClient) {
        val planetsQuery = SWPlanetsQuery.builder().build()
        if (Planets.PLANETS.size < 1) {
            val planetsCall = apolloClient.query(planetsQuery)
            planetsCall.enqueue(object : ApolloCall.Callback<SWPlanetsQuery.Data>() {
                override fun onResponse(response: Response<SWPlanetsQuery.Data>) {
                    val data = response.data()
                    val allPlanets = data?.allPlanets()
                    val planets = allPlanets?.planets()
                    var i = 0
                    if (planets != null) {
                        while (i < planets.size) {
                            val planetDetails = planets[i]?.fragments()?.planetDetails() as PlanetDetails
                            val newPlanet = Planets.Planet(planetDetails)
                            Planets.addPlanet(newPlanet)
                            MainListObjects.addMLObject(MainListObjects.MLObject(newPlanet.id, newPlanet.name, "Planet"))
                            i += 1
                        }
                    }
                }

                override fun onFailure(e: ApolloException) {
                    Log.w("SWAPI-PLANETS_DATA", e.localizedMessage + ": " + e.cause.toString())
                }
            })
        }
    }

    fun callSpeciesQuery(apolloClient: ApolloClient) {
        val speciesQuery = SWSpeciesQuery.builder().build()
        if (Species.SPECIES.size < 1) {
            val speciesCall = apolloClient.query(speciesQuery)
            speciesCall.enqueue(object : ApolloCall.Callback<SWSpeciesQuery.Data>() {
                override fun onResponse(response: Response<SWSpeciesQuery.Data>) {
                    val data = response.data()
                    val allSpecies = data?.allSpecies()
                    val species = allSpecies?.species()
                    var i = 0
                    if (species != null) {
                        while (i < species.size) {
                            val speciesDetails = species[i]?.fragments()?.speciesDetails() as SpeciesDetails
                            val newSpecies = Species.SPObject(speciesDetails)
                            Species.addSpecies(newSpecies)
                            MainListObjects.addMLObject(MainListObjects.MLObject(newSpecies.id, newSpecies.name, "Species"))
                            i += 1
                        }
                    }
                }

                override fun onFailure(e: ApolloException) {
                    Log.w("SWAPI-SPECIES_DATA", e.localizedMessage + ": " + e.cause.toString())
                }
            })
        }
    }

    fun callStarshipsQuery(apolloClient: ApolloClient) {
        val starshipsQuery = SWStarshipsQuery.builder().build()
        if (Starships.STARSHIPS.size < 1) {
            val starshipsCall = apolloClient.query(starshipsQuery)
            starshipsCall.enqueue(object : ApolloCall.Callback<SWStarshipsQuery.Data>() {
                override fun onResponse(response: Response<SWStarshipsQuery.Data>) {
                    val data = response.data()
                    val allStarships = data?.allStarships()
                    val starships = allStarships?.starships()
                    var i = 0
                    if (starships != null) {
                        while (i < starships.size) {
                            val starshipDetails = starships[i]?.fragments()?.starshipDetails() as StarshipDetails
                            val newStarship = Starships.Starship(starshipDetails)
                            Starships.addStarship(newStarship)
                            MainListObjects.addMLObject(MainListObjects.MLObject(newStarship.id, newStarship.name, "Starship"))
                            i += 1
                        }
                    }
                }

                override fun onFailure(e: ApolloException) {
                    Log.w("SWAPI-STARSHIPS_DATA", e.localizedMessage + ": " + e.cause.toString())
                }
            })
        }
    }

    fun callVehiclesQuery(apolloClient: ApolloClient) {
        val vehiclesQuery = SWVehiclesQuery.builder().build()
        if (Vehicles.VEHICLES.size < 1) {
            val vehiclesCall = apolloClient.query(vehiclesQuery)
            vehiclesCall.enqueue(object : ApolloCall.Callback<SWVehiclesQuery.Data>() {
                override fun onResponse(response: Response<SWVehiclesQuery.Data>) {
                    val data = response.data()
                    val allVehicles = data?.allVehicles()
                    val vehicles = allVehicles?.vehicles()
                    var i = 0
                    if (vehicles != null) {
                        while (i < vehicles.size) {
                            val vehicleDetails = vehicles[i]?.fragments()?.vehicleDetails() as VehicleDetails
                            val newVehicle = Vehicles.Vehicle(vehicleDetails)
                            Vehicles.addVehicle(newVehicle)
                            MainListObjects.addMLObject(MainListObjects.MLObject(newVehicle.id, newVehicle.name, "Vehicle"))
                            i += 1
                        }
                    }
                }

                override fun onFailure(e: ApolloException) {
                    Log.w("SWAPI-VEHICLES_DATA", e.localizedMessage + ": " + e.cause.toString())
                }
            })
        }
    }
}
