package com.example.tugasbesar_mbanking_18

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tugasbesar_mbanking_18.databinding.ActivityFormAddFaqBinding
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormAddFaqActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFormAddFaqBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormAddFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnAdd.setOnClickListener {
            saveData()
        }
    }

    fun saveData(){
        with(binding) {
            val id = txtId.text.toString()
            val title= txtTitle.text.toString()
            val body = txtBody.text.toString()

            RClient.instances.createDataFaq(id,title,body).enqueue(object :
                Callback<ResponseCreate> {
                override fun onResponse(
                    call: Call<ResponseCreate>,
                    response: Response<ResponseCreate>
                ) {
                    if(response.isSuccessful){
                        Toast.makeText(applicationContext,"${response.body()?.pesan}",
                            Toast.LENGTH_LONG).show()
                        finish()
                    }else {
                        val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())

                        txtId.setError(jsonObj.getString("message"))
                        Toast.makeText(applicationContext,"Maaf sudah ada datanya", Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<ResponseCreate>, t: Throwable) {
                    Toast.makeText(applicationContext, "${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}