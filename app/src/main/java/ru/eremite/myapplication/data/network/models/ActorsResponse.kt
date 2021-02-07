package ru.eremite.myapplication.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.eremite.myapplication.data.network.BaseDataTMDb
import ru.eremite.myapplication.domain.models.Actor

@Serializable
class ActorsResponse {
    @SerialName("id")
    val id: Int = 0

    @SerialName("cast")
    private var actors: List<ActorNetwork> = mutableListOf()

    var actorsList: List<ActorNetwork> = actors

    fun getActorsListModel(): List<Actor> {
        var actorList = mutableListOf<Actor>()
        actors.forEach {
            actorList.add(
                Actor(
                    it.id,
                    it.name,
                    BaseDataTMDb.PATH_POSTER + it.profile_path
                )
            )
        }
        return actorList
    }
}