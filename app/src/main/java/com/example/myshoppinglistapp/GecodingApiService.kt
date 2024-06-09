package com.example.myshoppinglistapp

import com.google.android.gms.maps.model.LatLng
import retrofit2.http.GET
import retrofit2.http.Query

interface GecodingApiService {
    // https://maps.googleapis.com/maps/api/geocode/json?latlng=41.234,12.345&key=API_KEY
    @GET("maps/api/geocode/json")
    suspend fun getAddressFromCoordinates(
        @Query("latlng")latLng: String,
        @Query("key") apikey:String
    ):GeocodingResponse
}