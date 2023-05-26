package com.mtc.rickandmorty.adapter

import com.mtc.rickandmorty.model.location.LocationModel

interface ILocationAdapterListener {
    fun onClickLocation(location:LocationModel)
    fun sendLocationRequest()
    fun loadingListener():Boolean
}