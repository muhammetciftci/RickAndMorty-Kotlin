package com.mtc.rickandmorty.model.location

data class LocationResponse(
    val info: LocationInfo?,
    val results: List<LocationModel>
)