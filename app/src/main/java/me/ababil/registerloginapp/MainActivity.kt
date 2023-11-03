package me.ababil.registerloginapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var apiInterface: ApiInterface
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var tvNewActivity: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)

        etName = findViewById(R.id.et_name)
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        tvNewActivity = findViewById(R.id.tv_new_activity)

        tvNewActivity.text = getString(R.string.you_have_an_account_sign_here)
        tvNewActivity.setTextColor(ContextCompat.getColor(this, android.R.color.holo_blue_dark))
        tvNewActivity.isClickable = true
        tvNewActivity.isFocusable = true
        tvNewActivity.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val btnRegister: Button = findViewById(R.id.btn_register)
        btnRegister.setOnClickListener {
            registerUser()
        }
//
//        val btnLogin: Button = findViewById(R.id.btn_login)
//        btnLogin.setOnClickListener {
//            loginUser()
//        }
    }

    private fun registerUser() {
        // get text from input fields
        val name = etName.text.toString()
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        val call = apiInterface.registerUser(User(0, name, email, password))
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                // Handle successful registration
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                // Handle error
            }
        })
    }

    private fun loginUser() {
        // get text from input fields
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