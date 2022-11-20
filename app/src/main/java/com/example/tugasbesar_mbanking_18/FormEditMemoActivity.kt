package com.example.tugasbesar_mbanking_18

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tugasbesar_mbanking_18.databinding.ActivityFormEditMemoBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class FormEditMemoActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFormEditMemoBinding
    private var b:Bundle? = null
    private val listMemo = ArrayList<MemoData>()
    @SuppressLint("RestrictedApi")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormEditMemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Form Edit Memo"
        b = intent.extras
        val id = b?.getString("id")
//        binding.tvEditTgl.setOnClickListener {
//            val datePicker = DatePickerDialog.OnDateSetListener{
//                    view, year, month, dayofMonth ->
//                binding.tglEditView.text = dateToString(year,month,dayofMonth)
//            }
//            dateDialog(this,datePicker).show()
//        }
        id?.let { getDetailData(it) }
        binding.btnUpdate.setOnClickListener {
            with(binding) {
                val title = txtEditTitle.text.toString()
                val body = txtEditBody.text.toString()

                RClient.instances.updateData(id,title,body).enqueue(object :
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
        RClient.instances.getData(id).enqueue(object :
            Callback<ResponseDataMemo> {
            override fun onResponse(
                call: Call<ResponseDataMemo>,
                response: Response<ResponseDataMemo>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {
                        listMemo.addAll(it.data) }
                    with(binding) {
                        txtId.setText(listMemo[0].id)
                        txtEditTitle.setText(listMemo[0].title)
                        txtEditBody.setText(listMemo[0].body)
                    }
                }
            }
            override fun onFailure(call: Call<ResponseDataMemo>, t: Throwable) {
            }
        })
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