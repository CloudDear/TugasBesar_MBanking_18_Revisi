package com.example.tugasbesar_mbanking_18

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tugasbesar_mbanking_18.databinding.ActivityEditProfileBinding
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_register.*

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

        binding.buttonSaveProfile.setOnClickListener{

            val intent = Intent(this, UserProfile::class.java)
            val mBundle = Bundle()

            val usernameBaru : String = binding.editUserName.editableText.toString()
            val emailBaru : String = binding.editEmail.editableText.toString()
            val tanggalLahirBaru : String = binding.editBirthDate.editableText.toString()
            val nomorTeleponBaru : String = binding.editPhoneNumber.editableText.toString()

            if(usernameBaru.isEmpty()) {
                binding.editUserName.setError("Username must be filled with text")
            }
            if(emailBaru.isEmpty()){
                binding.editEmail.setError("Email must be filled with text")
            }
            if(tanggalLahirBaru.isEmpty()) {
                binding.editBirthDate.setError("Birth Date must be filled with text")
            }
            if(nomorTeleponBaru.isEmpty()) {
                binding.editPhoneNumber.setError("Phone Number must be filled with text")
            }else{
                mBundle.putString("username", binding.editUserName.editableText.toString())

                mBundle.putString("email", binding.editEmail.editableText.toString())

                mBundle.putString("tanggalLahir", binding.editBirthDate.editableText.toString())

                mBundle.putString("nomorTelpon", binding.editPhoneNumber.editableText.toString())

                val name: String = edit_userName.text.toString()
                val email: String = edit_email.text.toString()
                val phoneNumber: String = edit_phoneNumber.text.toString()
                val birthDate: String = edit_birthDate.text.toString()

                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString("NAME", name)
                editor.putString("EMAIL", email)
                editor.putString("PHONENUMBER", phoneNumber)
                editor.putString("BIRTHDATE", birthDate)
                editor.apply()

                intent.putExtras(mBundle)
                startActivity(intent)
            }
        }

        binding.buttonCancel.setOnClickListener{
            val intent = Intent(this, UserProfile::class.java)
            startActivity(intent)
        }


    }
}