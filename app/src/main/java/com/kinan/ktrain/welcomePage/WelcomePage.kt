package com.kinan.ktrain.welcomePage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kinan.ktrain.authentication.LogRegScreen
import com.kinan.ktrain.databinding.ActivityWelcomePageBinding

class WelcomePage : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnStart.setOnClickListener {
                val Intent = Intent(this@WelcomePage, LogRegScreen::class.java).apply {
                    putExtra("TAB", 0)
                }
                startActivity(Intent)
            }
        }
    }}