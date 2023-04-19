package com.example.assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class NavYee : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nav)

        drawerLayout = findViewById(R.id.drawerLayout)
        navView = findViewById(R.id.navView)
        toolbar = findViewById(R.id.barYee)

        setSupportActionBar(toolbar);
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.icon_menu)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.prof -> Toast.makeText(applicationContext,
                    "Clicked Profile",Toast.LENGTH_SHORT).show()
                R.id.fj -> Toast.makeText(applicationContext,
                    "Clicked Find Jobs",Toast.LENGTH_SHORT).show()
                R.id.ja -> Toast.makeText(applicationContext,
                    "Clicked Job Applied",Toast.LENGTH_SHORT).show()
                R.id.inter -> Toast.makeText(applicationContext,
                    "Clicked Interviews",Toast.LENGTH_SHORT).show()
                R.id.cd -> Toast.makeText(applicationContext,
                    "Clicked Career Development",Toast.LENGTH_SHORT).show()
                R.id.so -> Toast.makeText(applicationContext,
                    "Clicked Sign Out",Toast.LENGTH_SHORT).show()
            }
            true
        }

        fun onOptionsItemSelected(item: MenuItem): Boolean {
            val id = item.itemId
            if (id == android.R.id.home) {
                drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
            return super.onOptionsItemSelected(item)
        }


    }
}
