package com.mtc.rickandmorty.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtc.rickandmorty.api.RickAndMortyAPIService
import com.mtc.rickandmorty.model.character.CharacterModel
import com.mtc.rickandmorty.model.location.LocationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val rickAndMortyAPIService: RickAndMortyAPIService
) : ViewModel() {

    private var currentLocationPage = 1


    private val _locations = MutableLiveData<List<LocationModel>>()
    val locations: LiveData<List<LocationModel>> = _locations

    private val _character = MutableLiveData<List<CharacterModel>>()
    val character: LiveData<List<CharacterModel>> = _character

    private val _locationIsLoading = MutableLiveData<Boolean>()
    val locationIsLoading: LiveData<Boolean> = _locationIsLoading

    private val _characterIsLoading = MutableLiveData<Boolean>()
    val characterIsLoading: LiveData<Boolean> = _characterIsLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    init {
        getLocations()
        getCharacters()
    }

    fun tryAgain(){
        currentLocationPage = 1
        _character.value = emptyList()
        _locations.value = emptyList()
        getLocations()
        getCharacters()
    }


    fun getLocations() {
        viewModelScope.launch {
            _locationIsLoading.value = true
            try {
                val response = rickAndMortyAPIService.getLocationData(
                    _locations.value ?: emptyList(), currentLocationPage)
                if (response.isSuccessful && response.body()?.results != null) {
                    _locations.value = response.body()?.results
                    currentLocationPage++
                } else {
                    _locationIsLoading.value = false
                }
            }catch (e:Exception){
                _errorMessage.value = e.message
                _locationIsLoading.value = false
            }

        }
    }

    fun getCharacters() {
        viewModelScope.launch {
            _characterIsLoading.value = true
            try {
                val response = rickAndMortyAPIService.getAllCharacters()
                if (response.isSuccessful && response.body()?.results != null) {
                    _character.value = response.body()?.results
                    _characterIsLoading.value = false
                } else {
                    _characterIsLoading.value = false
                }
            }catch (e:Exception){
                _errorMessage.value = e.message
                _characterIsLoading.value = false

            }


        }
    }


}