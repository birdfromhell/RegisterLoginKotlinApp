package me.ababil.registerloginapp

import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @POST("register")
    fun registerUser(@Body info: User): Call<User>

    @POST("login")
    fun loginUser(@Body info: User): Call<User>
}