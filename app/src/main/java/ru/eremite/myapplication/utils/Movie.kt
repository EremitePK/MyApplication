package ru.eremite.myapplication.utils

import ru.eremite.myapplication.R

sealed class ModelData {
    data class Movie(
        val _id: Int,
        val name: String,
        val like: Boolean,
        val age: String,
        val genre: List<Int> = listOf(),
        val rating: Int,
        val reviews: String,
        val duration: String,
        val store: String,
        val posterListRes: Int?,
        val posterListURL: String?,
        val posterDetailsRes: Int?,
        val posterDetailsURL: String?,
        val actors: List<Int> = listOf()
    ):ModelData() {
        val genreString: String
            get() {
                var stringGenre = "";
                val listGenre: List<Genre> = MoviesDataSource().getGanre()
                for (n: Int in genre) {
                    if (stringGenre != "") {
                        stringGenre = "$stringGenre, "
                    }
                    stringGenre += listGenre[n].name
                }
                return stringGenre
            }
        val actorsList: List<Actor>
            get() {
                val listActors: List<Actor> = MoviesDataSource().getActors()
                return listActors.slice(actors)
            }
    }

    data class Actor(
        val _id: Int,
        val name: String,
        val photoRes: Int?,
        val photoURL: String?
    ):ModelData()

    data class Genre(
        val _id: Int,
        val name: String
    ):ModelData()
}

data class Header(
        val name:String="",
        val image:Int=0
)

class MoviesDataSource {
    fun getMovies(): List<ModelData.Movie> {
        return listOf(
            ModelData.Movie(0,"Avengers:  End Game",true,"13+", listOf(0,1,2),2,"125","97",
                        "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos actions and restore balance to the universe.",
                        R.drawable.poster_1,null, R.drawable.poster_details_1,null, listOf(0,1,2,3,4)),
            ModelData.Movie(1,"Tenet",false,"16+",listOf(0,3,4),3,"98","97",
                        "Armed with only one word, Tenet, and fighting for the survival of the entire world, a Protagonist journeys through a twilight world of international espionage on a mission that will unfold in something beyond real time.",
                        R.drawable.poster_2,null, R.drawable.poster_details_1,null, listOf(2,3)),
            ModelData.Movie(2,"Black Widow",false,"13+",listOf(0,1,3),4,"38","102",
                        "A film about Natasha Romanoff in her quests between the films Civil War and Infinity War.",
                        R.drawable.poster_3,null, R.drawable.poster_details_1,null, listOf(3,4)),
            ModelData.Movie(3,"Wonder Woman 1984",false,"13+",listOf(0,1,2),5,"74","120",
                        "Fast forward to the 1980s as Wonder Woman's next big screen adventure finds her facing two all-new foes: Max Lord and The Cheetah.",
                        R.drawable.poster_4,null, R.drawable.poster_details_1,null, listOf(4,2)),
            ModelData.Movie(4,"Dolittle",false,"PG",listOf(0,6,7),2,"87","101",
                        "A physician who can talk to animals embarks on an adventure to find a legendary island with a young apprentice and a crew of strange pets.",
                        null,"pic_movie_image_in_list_dolittle",null,"pic_movie_image_in_details_dolittle", listOf(0,4,5,6,7,8)),
            ModelData.Movie(5,"Underwater",false,"18+", listOf(0,5,3),2,"113","95",
                        "A crew of oceanic researchers working for a deep sea drilling company try to get to safety after a mysterious earthquake devastates their deepwater research and drilling facility located at the bottom of the Mariana Trench.",
                        null,"pic_movie_image_in_list_underwater",null,"pic_movie_image_in_details_underwater", listOf(9,10,11,12,13,14,15,16)),
            ModelData.Movie(6,"The Call Of The Wild",false,"PG",listOf(1,8,7),3,"321","119",
                        "A sled dog struggles for survival in the wilds of the Yukon.",
                        null,"pic_movie_image_in_list_the_call_of_the_wild",null,"pic_movie_image_in_details_the_call_of_the_wild", listOf(18,19,20,21,22,23)),
            ModelData.Movie(7,"Last Christmas",false,"13+",listOf(6,8,9),3,"208","121",
                        "Kate is a young woman subscribed to bad decisions. Working as an elf in a year round Christmas store is not good for the wannabe singer. However, she meets Tom there. Her life takes a new turn. For Kate, it seems too good to be true.",
                        null,"pic_movie_image_in_list_last_christmas",null,"pic_movie_image_in_details_last_christmas", listOf(24,25,26,27,28,29)),
            ModelData.Movie(8,"The Invisible Guest",false,"16+",listOf(10,8,11),4,"173","106",
                        "A successful entrepreneur accused of murder and a witness preparation expert have less than three hours to come up with an impregnable defense.",
                        null,"pic_movie_image_in_list_the_invisible_guest",null,"pic_movie_image_in_details_the_invisible_guest", listOf(30,31,32,33,34)),
            ModelData.Movie(9,"Fantasy Island",false,"13+",listOf(0,1,2),2,"181","109",
                        "When the owner and operator of a luxurious island invites a collection of guests to live out their most elaborate fantasies in relative seclusion, chaos quickly descends.",
                        null,"pic_movie_image_in_list_fantasy_island",null,"pic_movie_image_in_details_fantasy_island", listOf(35,36,37,38,39))
        )
    }
    fun getActors(): List<ModelData.Actor> {
        return listOf(ModelData.Actor(0,"Robert Downey Jr.",null, "pic_actor_photo_robert_downey_jr"),//0
            ModelData.Actor(1,"Chris Evans", R.drawable.movie2,null),//1
            ModelData.Actor(2,"Mark Ruffalo", R.drawable.movie3,null),//2
            ModelData.Actor(3,"Chris Hemsworth", R.drawable.movie4,null),//3
            ModelData.Actor(4,"Antonio Banderas",null, "pic_actor_photo_antonio_banderas"),///4
            ModelData.Actor(5,"Michael Sheen",null, "pic_actor_photo_michael_sheen"),//5
            ModelData.Actor(6,"Jim Broadbent",null, "pic_actor_photo_jim_broadbent"),//6
            ModelData.Actor(7,"Jessie Buckley",null, "pic_actor_photo_jessie_buckley"),//7
            ModelData.Actor(8,"Harry Colett",null, "pic_actor_photo_harry_colett"),//8
            ModelData.Actor(9,"Kristen Stewart",null, "pic_actor_photo_kristen_stewart"),//9
            ModelData.Actor(10,"Vincent Cassel",null, "pic_actor_photo_vincent_cassel"),//10
            ModelData.Actor(11,"Mamoudou Athie",null, " pic_actor_photo_mamoudou_athie"),//11
            ModelData.Actor(12,"T. J. Miller",null, "pic_actor_photo_tj_miller"),//12
            ModelData.Actor(13,"John Gallagher Jr.",null, "pic_actor_photo_john_gallagher_jr"),//13
            ModelData.Actor(14,"Jessica Henwick",null, "pic_actor_photo_jessica_henwick"),//14
            ModelData.Actor(15,"Gunner Wright",null, "pic_actor_photo_gunner_wright"),//15
            ModelData.Actor(16,"Fiona Rene",null, "pic_actor_photo_fiona_rene"),//16
            ModelData.Actor(17,"Amanda Troop",null, "pic_actor_photo_amanda_troop"),//17
            ModelData.Actor(18,"Harrison Ford",null, "pic_actor_photo_harrison_ford"),//18
            ModelData.Actor(19,"Omar Sy",null, "pic_actor_photo_omar_sy"),//19
            ModelData.Actor(20,"Cara Gee",null, "pic_actor_photo_cara_gee"),//20
            ModelData.Actor(21,"Dan Stevens",null, "pic_actor_photo_dan_stevens"),//21
            ModelData.Actor(22,"Bradley Whitford",null, "pic_actor_photo_bradley_whitford"),//22
            ModelData.Actor(23,"Jean Louisa Kelly",null, "pic_actor_photo_jean_louisa_kelly"),//23
            ModelData.Actor(24,"Emilia Clarke",null, "pic_actor_photo_emilia_clarke"),//24
            ModelData.Actor(25,"Madison Ingoldsby",null, "pic_actor_photo_madison_ingoldsby"),//25
            ModelData.Actor(26,"Emma Thompson", null,"pic_actor_photo_emma_thompson"),//26
            ModelData.Actor(27,"Boris Isakovic",null, "pic_actor_photo_boris_isakovich"),//27
            ModelData.Actor(28,"Maxim Baldry",null, "pic_actor_photo_maxim_baldry"),//28
            ModelData.Actor(29,"Mario Casas",null, "pic_actor_photo_mario_casas"),//29
            ModelData.Actor(30,"Ana Wagener",null, "pic_actor_photo_ana_wagener"),//30
            ModelData.Actor(31,"Barbara Lennie",null, "pic_actor_photo_barbara_lennie"),//31
            ModelData.Actor(32,"Francesc Orella",null, "pic_actor_photo_francesc_orella"),//32
            ModelData.Actor(33,"Paco Tous",null, "pic_actor_photo_paco_tous"),//33
            ModelData.Actor(34,"Michael Pena",null, "pic_actor_photo_michael_pena"),//34
            ModelData.Actor(35,"Maggie Q",null, "pic_actor_photo_maggie_q"),//35
            ModelData.Actor(36,"Lucy Hale",null, "pic_actor_photo_lucy_hale"),//36
            ModelData.Actor(37,"Austin Stowell",null, "pic_actor_photo_austin_stowell"),//37
            ModelData.Actor(38,"Jimmy O. Yang",null, "pic_actor_photo_jummy_o_yang"),//38
            ModelData.Actor(39,"Portia Doubleday",null, "pic_actor_photo_portia_doubleday"))//39
    }

    fun getGanre(): List<ModelData.Genre> {
        return listOf(ModelData.Genre(0,"Action"),//0
            ModelData.Genre(1,"Adventure"),//1
            ModelData.Genre(2,"Fantasy"),//2
            ModelData.Genre(3,"Sci-Fi"),//3
            ModelData.Genre(4,"Thriller"),//4
            ModelData.Genre(5,"Horror"),//5
            ModelData.Genre(6,"Comedy"),//6
            ModelData.Genre(7,"Family"),//7
            ModelData.Genre(8,"Drama"),//8
            ModelData.Genre(9,"Romance"),//9
            ModelData.Genre(10,"Crime"),//10
            ModelData.Genre(11,"Mystery")) //11
    }
}