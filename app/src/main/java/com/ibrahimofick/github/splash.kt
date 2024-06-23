package com.ibrahimofick.github

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity



@SuppressLint("CustomSplashScreen")
class splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@splash, MainActivity::class.java)
            startActivity(intent)
//            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }, 3000L)
    }
}