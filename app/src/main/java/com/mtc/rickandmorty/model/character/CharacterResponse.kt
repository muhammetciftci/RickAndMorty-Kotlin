package com.mtc.rickandmorty.model.character

data class CharacterResponse(
    val info: CharacterInfo?,
    val results: List<CharacterModel>
)