package com.example.tugasbesar_mbanking_18


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfile : AppCompatActivity() {

    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val name = preferences.getString("NAME", "")
        textViewName.text = name
        val email = preferences.getString("EMAIL", "")
        textViewEmail.text = email
        val phoneNumber = preferences.getString("PHONENUMBER", "")
        textViewPhoneNumber.text = phoneNumber
        val birthDate = preferences.getString("BIRTHDATE", "")
        textViewBirthDate.text = birthDate

        btnEditProfile.setOnClickListener{
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        btnBack.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }



    }
}