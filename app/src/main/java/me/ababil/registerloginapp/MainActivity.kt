package me.ababil.registerloginapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var apiInterface: ApiInterface
    private lateinit var etUsername: EditText
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var tvNewActivity: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)

        etName = findViewById(R.id.et_name)
        etUsername = findViewById(R.id.et_username)
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        etConfirmPassword = findViewById(R.id.et_confirm_password)
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
    }
    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordValid(password: String): Boolean {
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"
        val passwordMatcher = Regex(passwordPattern)
        return passwordMatcher.matches(password)
    }
    private fun registerUser() {
        val fullName = etName.text.toString()
        val username = etUsername.text.toString()
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()

        if (!isEmailValid(email)) {
            Toast.makeText(this@MainActivity, "Email not valid", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isPasswordValid(password)) {
            Toast.makeText(this@MainActivity, "Password not valid. It must contain at least 8 characters, including 1 number, 1 lower case letter and 1 upper case letter", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this@MainActivity, "Password and Confirm Password do not match", Toast.LENGTH_SHORT).show()
            return
        }

        val call = apiInterface.registerUser(UserRegister(0,username, fullName, email, password))
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "Register success!", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@MainActivity, "Register failed!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Network error!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}