package com.example.myshoppinglistapp


import android.content.res.Resources
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshoppinglistapp.R.*
import kotlinx.coroutines.launch

class LocationViewModel:ViewModel() {
    private val _location= mutableStateOf<LocationData?>(null)
    val location:State<LocationData?> = _location
    private val _address= mutableStateOf(listOf <GeocodingResult>())
    var address:State<List<GeocodingResult>> = _address
    fun updateLocation(newLocation:LocationData){
        _location.value=newLocation
    }
    fun fetchaddress(latlng:String){
        Log.d("res1","fetchaddress(latlng:String):${latlng}" )
        try {
            viewModelScope.launch {
               val result=RetrofitClient.create()
                           .getAddressFromCoordinates(latlng,"AIzaSyCU3d21SqYzk1fzUK9S35Cn60avoR23j66")
             _address.value=result.results
              Log.d("res1","Address:${ _address.value.toString()}" )

            }
        }catch (e:Exception){
            Log.d("res1" ,"${e.cause} ${e.message}")
        }
    }
}