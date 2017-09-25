package com.jonahstarling.swapi

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jonahstarling.swapi.swobjects.Films
import kotlinx.android.synthetic.main.activity_film_detail.*
import kotlinx.android.synthetic.main.film_detail.view.*


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
    private var mItem: Films.Film? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments.containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = Films.FILM_MAP[arguments.getString(ARG_ITEM_ID)]
            mItem?.let {
                activity.toolbar_layout?.title = it.title
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.film_detail, container, false)

        // Show the dummy content as text in a TextView.
        mItem?.let {
            rootView.releaseDate.text = it.releaseDate
            rootView.director.text = it.director
            rootView.producers.text = "producers temp val"
            rootView.openingCrawl.text = it.openingCrawl
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
