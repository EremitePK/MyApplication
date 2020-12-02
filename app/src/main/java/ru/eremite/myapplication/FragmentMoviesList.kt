package ru.eremite.myapplication

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class FragmentMoviesList : Fragment() {

    private var listener: TopMainMenuClickListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_movies_list, container, false)
        val movie_1_click : ImageView = view.findViewById<ImageView>(R.id.poster_movie_1_image_view)
        movie_1_click.setOnClickListener { listener?.onMovieDetailList() };
        return view
    }

    companion object {
        fun newInstance(): FragmentMoviesList {
            /*val args = Bundle()
            args.putString("android", academy)*/
            val fragment = FragmentMoviesList()
            /*fragment.arguments = args*/
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is TopMainMenuClickListener){
            listener = context
        }
    }

    override fun onDetach() {
        listener = null
        super.onDetach()
    }

}
