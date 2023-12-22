package com.kinan.ktrain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.ui.setupWithNavController
import com.kinan.ktrain.databinding.ActivityNonAdminBinding


class NonAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNonAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNonAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            val navController = findNavController(R.id.nav_host)
            bottomNavigation.setupWithNavController(navController)



        }


    }
}