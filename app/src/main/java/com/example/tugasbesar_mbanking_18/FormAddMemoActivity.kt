package com.example.tugasbesar_mbanking_18

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tugasbesar_mbanking_18.databinding.ActivityFormAddMemoBinding
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class FormAddMemoActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFormAddMemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormAddMemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnAdd.setOnClickListener {
            saveData()
        }
//        binding.tvTgl.setOnClickListener {
//            val datePicker = DatePickerDialog.OnDateSetListener{ view, year, month, dayofMonth ->
//                binding.tglView.text = dateToString(year,month,dayofMonth)
//            }
//            dateDialog(this,datePicker).show()
//        }
    }

    fun saveData(){
        with(binding) {
            val id = txtId.text.toString()
            val title= txtTitle.text.toString()
            val body = txtBody.text.toString()

            RClient.instances.createData(id,title,body).enqueue(object :
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
//    private fun dateToString(year: Int, month: Int, dayofMonth:
//    Int): String {
//        return year.toString()+"-"+(month+1)+"-"+dayofMonth.toString()
//    }
//    private fun dateDialog(context: Context, datePicker:
//    DatePickerDialog.OnDateSetListener): DatePickerDialog {
//        val calender = Calendar.getInstance()
//        return DatePickerDialog(
//            context, datePicker,
//            calender[Calendar.YEAR],
//            calender[Calendar.MONTH],
//            calender[Calendar.DAY_OF_MONTH],
//        )
//    }
}