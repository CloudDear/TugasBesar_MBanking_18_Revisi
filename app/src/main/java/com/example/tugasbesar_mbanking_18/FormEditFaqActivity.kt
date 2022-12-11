package com.example.tugasbesar_mbanking_18

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tugasbesar_mbanking_18.databinding.ActivityFormEditFaqBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormEditFaqActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFormEditFaqBinding
    private var b:Bundle? = null
    private val listFaq = ArrayList<FaqData>()
    @SuppressLint("RestrictedApi")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormEditFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Form Edit Faq"
        b = intent.extras
        val id = b?.getString("id")

        id?.let { getDetailData(it) }
        binding.btnUpdate.setOnClickListener {
            with(binding) {
                val title = txtEditTitle.text.toString()
                val body = txtEditBody.text.toString()

                RClient.instances.updateDataFaq(id,title,body).enqueue(object :
                    Callback<ResponseCreate> {
                    override fun onResponse(
                        call: Call<ResponseCreate>,
                        response: Response<ResponseCreate>
                    ) {
                        if(response.isSuccessful) {

                            Toast.makeText(applicationContext,"${response.body()?.pesan}",
                                Toast.LENGTH_LONG).show()
                            finish()
                        }
                    }
                    override fun onFailure(call: Call<ResponseCreate>, t: Throwable) {
                    }
                })
            }
        }
    }

    fun getDetailData(id:String) {
        RClient.instances.getDataFaq(id).enqueue(object :
            Callback<ResponseDataFaq> {
            override fun onResponse(
                call: Call<ResponseDataFaq>,
                response: Response<ResponseDataFaq>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {
                        listFaq.addAll(it.data) }
                    with(binding) {
                        txtId.setText(listFaq[0].id)
                        txtEditTitle.setText(listFaq[0].title)
                        txtEditBody.setText(listFaq[0].body)
                    }
                }
            }
            override fun onFailure(call: Call<ResponseDataFaq>, t: Throwable) {
            }
        })
    }
}