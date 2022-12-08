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
import java.util.ArrayList
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.tugasbesar_mbanking_18.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.shashank.sony.fancytoastlib.FancyToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    lateinit var vUsername: String
    lateinit var vPassword: String
    lateinit var mBundle: Bundle
    lateinit var sharedPreferences: SharedPreferences

    private lateinit var binding: ActivityMainBinding

    private val CHANNEL_ID_1 = "channel_notification_01"
    private val notificationId1 = 10
    private val listUser = ArrayList<UserData>()
    private val filterUser = ArrayList<UserData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        getBundle()
        createNotificationChannel()

        setTitle("User Login")

        binding.btnClear.setOnClickListener { // Mengosongkan Input
            binding.inputLayoutUsername.getEditText()?.setText("")
            binding.inputLayoutPassword.getEditText()?.setText("")

            Snackbar.make(binding.mainLayout, "Text Cleared Success", Snackbar.LENGTH_LONG).show()
        }

        binding.btnLogin.setOnClickListener(View.OnClickListener {
            val username: String = binding.inputLayoutUsername.getEditText()?.getText().toString()
            val password: String = binding.inputLayoutPassword.getEditText()?.getText().toString()

            if (username.isEmpty()) {
                binding.inputLayoutUsername.setError("Username must be filled with text")
            }else{
                binding.inputLayoutUsername.setError(null)
            }

            if (password.isEmpty()) {
                binding.inputLayoutPassword.setError("Password must be filled with text")
            }else{
                binding.inputLayoutPassword.setError(null)
            }
            getAllUser()
//            if (username == vUsername && password == vPassword) checkLogin = true
//            if (!checkLogin) return@OnClickListener
//            val moveHome = Intent(this@MainActivity, HomeActivity::class.java)
//            startActivity(moveHome)
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

    fun getAllUser() {
        val username: String = binding.inputLayoutUsername.getEditText()?.getText().toString()
        val password: String = binding.inputLayoutPassword.getEditText()?.getText().toString()
        RClient.instances.getAllUser(username).enqueue(object :Callback<ResponseDataUser>{
            override fun onResponse(
                call: Call<ResponseDataUser>,
                response: Response<ResponseDataUser>
            ) {
                if(response.isSuccessful){
                    listUser.clear()
                    filterUser.clear()
                    response.body()?.let {
                        listUser.addAll(it.data)
                        listUser.find { it.nameUser == username && it.passwordUser == password }
                            ?.let { it1 -> filterUser.add(it1) }

                        if (filterUser.isEmpty()){
                            sendNotification2()
                        }else{
                            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                            val editor: SharedPreferences.Editor = sharedPreferences.edit()
                            editor.putInt("USERID", filterUser.get(0).idUser)
                            editor.putString("NAME", filterUser.get(0).nameUser)
                            editor.putString("EMAIL", filterUser.get(0).emailUser)
                            editor.putString("PHONENUMBER", filterUser.get(0).noTelp)
                            editor.putString("BIRTHDATE", filterUser.get(0).tglLahir)
                            editor.apply()
                            sendNotification1()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResponseDataUser>, t: Throwable) {

            }
        })
    }

    /** fun getAllUser() : ArrayList<UserData> {
        var checkLogin = false
        RClient.instances.getAllUser().enqueue(object : Callback<ResponseDataUser>{
            override fun onResponse(
                call: Call<ResponseDataUser>,
                response: Response<ResponseDataUser>
            ) {
                if(response.isSuccessful){
                    listUser.clear()
                    filterUser.clear()
                    response.body()?.let {
                        listUser.addAll(it.data)
                        listUser.find { it.nameUser ==  binding.inputLayoutUsername.getEditText()?.toString() && it.passwordUser == binding.inputLayoutPassword.getEditText()?.toString()} ?.let {
                                it1 -> filterUser.add(it1) }
                        if(filterUser.isEmpty()){
                            checkLogin = false
                        }else{
                            checkLogin = true
                            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                        }
                    }
                }
            }
            override fun onFailure(call: Call<ResponseDataUser>, t: Throwable) {
            }
        })
    }
    **/

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
        FancyToast.makeText(this,"Berhasil Login !",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show()

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
        FancyToast.makeText(this,"Username dan Password Salah !",FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show()
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