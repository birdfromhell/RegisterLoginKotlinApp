package me.ababil.registerloginapp.data

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("fullname") val fullname: String,
    @SerializedName("password") val password: String
)
