package com.kinan.ktrain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.kinan.ktrain.databinding.ActivityLogRegScreenBinding

class LogRegScreen : AppCompatActivity() {
    lateinit var viewPager2: ViewPager2
    lateinit var binding: ActivityLogRegScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLogRegScreenBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding){
            Log.d("VP", "ViewPager2Created")
            viewPager2 = viewPager
            viewPager2.adapter = TabAdapter(supportFragmentManager, lifecycle)


            TabLayoutMediator(tabLayout, viewPager){
                    tab, position->
                tab.text = when(position){
                    0 -> "Register"
                    1 -> "Login"
                    else -> ""
                }
            }.attach()
        }
    }

    fun switchFragment (position: Int){
        viewPager2.currentItem = position
    }
}