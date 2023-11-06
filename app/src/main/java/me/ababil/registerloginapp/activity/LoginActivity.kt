package me.ababil.registerloginapp.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import me.ababil.registerloginapp.MainActivity
import me.ababil.registerloginapp.R
import me.ababil.registerloginapp.data.LoginResponse
import me.ababil.registerloginapp.data.User
import me.ababil.registerloginapp.utils.ApiClient
import me.ababil.registerloginapp.utils.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var apiInterface: ApiInterface
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var tvGoToRegister: TextView
    private lateinit var tvErrorMessage: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        apiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)

        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        tvGoToRegister = findViewById(R.id.tv_go_to_register)
        tvErrorMessage = findViewById(R.id.tv_error_message)

        val btnLogin: Button = findViewById(R.id.btn_login)
        btnLogin.setOnClickListener {
            loginUser()
        }
        tvGoToRegister.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser() {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        val call = apiInterface.loginUser(User(0, "", email, password, ""))
        call.enqueue(object : Callback<LoginResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val token = response.body()?.token
                    if (!token.isNullOrEmpty()) {
                        val sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                        with (sharedPref.edit()) {
                            putString("token", token)
                            apply()
                        }

                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(intent)
                        tvErrorMessage.visibility = View.GONE  // hide error message in case of successful login
                    } else {
                        Toast.makeText(applicationContext, "Token not found", Toast.LENGTH_LONG).show()
                    }
                } else {
                    if (response.code() == 401) {
                        val errorBody = response.errorBody()?.string()
                        if (errorBody != null && errorBody.contains("Incorrect username or password")) {
                            // Display error message using TextView
                            tvErrorMessage.text = "Incorrect username or password."
                            tvErrorMessage.visibility = View.VISIBLE
                        } else {
                            Toast.makeText(applicationContext, "Login failed.", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Network error occurred", Toast.LENGTH_LONG).show()
            }
        })
    }
}