package com.xiangze.mobileclassb5_b6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.xiangze.mobileclassb5_b6.adapters.PageAdapter
import com.xiangze.mobileclassb5_b6.databinding.ActivityMainBinding
import com.xiangze.mobileclassb5_b6.fragments.FirstFragment
import com.xiangze.mobileclassb5_b6.fragments.SecondFragment

//Caaron

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PageAdapter(
            listOf(FirstFragment(),SecondFragment()), //ThirdFragment()
            supportFragmentManager,
            this.lifecycle
            )

        binding.vpPages.adapter = adapter

        TabLayoutMediator(binding.tlHome,binding.vpPages){tab,position->
            when(position){
                0 -> tab.text = "First Fragment"
                //1 -> tab.text = "Third Fragment"
                else -> tab.text = "Second Fragment"
            }
        }.attach()
    }
}