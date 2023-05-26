package com.mtc.rickandmorty.model.character

data class CharacterInfo(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)
