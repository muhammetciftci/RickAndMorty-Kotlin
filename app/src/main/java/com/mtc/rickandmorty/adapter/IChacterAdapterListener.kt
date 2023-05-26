package com.mtc.rickandmorty.adapter

import com.mtc.rickandmorty.model.character.CharacterModel

interface IChacterAdapterListener {
    fun onClickChar(char: CharacterModel)
}