package com.mtc.rickandmorty.api

import com.mtc.rickandmorty.model.character.CharacterResponse
import com.mtc.rickandmorty.model.location.LocationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyAPI {

    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): Response<CharacterResponse>
    @GET("location")
    suspend fun getLocations(@Query("page") page:Int): Response<LocationResponse>

}