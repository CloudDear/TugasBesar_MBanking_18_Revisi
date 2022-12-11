package com.example.tugasbesar_mbanking_18

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tugasbesar_mbanking_18.databinding.ActivityDetailFaqBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailFaqActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailFaqBinding
    private var b:Bundle? = null
    private val listFaq = ArrayList<FaqData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)

        b = intent.extras
        val id = b?.getString("id")
        id?.let { getDataDetail(it) }
        binding.btnHapus.setOnClickListener {
            id?.let { it1 -> deleteData(it1) }
        }

        binding.btnEdit.setOnClickListener {
            startActivity(
                Intent(this,
                    FormEditFaqActivity::class.java).apply {
                    putExtra("id",id)
                })
        }

    }

    fun getDataDetail(id:String){
        RClient.instances.getDataFaq(id).enqueue(object : Callback<ResponseDataFaq> {
            override fun onResponse(
                call: Call<ResponseDataFaq>,
                response: Response<ResponseDataFaq>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {
                        listFaq.addAll(it.data) }
                    with(binding) {
                        tvId.text = listFaq[0].id
                        tvTitle.text = listFaq[0].title
                        tvBody.text = listFaq[0].body
                    }
                }
            }
            override fun onFailure(call: Call<ResponseDataFaq>, t: Throwable) {
            }
        })
    }
    override fun onRestart() {
        super.onRestart()
        this.recreate()
    }
    fun deleteData(id:String){
        val builder = AlertDialog.Builder(this@DetailFaqActivity)
        builder.setMessage("Anda Yakin mau hapus?? Saya nggayakin loh.")
            .setCancelable(false)
            .setPositiveButton("Ya, Hapus Aja!"){ dialog, _ ->
                doDeleteData(id)
            }
            .setNegativeButton("Tidak, Masih sayangdataku"){ dialog, _ ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    private fun doDeleteData(id:String) {
        RClient.instances.deleteDataFaq(id).enqueue(object: Callback<ResponseCreate> {
            override fun onResponse(
                call: Call<ResponseCreate>,
                response: Response<ResponseCreate>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(applicationContext, "Databerhasil dihapus", Toast.LENGTH_LONG).show()
                    finish()
                }
            }
            override fun onFailure(call: Call<ResponseCreate>, t:Throwable) {
            }
        })
    }
}