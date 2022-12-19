package com.xiangze.mobileclassb5_b6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.xiangze.mobileclassb5_b6.adapters.PageAdapter
import com.xiangze.mobileclassb5_b6.databinding.ActivityMainBinding
import com.xiangze.mobileclassb5_b6.fragments.FirstFragment
import com.xiangze.mobileclassb5_b6.fragments.SecondFragment

// Justin

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PageAdapter(
            listOf(FirstFragment(), SecondFragment()),
            supportFragmentManager,
            this.lifecycle
        )

        binding.vpPages.adapter = adapter

        TabLayoutMediator(binding.tlHome, binding.vpPages) { tab, position ->
            when (position) {
                0 -> tab.text = "First Fragment"
                else -> tab.text = "Second Fragment"
            }
        }.attach()
        // #FF0000   1111
//        val color = ContextCompat.getColor(this, R.color.teal_700)
//
//        binding.vpPages.setBackgroundColor(-16676986)
    }
}

// testing git reset