package com.example.tugasbesar_mbanking_18


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.tugasbesar_mbanking_18.databinding.ActivityRegisterBinding
import com.example.tugasbesar_mbanking_18.room.User
import com.example.tugasbesar_mbanking_18.room.UserDB
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    lateinit var sharedPreferences: SharedPreferences
    private val CHANNEL_ID_1 = "channel_notification_01"

    private val notificationId1 = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        createNotificationChannel()


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

                sendNotification1()

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

    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Title"
            val descriptionText = "Notification Description"

            val channel1 = NotificationChannel(CHANNEL_ID_1, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel1)

        }
    }

    private fun sendNotification1(){

        val intent: Intent = Intent(this, RegisterActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val broadcastIntent: Intent = Intent(this, NotificationReceiver::class.java)
        broadcastIntent.putExtra("toastMessage", binding.inputUsername.text.toString() +" sudah registrasi")
        val actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val bigPictureBitmap = ContextCompat.getDrawable(this, R.drawable.logo)?.toBitmap()
        val bigPictureStyle = NotificationCompat.BigPictureStyle()
            .bigPicture(bigPictureBitmap)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID_1)
            .setSmallIcon(R.drawable.ic_baseline_looks_one_24)
            .setStyle(bigPictureStyle)
            .setContentTitle("BERHASIL REGISTRASI")
            .setContentText("Selamat " +binding.inputUsername.text.toString() +" sudah berhasil registrasi")
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setColor(Color.GREEN)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .addAction(R.mipmap.ic_launcher, "OK", actionIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)){
            notify(notificationId1, builder.build())
        }
    }


}