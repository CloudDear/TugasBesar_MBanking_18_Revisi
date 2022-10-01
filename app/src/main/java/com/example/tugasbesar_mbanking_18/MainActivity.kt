package com.example.tugasbesar_mbanking_18
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.tugasbesar_mbanking_18.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var vUsername: String
    lateinit var vPassword: String
    lateinit var mBundle: Bundle

    private lateinit var binding: ActivityMainBinding

    private val CHANNEL_ID_1 = "channel_notification_01"
    private val notificationId1 = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getBundle()

        createNotificationChannel()

        setTitle("User Login")

        binding.btnClear.setOnClickListener { // Mengosongkan Input
            binding.inputLayoutUsername.getEditText()?.setText("")
            binding.inputLayoutPassword.getEditText()?.setText("")

            Snackbar.make(binding.mainLayout, "Text Cleared Success", Snackbar.LENGTH_LONG).show()
        }

        binding.btnLogin.setOnClickListener(View.OnClickListener {
            var checkLogin = false
            val username: String = binding.inputLayoutUsername.getEditText()?.getText().toString()
            val password: String = binding.inputLayoutPassword.getEditText()?.getText().toString()

            if (username.isEmpty()) {
                binding.inputLayoutUsername.setError("Username must be filled with text")
                checkLogin = false
            }

            if(username != vUsername){
                binding.inputLayoutUsername.setError("Username not valid")
                checkLogin = false
            }

            if (password.isEmpty()) {
                binding.inputLayoutPassword.setError("Password must be filled with text")
                checkLogin = false
            }

            if(password != vPassword){
                binding.inputLayoutPassword.setError("Password not valid")
                checkLogin = false
            }
            sendNotification2()

            if (username == vUsername && password == vPassword) checkLogin = true
            if (!checkLogin) return@OnClickListener
            val moveHome = Intent(this@MainActivity, HomeActivity::class.java)
            sendNotification1()
            startActivity(moveHome)
        })

        binding.textViewRegister.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // for splash screen
        val isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
            .getBoolean("isFirstRun", true)
        if (isFirstRun) {
            //show start activity
            startActivity(Intent(this@MainActivity, SplashScreen::class.java))
            finish()
        }
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
            .putBoolean("isFirstRun", false).commit()

    }

    fun getBundle(){
        val bundle: Bundle? = intent.extras

        val name: String? = bundle?.getString("username")
        val pass: String? = bundle?.getString("password")

        binding.inputLayoutUsername.getEditText()?.setText(name)
        binding.inputLayoutPassword.getEditText()?.setText(pass)
        vUsername = name.toString()
        vPassword = pass.toString()
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Title"
            val descriptionText = "Notification Description"

            val channel1 = NotificationChannel(CHANNEL_ID_1, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = descriptionText
            }

            val notificationManager : NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel1)
        }
    }

    private fun sendNotification1(){

        val intent : Intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent : PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val broadcastIntent : Intent = Intent( this, NotificationReceiver::class.java)
        broadcastIntent.putExtra("toastMessage", binding.inputLayoutUsername.getEditText()?.getText().toString() +" sudah login")
        val actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID_1)
            .setSmallIcon(R.drawable.ic_baseline_check_24)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("Mobile banking is a service provided by a bank or " +
                        "other financial institution that allows its customers " +
                        "to conduct financial transactions remotely using a mobile" +
                        "device such as a smartphone or tablet. Unlike the related " +
                        "internet banking it uses software, usually called an app, " +
                        "provided by the financial institution for the purpose. Mobile " +
                        "banking is usually available on a 24-hour basis. Some financial " +
                        "institutions have restrictions on which accounts may be accessed " +
                        "through mobile banking, as well as a limit on the amount that can be " +
                        "transacted. Mobile banking is dependent on the availability of an internet " +
                        "or data connection to the mobile device."
                )
                .setBigContentTitle("BERHASIL LOGIN")
                .setSummaryText("by SOAR Bank")

            )
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setColor(Color.BLUE)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .addAction(R.mipmap.ic_launcher, "OK", actionIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)){
            notify(notificationId1, builder.build())
        }
    }

    private fun sendNotification2(){
        val intent : Intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent : PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val broadcastIntent : Intent = Intent( this, NotificationReceiver::class.java)
        broadcastIntent.putExtra("toastMessage","Masalah Login Terdeteksi")
        val actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID_1)
            .setSmallIcon(R.drawable.ic_baseline_warning_24)
            .setStyle(NotificationCompat.InboxStyle()
                .addLine("Masalah Login")
                .addLine("Terdeteksi Pada Akun")
                .addLine("Harap Periksa")
                .addLine("Username dan Password Pengguna")
                .addLine("Apakah Sudah Sesuai")
                .addLine("Atau Terdapat Kesalahan")
                .setBigContentTitle("GAGAL LOGIN")
                .setSummaryText("by SOAR Bank"))
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setColor(Color.CYAN)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .addAction(R.mipmap.ic_launcher, "OK", actionIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)){
            notify(notificationId1, builder.build())
        }
    }



}