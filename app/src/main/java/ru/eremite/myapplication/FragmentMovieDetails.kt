package ru.eremite.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class FragmentMovieDetails : Fragment() {
    private var listener: TopMainMenuClickListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view=inflater.inflate(R.layout.fragment_movie_details, container, false)
        val backButton:TextView = view.findViewById<TextView>(R.id.back)
        backButton.setOnClickListener{ listener?.onMoviesListActiv() }
        return view
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

    companion object {
        fun newInstance(): FragmentMovieDetails {
            /*val args = Bundle()
            args.putString("android", academy)*/
            val fragment = FragmentMovieDetails()
            /*fragment.arguments = args*/
            return fragment
        }
    }


}