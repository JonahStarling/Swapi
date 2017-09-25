package com.jonahstarling.swapi

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.beust.klaxon.Parser

import com.jonahstarling.swapi.dummy.DummyContent
import kotlinx.android.synthetic.main.activity_film_list.*
import kotlinx.android.synthetic.main.film_list_content.view.*

import kotlinx.android.synthetic.main.film_list.*
import okhttp3.OkHttpClient
import org.json.JSONObject

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [FilmDetailActivity] representing
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

        val BASE_URL = "http://10.0.2.2:8040/"
        val okHttpClient = OkHttpClient.Builder().build()
        val apolloClient = ApolloClient.builder().serverUrl(BASE_URL).okHttpClient(okHttpClient).build();
        val filmQuery = SWFilmsQuery.builder().build()
        val filmCall = apolloClient.query(filmQuery)
        filmCall.enqueue(object : ApolloCall.Callback<SWFilmsQuery.Data>() {
            override fun onResponse(response: Response<SWFilmsQuery.Data>) {
                val data = response.data()
                Log.d("SWAPI-DATA", data.toString())
                val dataObject = parse(data.toString()) as JSONObject
                val allFilms = dataObject.get("allFilms") as JSONObject
                val films = allFilms.getJSONArray("films")
                val film = films.get(1) as JSONObject
                val filmTitle = film.getString("title")
                Log.d("SWAPI-FILM_DATA", filmTitle)
            }

            override fun onFailure(e: ApolloException) {
                Log.w("SWAPI-DATA", e.localizedMessage + ": " + e.cause.toString())
            }
        })

        setupRecyclerView(film_list)
    }

    fun parse(name: String) : Any? {
        val cls = Parser::class.java
        return cls.getResourceAsStream(name)?.let { inputStream ->
            return Parser().parse(inputStream)
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, mTwoPane)
    }

    class SimpleItemRecyclerViewAdapter(private val mParentActivity: FilmListActivity,
                                        private val mValues: List<DummyContent.DummyItem>,
                                        private val mTwoPane: Boolean) :
            RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val mOnClickListener: View.OnClickListener

        init {
            mOnClickListener = View.OnClickListener { v ->
                val item = v.tag as DummyContent.DummyItem
                if (mTwoPane) {
                    val fragment = FilmDetailFragment().apply {
                        arguments = Bundle()
                        arguments.putString(FilmDetailFragment.ARG_ITEM_ID, item.id)
                    }
                    mParentActivity.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.film_detail_container, fragment)
                            .commit()
                } else {
                    val intent = Intent(v.context, FilmDetailActivity::class.java).apply {
                        putExtra(FilmDetailFragment.ARG_ITEM_ID, item.id)
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
            val item = mValues[position]
            holder.mIdView.text = item.id
            holder.mContentView.text = item.content

            with(holder.itemView) {
                tag = item
                setOnClickListener(mOnClickListener)
            }
        }

        override fun getItemCount(): Int {
            return mValues.size
        }

        inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
            val mIdView: TextView = mView.id_text
            val mContentView: TextView = mView.content
        }
    }
}
