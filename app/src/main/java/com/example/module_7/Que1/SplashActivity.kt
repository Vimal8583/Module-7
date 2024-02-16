package com.example.module_7.Que1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.module_7.MainActivity
import com.example.module_7.R
import com.example.module_7.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var logoImage = binding.logoImageView
        var fadeIn = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.logoImageView.startAnimation(fadeIn)
        Thread(
            Runnable {
                Thread.sleep(5000)

                        var intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                        finish()

            }
        ).start()
    }
}