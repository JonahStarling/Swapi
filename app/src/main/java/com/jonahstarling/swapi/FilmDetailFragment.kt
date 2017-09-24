package com.jonahstarling.swapi

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apollographql.apollo.ApolloClient
import com.jonahstarling.swapi.dummy.DummyContent
import kotlinx.android.synthetic.main.activity_film_detail.*
import kotlinx.android.synthetic.main.film_detail.view.*
import okhttp3.OkHttpClient
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.api.Response


/**
 * A fragment representing a single Film detail screen.
 * This fragment is either contained in a [FilmListActivity]
 * in two-pane mode (on tablets) or a [FilmDetailActivity]
 * on handsets.
 */
class FilmDetailFragment : Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    private var mItem: DummyContent.DummyItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val BASE_URL = "http://localhost:8040/"
        val okHttpClient = OkHttpClient.Builder().build()
        val apolloClient = ApolloClient.builder().serverUrl(BASE_URL).okHttpClient(okHttpClient).build();
        val filmQuery = SWFilmsQuery.builder().build()
        val filmCall = apolloClient.query(filmQuery)
        filmCall.enqueue(object : ApolloCall.Callback<SWFilmsQuery.Data>() {
            override fun onResponse(response: Response<SWFilmsQuery.Data>) {
                val data = response.data()
                Log.d("SWAPI-DATA", data.toString())
            }

            override fun onFailure(e: ApolloException) {
                Log.w("SWAPI-DATA", e.localizedMessage + ": " + e.cause.toString())
            }
        })

        if (arguments.containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP[arguments.getString(ARG_ITEM_ID)]
            mItem?.let {
                activity.toolbar_layout?.title = it.content
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.film_detail, container, false)

        // Show the dummy content as text in a TextView.
        mItem?.let {
            rootView.film_detail.text = it.details
        }

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}
