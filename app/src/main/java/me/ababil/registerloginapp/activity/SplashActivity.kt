package me.ababil.registerloginapp.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import me.ababil.registerloginapp.MainActivity
import me.ababil.registerloginapp.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        Handler().postDelayed({
            // Start the (next) MainActivity
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, SPLASH_TIME_OUT)
    }

    companion object {
        const val SPLASH_TIME_OUT: Long = 1000 // 3 sec
    }
}