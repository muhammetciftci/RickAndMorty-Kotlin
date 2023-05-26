package com.mtc.rickandmorty.api

import com.mtc.rickandmorty.model.character.CharacterResponse
import com.mtc.rickandmorty.model.location.LocationModel
import com.mtc.rickandmorty.model.location.LocationResponse
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RickAndMortyAPIService @Inject constructor(
    private val retrofit: Retrofit
) {

    private val api = retrofit.create(RickAndMortyAPI::class.java)

    suspend fun getLocationData(locations: List<LocationModel>, pageNumber: Int): Response<LocationResponse> {
        val response = api.getLocations(pageNumber)
        if (response.isSuccessful) {
            val locationResponse = response.body()
            val locationInfo = response.body()?.info
            val newLocations = locationResponse?.results ?: emptyList()
            val mergedLocations = locations + newLocations
            return Response.success(LocationResponse(locationInfo,mergedLocations))
        } else {
            return response
        }
    }

    suspend fun getAllCharacters(): Response<CharacterResponse> {
        var pageNumber = 1
        var response = api.getCharacters(pageNumber)
        var characterResponse = CharacterResponse(null, emptyList())
        while (response.isSuccessful && response.body() != null) {
            val characterInfo = response.body()?.info
            val characters = response.body()?.results ?: emptyList()
            characterResponse = CharacterResponse(characterInfo, characterResponse.results + characters)
            pageNumber++
            response = api.getCharacters(pageNumber)
        }
        return Response.success(characterResponse)
    }
}




