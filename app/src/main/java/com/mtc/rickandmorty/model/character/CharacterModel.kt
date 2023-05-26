package com.mtc.rickandmorty.model.character

import java.io.Serializable

data class CharacterModel (
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String?,
    val gender: String,
    val origin: CharacterOrigin,
    val location: CharacterLocation,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
): Serializable