package se.magictechnology.modernaandroidv4

import kotlinx.serialization.Serializable

@Serializable
data class Chuckjoke(val id : String, val value : String)

@Serializable
data class Searchresult(val total : Int, val result : List<Chuckjoke>)
