package com.ejemplo.CalculatorApp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val tvAvatar = findViewById<TextView>(R.id.tvAvatar)
        val tvUserName = findViewById<TextView>(R.id.tvUserName)
        val btnLogout = findViewById<ImageButton>(R.id.btnLogout)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)

        val name = intent.getStringExtra("USER_NAME")
        val displayName = if (!name.isNullOrBlank()) name else getString(R.string.default_user_name)

        tvUserName.text = displayName
        tvAvatar.text = displayName.first().uppercase()

        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        val tabTitles = arrayOf(
            getString(R.string.tab_discount),
            getString(R.string.tab_split),
            getString(R.string.tab_installments)
        )

        val tabIcons = arrayOf(
            android.R.drawable.ic_menu_agenda,
            android.R.drawable.ic_menu_myplaces,
            android.R.drawable.ic_menu_edit
        )

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
            tab.setIcon(tabIcons[position])
        }.attach()

        btnLogout.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}