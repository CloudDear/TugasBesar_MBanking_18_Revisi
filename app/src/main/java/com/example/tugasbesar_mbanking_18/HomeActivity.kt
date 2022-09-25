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
        val fragmentProfile = ProfileFragment()
        val fragmentMemo = FragmentMemo()

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
                    loadFragment(fragmentMemo)

                }
                R.id.logout -> {
                    val mBuilder = AlertDialog.Builder(this@HomeActivity)
                        .setTitle("Confirm")
                        .setMessage("Are you sure you want to exit?")
                        .setPositiveButton("Yes", object : DialogInterface.OnClickListener{
                            override fun onClick(dialogInterface: DialogInterface, i: Int){
                                exitProcess(0)
                            }
                        })
                        .setNegativeButton("No", null)
                        .show()
                }
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