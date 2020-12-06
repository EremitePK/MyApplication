package ru.eremite.myapplication

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FragmentMoviesList : Fragment() {

    private var listener: TopMainMenuClickListener? = null
    private var movieListRecycler: RecyclerView? = null
    private val movies:List<Movie> = MoviesDataSource().getMovies()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_movies_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        movieListRecycler = view.findViewById(R.id.movies_list_recycler_view)
        movieListRecycler?.adapter = MoviesAdapter(clickListener)
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
        movieListRecycler = null
        super.onDetach()
    }

    override fun onStart() {
        super.onStart()

        updateData()
    }

    private fun updateData() {
        (movieListRecycler?.adapter as? MoviesAdapter)?.apply {
            bindMovies(movies)
        }
    }

    private fun doOnClick( movie: Movie):Movie {
        listener?.onMovieDetailList(movies.indexOf(movie))
        return movie;
    }

    private val clickListener = object : OnRecyclerItemClicked {
        override fun onClick(movie: Movie) {
            doOnClick(movie)
        }
    }
}
class MoviesDataSource {
    fun getMovies(): List<Movie> {
        return listOf(
                Movie("Avengers:  End Game",true,"13+", listOf(0,1,2),2,"125","97",
                        "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos actions and restore balance to the universe.",
                        R.drawable.poster_1.toString(),R.drawable.poster_details_1.toString(), listOf(0,1,2,3,4)),
                Movie("Tenet",false,"16+",listOf(0,3,4),3,"98","97",
                        "Armed with only one word, Tenet, and fighting for the survival of the entire world, a Protagonist journeys through a twilight world of international espionage on a mission that will unfold in something beyond real time.",
                        R.drawable.poster_2.toString(),R.drawable.poster_details_1.toString(), listOf(2,3)),
                Movie("Black Widow",false,"13+",listOf(0,1,3),4,"38","102",
                        "A film about Natasha Romanoff in her quests between the films Civil War and Infinity War.",
                        R.drawable.poster_3.toString(),R.drawable.poster_details_1.toString(), listOf(3,4)),
                Movie("Wonder Woman 1984",false,"13+",listOf(0,1,2),5,"74","120",
                        "Fast forward to the 1980s as Wonder Woman's next big screen adventure finds her facing two all-new foes: Max Lord and The Cheetah.",
                        R.drawable.poster_4.toString(),R.drawable.poster_details_1.toString(), listOf(4,2)),
                Movie("Dolittle",false,"PG",listOf(0,6,7),2,"87","101",
                        "A physician who can talk to animals embarks on an adventure to find a legendary island with a young apprentice and a crew of strange pets.",
                        "pic_movie_image_in_list_dolittle","pic_movie_image_in_details_dolittle", listOf(0,4,5,6,7,8)),
                 Movie("Underwater",false,"18+", listOf(0,5,3),2,"113","95",
                        "A crew of oceanic researchers working for a deep sea drilling company try to get to safety after a mysterious earthquake devastates their deepwater research and drilling facility located at the bottom of the Mariana Trench.",
                        "pic_movie_image_in_list_underwater","pic_movie_image_in_details_underwater", listOf(9,10,11,12,13,14,15,16)),
                Movie("The Call Of The Wild",false,"PG",listOf(1,8,7),3,"321","119",
                        "A sled dog struggles for survival in the wilds of the Yukon.",
                        "pic_movie_image_in_list_the_call_of_the_wild","pic_movie_image_in_details_the_call_of_the_wild", listOf(18,19,20,21,22,23)),
                Movie("Last Christmas",false,"13+",listOf(6,8,9),3,"208","121",
                        "Kate is a young woman subscribed to bad decisions. Working as an elf in a year round Christmas store is not good for the wannabe singer. However, she meets Tom there. Her life takes a new turn. For Kate, it seems too good to be true.",
                        "pic_movie_image_in_list_last_christmas","pic_movie_image_in_details_last_christmas", listOf(24,25,26,27,28,29)),
                Movie("The Invisible Guest",false,"16+",listOf(10,8,11),4,"173","106",
                        "A successful entrepreneur accused of murder and a witness preparation expert have less than three hours to come up with an impregnable defense.",
                        "pic_movie_image_in_list_the_invisible_guest","pic_movie_image_in_details_the_invisible_guest", listOf(30,31,32,33,34)),
                Movie("Fantasy Island",false,"13+",listOf(0,1,2),2,"181","109",
                        "When the owner and operator of a luxurious island invites a collection of guests to live out their most elaborate fantasies in relative seclusion, chaos quickly descends.",
                        "pic_movie_image_in_list_fantasy_island","pic_movie_image_in_details_fantasy_island", listOf(35,36,37,38,39))
        )
    }
    fun getActors(): List<Actor> {
        return listOf(Actor("Robert Downey Jr.", "pic_actor_photo_robert_downey_jr"),//0
                Actor("Chris Evans", R.drawable.movie2.toString()),//1
                Actor("Mark Ruffalo", R.drawable.movie3.toString()),//2
                Actor("Chris Hemsworth", R.drawable.movie4.toString()),//3
                Actor("Antonio Banderas", "pic_actor_photo_antonio_banderas"),///4
                Actor("Michael Sheen", "pic_actor_photo_michael_sheen"),//5
                Actor("Jim Broadbent", "pic_actor_photo_jim_broadbent"),//6
                Actor("Jessie Buckley", "pic_actor_photo_jessie_buckley"),//7
                Actor("Harry Colett", "pic_actor_photo_harry_colett"),//8
                Actor("Kristen Stewart", "pic_actor_photo_kristen_stewart"),//9
                Actor("Vincent Cassel", "pic_actor_photo_vincent_cassel"),//10
                Actor("Mamoudou Athie", " pic_actor_photo_mamoudou_athie"),//11
                Actor("T. J. Miller", "pic_actor_photo_tj_miller"),//12
                Actor("John Gallagher Jr.", "pic_actor_photo_john_gallagher_jr"),//13
                Actor("Jessica Henwick", "pic_actor_photo_jessica_henwick"),//14
                Actor("Gunner Wright", "pic_actor_photo_gunner_wright"),//15
                Actor("Fiona Rene", "pic_actor_photo_fiona_rene"),//16
                Actor("Amanda Troop", "pic_actor_photo_amanda_troop"),//17
                Actor("Harrison Ford", "pic_actor_photo_harrison_ford"),//18
                Actor("Omar Sy", "pic_actor_photo_omar_sy"),//19
                Actor("Cara Gee", "pic_actor_photo_cara_gee"),//20
                Actor("Dan Stevens", "pic_actor_photo_dan_stevens"),//21
                Actor("Bradley Whitford", "pic_actor_photo_bradley_whitford"),//22
                Actor("Jean Louisa Kelly", "pic_actor_photo_jean_louisa_kelly"),//23
                Actor("Emilia Clarke", "pic_actor_photo_emilia_clarke"),//24
                Actor("Madison Ingoldsby", "pic_actor_photo_madison_ingoldsby"),//25
                Actor("Emma Thompson", "pic_actor_photo_emma_thompson"),//26
                Actor("Boris Isakovic", "pic_actor_photo_boris_isakovich"),//27
                Actor("Maxim Baldry", "pic_actor_photo_maxim_baldry"),//28
                Actor("Mario Casas", "pic_actor_photo_mario_casas"),//29
                Actor("Ana Wagener", "pic_actor_photo_ana_wagener"),//30
                Actor("Barbara Lennie", "pic_actor_photo_barbara_lennie"),//31
                Actor("Francesc Orella", "pic_actor_photo_francesc_orella"),//32
                Actor("Paco Tous", "pic_actor_photo_paco_tous"),//33
                Actor("Michael Pena", "pic_actor_photo_michael_pena"),//34
                Actor("Maggie Q", "pic_actor_photo_maggie_q"),//35
                Actor("Lucy Hale", "pic_actor_photo_lucy_hale"),//36
                Actor("Austin Stowell", "pic_actor_photo_austin_stowell"),//37
                Actor("Jimmy O. Yang", "pic_actor_photo_jummy_o_yang"),//38
                Actor("Portia Doubleday", "pic_actor_photo_portia_doubleday"))//39
    }

    fun getGanre(): List<Genre> {
        return listOf(Genre("Action"),//0
                Genre("Adventure"),//1
                Genre("Fantasy"),//2
                Genre("Sci-Fi"),//3
                Genre("Thriller"),//4
                Genre("Horror"),//5
                Genre("Comedy"),//6
                Genre("Family"),//7
                Genre("Drama"),//8
                Genre("Romance"),//9
                Genre("Crime"),//10
                Genre("Mystery")) //11
    }
}
