package com.example.tugasbesar_mbanking_18


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tugasbesar_mbanking_18.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)


        binding.btnClear.setOnClickListener(){
            binding.inputUsername.setText("")
            binding.inputPassword.setText("")
            binding.inputEmail.setText("")
            binding.inputTanggalLahir.setText("")
            binding.inputNomorTelepon.setText("")

            Snackbar.make(binding.registerLayout, "Text Cleared Success", Snackbar.LENGTH_LONG).show()
        }

        binding.textViewLogin.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            val mBundle = Bundle()

            val username : String = binding.inputUsername.editableText.toString()
            val password : String = binding.inputPassword.editableText.toString()
            val email : String = binding.inputEmail.editableText.toString()
            val tanggalLahir : String = binding.inputTanggalLahir.editableText.toString()
            val nomorTelepon : String = binding.inputNomorTelepon.editableText.toString()

            if(username.isEmpty()) {
                binding.inputUsername.setError("Username must be filled with text")
            }
            if(password.isEmpty()){
                binding.inputPassword.setError("Password must be filled with text")
            }
            if(email.isEmpty()) {
                binding.inputEmail.setError("Email must be filled with text")
            }
            if(tanggalLahir.isEmpty()) {
                binding.inputTanggalLahir.setError("Tanggal Lahir must be filled with text")
            }
            if(nomorTelepon.isEmpty()) {
                binding.inputNomorTelepon.setError("Nomor Telepon must be filled with text")
            }else{
                mBundle.putString("username", binding.inputUsername.editableText.toString())

                mBundle.putString("password", binding.inputPassword.editableText.toString())

                mBundle.putString("email", binding.inputEmail.editableText.toString())

                mBundle.putString("tanggalLahir", binding.inputTanggalLahir.editableText.toString())

                mBundle.putString("nomorTelpon", binding.inputNomorTelepon.editableText.toString())

                val name: String = inputUsername.text.toString()
                val email: String = inputEmail.text.toString()
                val phoneNumber: String = inputNomorTelepon.text.toString()
                val birthDate: String = inputTanggalLahir.text.toString()

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

//        btnRegister.setOnClickListener{
//
//
//        }

    }

}