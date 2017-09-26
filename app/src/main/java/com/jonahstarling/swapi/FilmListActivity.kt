package com.jonahstarling.swapi

import SWFilmsQuery
import SWPeopleQuery
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.jonahstarling.swapi.swobjects.Films
import com.jonahstarling.swapi.swobjects.MainListObjects
import com.jonahstarling.swapi.swobjects.People
import com.jonahstarling.swapi.swobjects.Planets
import fragment.FilmDetails
import fragment.PersonDetails
import fragment.PlanetDetails
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

        val BASE_URL = "http://10.0.2.2:54865/"
        val okHttpClient = OkHttpClient.Builder().build()
        val apolloClient = ApolloClient.builder().serverUrl(BASE_URL).okHttpClient(okHttpClient).build();
        callFilmQuery(apolloClient)
        //callPeopleQuery(apolloClient)
        callPlanetsQuery(apolloClient)

        setupRecyclerView(film_list)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(this, MainListObjects.MLOBJECTS, mTwoPane)
    }

    class SimpleItemRecyclerViewAdapter(private val mParentActivity: FilmListActivity,
                                        private val mMLObjects: List<MainListObjects.MLObject>,
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

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.film_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = mMLObjects[position]
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
        }
    }

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
}
