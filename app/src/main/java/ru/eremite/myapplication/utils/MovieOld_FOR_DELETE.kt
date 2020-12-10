package ru.eremite.myapplication.utils

sealed class ModelDataOld() {
    data class MovieOld(
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
    ) : ModelDataOld(), UniversalTypeHolderView {
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
        val actorsList: List<ActorOld>
            get() {
                val listActors: List<ActorOld> = MoviesDataSource().getActors()
                return listActors.slice(actors)
            }

        override fun getTypeView() = 2
    }

    data class ActorOld(
        val _id: Int,
        val name: String,
        val photoRes: Int?,
        val photoURL: String?
    ) : ModelDataOld(), UniversalTypeHolderView {
        override fun getTypeView() = 3
    }

    data class Genre(
        val _id: Int,
        val name: String
    ) : ModelDataOld()
}

class MoviesDataSource {
    fun getMovies(): List<ModelDataOld.MovieOld> {
        return listOf(
            ModelDataOld.MovieOld(
                4,
                "Dolittle",
                false,
                "PG",
                listOf(0, 6, 7),
                2,
                "87",
                "101",
                "A physician who can talk to animals embarks on an adventure to find a legendary island with a young apprentice and a crew of strange pets.",
                null,
                "pic_movie_image_in_list_dolittle",
                null,
                "pic_movie_image_in_details_dolittle",
                listOf(0, 4, 5, 6, 7, 8)
            ),
            ModelDataOld.MovieOld(
                5,
                "Underwater",
                false,
                "18+",
                listOf(0, 5, 3),
                2,
                "113",
                "95",
                "A crew of oceanic researchers working for a deep sea drilling company try to get to safety after a mysterious earthquake devastates their deepwater research and drilling facility located at the bottom of the Mariana Trench.",
                null,
                "pic_movie_image_in_list_underwater",
                null,
                "pic_movie_image_in_details_underwater",
                listOf(9, 10, 11, 12, 13, 14, 15, 16)
            ),
            ModelDataOld.MovieOld(
                6,
                "The Call Of The Wild",
                false,
                "PG",
                listOf(1, 8, 7),
                3,
                "321",
                "119",
                "A sled dog struggles for survival in the wilds of the Yukon.",
                null,
                "pic_movie_image_in_list_the_call_of_the_wild",
                null,
                "pic_movie_image_in_details_the_call_of_the_wild",
                listOf(18, 19, 20, 21, 22, 23)
            ),
            ModelDataOld.MovieOld(
                7,
                "Last Christmas",
                false,
                "13+",
                listOf(6, 8, 9),
                3,
                "208",
                "121",
                "Kate is a young woman subscribed to bad decisions. Working as an elf in a year round Christmas store is not good for the wannabe singer. However, she meets Tom there. Her life takes a new turn. For Kate, it seems too good to be true.",
                null,
                "pic_movie_image_in_list_last_christmas",
                null,
                "pic_movie_image_in_details_last_christmas",
                listOf(24, 25, 26, 27, 28, 29)
            ),
            ModelDataOld.MovieOld(
                8,
                "The Invisible Guest",
                false,
                "16+",
                listOf(10, 8, 11),
                4,
                "173",
                "106",
                "A successful entrepreneur accused of murder and a witness preparation expert have less than three hours to come up with an impregnable defense.",
                null,
                "pic_movie_image_in_list_the_invisible_guest",
                null,
                "pic_movie_image_in_details_the_invisible_guest",
                listOf(30, 31, 32, 33, 34)
            ),
            ModelDataOld.MovieOld(
                9,
                "Fantasy Island",
                false,
                "13+",
                listOf(0, 1, 2),
                2,
                "181",
                "109",
                "When the owner and operator of a luxurious island invites a collection of guests to live out their most elaborate fantasies in relative seclusion, chaos quickly descends.",
                null,
                "pic_movie_image_in_list_fantasy_island",
                null,
                "pic_movie_image_in_details_fantasy_island",
                listOf(35, 36, 37, 38, 39)
            )
        )
    }

    fun getActors(): List<ModelDataOld.ActorOld> {
        return listOf(
            ModelDataOld.ActorOld(
                0,
                "Robert Downey Jr.",
                null,
                "pic_actor_photo_robert_downey_jr"
            ),//0
            ModelDataOld.ActorOld(1, "Chris Evans", null, "pic_actor_photo_antonio_banderas"),//1
            ModelDataOld.ActorOld(2, "Mark Ruffalo", null, "pic_actor_photo_antonio_banderas"),//2
            ModelDataOld.ActorOld(
                3,
                "Chris Hemsworth",
                null,
                "pic_actor_photo_antonio_banderas"
            ),//3
            ModelDataOld.ActorOld(
                4,
                "Antonio Banderas",
                null,
                "pic_actor_photo_antonio_banderas"
            ),///4
            ModelDataOld.ActorOld(5, "Michael Sheen", null, "pic_actor_photo_michael_sheen"),//5
            ModelDataOld.ActorOld(6, "Jim Broadbent", null, "pic_actor_photo_jim_broadbent"),//6
            ModelDataOld.ActorOld(7, "Jessie Buckley", null, "pic_actor_photo_jessie_buckley"),//7
            ModelDataOld.ActorOld(8, "Harry Colett", null, "pic_actor_photo_harry_colett"),//8
            ModelDataOld.ActorOld(9, "Kristen Stewart", null, "pic_actor_photo_kristen_stewart"),//9
            ModelDataOld.ActorOld(10, "Vincent Cassel", null, "pic_actor_photo_vincent_cassel"),//10
            ModelDataOld.ActorOld(
                11,
                "Mamoudou Athie",
                null,
                " pic_actor_photo_mamoudou_athie"
            ),//11
            ModelDataOld.ActorOld(12, "T. J. Miller", null, "pic_actor_photo_tj_miller"),//12
            ModelDataOld.ActorOld(
                13,
                "John Gallagher Jr.",
                null,
                "pic_actor_photo_john_gallagher_jr"
            ),//13
            ModelDataOld.ActorOld(
                14,
                "Jessica Henwick",
                null,
                "pic_actor_photo_jessica_henwick"
            ),//14
            ModelDataOld.ActorOld(15, "Gunner Wright", null, "pic_actor_photo_gunner_wright"),//15
            ModelDataOld.ActorOld(16, "Fiona Rene", null, "pic_actor_photo_fiona_rene"),//16
            ModelDataOld.ActorOld(17, "Amanda Troop", null, "pic_actor_photo_amanda_troop"),//17
            ModelDataOld.ActorOld(18, "Harrison Ford", null, "pic_actor_photo_harrison_ford"),//18
            ModelDataOld.ActorOld(19, "Omar Sy", null, "pic_actor_photo_omar_sy"),//19
            ModelDataOld.ActorOld(20, "Cara Gee", null, "pic_actor_photo_cara_gee"),//20
            ModelDataOld.ActorOld(21, "Dan Stevens", null, "pic_actor_photo_dan_stevens"),//21
            ModelDataOld.ActorOld(
                22,
                "Bradley Whitford",
                null,
                "pic_actor_photo_bradley_whitford"
            ),//22
            ModelDataOld.ActorOld(
                23,
                "Jean Louisa Kelly",
                null,
                "pic_actor_photo_jean_louisa_kelly"
            ),//23
            ModelDataOld.ActorOld(24, "Emilia Clarke", null, "pic_actor_photo_emilia_clarke"),//24
            ModelDataOld.ActorOld(
                25,
                "Madison Ingoldsby",
                null,
                "pic_actor_photo_madison_ingoldsby"
            ),//25
            ModelDataOld.ActorOld(26, "Emma Thompson", null, "pic_actor_photo_emma_thompson"),//26
            ModelDataOld.ActorOld(
                27,
                "Boris Isakovic",
                null,
                "pic_actor_photo_boris_isakovich"
            ),//27
            ModelDataOld.ActorOld(28, "Maxim Baldry", null, "pic_actor_photo_maxim_baldry"),//28
            ModelDataOld.ActorOld(29, "Mario Casas", null, "pic_actor_photo_mario_casas"),//29
            ModelDataOld.ActorOld(30, "Ana Wagener", null, "pic_actor_photo_ana_wagener"),//30
            ModelDataOld.ActorOld(31, "Barbara Lennie", null, "pic_actor_photo_barbara_lennie"),//31
            ModelDataOld.ActorOld(
                32,
                "Francesc Orella",
                null,
                "pic_actor_photo_francesc_orella"
            ),//32
            ModelDataOld.ActorOld(33, "Paco Tous", null, "pic_actor_photo_paco_tous"),//33
            ModelDataOld.ActorOld(34, "Michael Pena", null, "pic_actor_photo_michael_pena"),//34
            ModelDataOld.ActorOld(35, "Maggie Q", null, "pic_actor_photo_maggie_q"),//35
            ModelDataOld.ActorOld(36, "Lucy Hale", null, "pic_actor_photo_lucy_hale"),//36
            ModelDataOld.ActorOld(37, "Austin Stowell", null, "pic_actor_photo_austin_stowell"),//37
            ModelDataOld.ActorOld(38, "Jimmy O. Yang", null, "pic_actor_photo_jummy_o_yang"),//38
            ModelDataOld.ActorOld(39, "Portia Doubleday", null, "pic_actor_photo_portia_doubleday")
        )//39
    }

    fun getGanre(): List<ModelDataOld.Genre> {
        return listOf(
            ModelDataOld.Genre(0, "Action"),//0
            ModelDataOld.Genre(1, "Adventure"),//1
            ModelDataOld.Genre(2, "Fantasy"),//2
            ModelDataOld.Genre(3, "Sci-Fi"),//3
            ModelDataOld.Genre(4, "Thriller"),//4
            ModelDataOld.Genre(5, "Horror"),//5
            ModelDataOld.Genre(6, "Comedy"),//6
            ModelDataOld.Genre(7, "Family"),//7
            ModelDataOld.Genre(8, "Drama"),//8
            ModelDataOld.Genre(9, "Romance"),//9
            ModelDataOld.Genre(10, "Crime"),//10
            ModelDataOld.Genre(11, "Mystery")
        ) //11
    }
}