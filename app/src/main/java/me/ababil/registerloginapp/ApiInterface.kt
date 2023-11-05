package me.ababil.registerloginapp

import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @POST("register")
    fun registerUser(@Body info: UserRegister): Call<User>

    @POST("login")
    fun loginUser(@Body userData: User): Call<LoginResponse>
}