package com.example.tugasbesar_mbanking_18

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.system.exitProcess

class HomeActivity : AppCompatActivity() {

    lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val fragmentHome = HomeFragment()
        val fragmentTransaksi = FragmentTransaksi()
//        val fragmentMemo = FragmentMemo()

        bottomNav = findViewById(R.id.bottomNavigation)

        loadFragment(fragmentHome)
        bottomNav.setOnNavigationItemReselectedListener{
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(fragmentHome)

                }
                R.id.profile -> {
                    val intent = Intent(this, UserProfile::class.java)
                    startActivity(intent)
                }
                R.id.transaction -> {
                    loadFragment(fragmentTransaksi)

                }
                R.id.memo -> {
                    val intent = Intent(this, MemoActivity::class.java)
                    startActivity(intent)

                }
//                R.id.logout -> {
//                    val intent = Intent(this, MainActivity::class.java)
//                    startActivity(intent)
//                }
            }
            true

        }
    }
    private  fun loadFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container,fragment)
            commit()
        }

    }

}