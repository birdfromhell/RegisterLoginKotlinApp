package me.ababil.registerloginapp.utils

import me.ababil.registerloginapp.data.LoginResponse
import me.ababil.registerloginapp.data.User
import me.ababil.registerloginapp.data.UserRegister
import me.ababil.registerloginapp.data.UserUpdate
import me.ababil.registerloginapp.data.UpdateUserResponse
import me.ababil.registerloginapp.data.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @POST("register")
    fun registerUser(@Body info: UserRegister): Call<User>

    @POST("login")
    fun loginUser(@Body userData: User): Call<LoginResponse>

    @PUT("user/{user_id}")
    fun updateUser(@Path("user_id") userId: Int, @Body info: UserUpdate): Call<UpdateUserResponse>

    @GET("user/{user_id}")
    fun getUser(@Path("user_id") userId: Int): Call<UserResponse>

}