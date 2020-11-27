package ru.eremite.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class MovieDetailsActivity : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var vv=inflater.inflate(R.layout.activity_movie_details, container, false)
        var iv = vv.findViewById<TextView>(R.id.back) as TextView
        iv.setOnClickListener(clickListener)

        return vv
    }

    val clickListener = View.OnClickListener {view ->
        getActivity()?.getSupportFragmentManager()?.beginTransaction()
            ?.replace(R.id.fragment_container_view, FragmentMoviesList())
            ?.commit()
    }

}