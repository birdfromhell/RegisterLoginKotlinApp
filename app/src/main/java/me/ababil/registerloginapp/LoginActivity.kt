package me.ababil.registerloginapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var apiInterface: ApiInterface
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var tvGoToRegister: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        apiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)

        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        tvGoToRegister = findViewById(R.id.tv_go_to_register)


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

        val call = apiInterface.loginUser(User(0, "", email, password))
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                // Handle successful login
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                // Handle error
            }
        })
    }
}