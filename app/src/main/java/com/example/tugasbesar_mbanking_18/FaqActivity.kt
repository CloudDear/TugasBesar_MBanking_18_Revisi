package com.example.tugasbesar_mbanking_18

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import com.example.tugasbesar_mbanking_18.databinding.ActivityFaqBinding
import com.example.tugasbesar_mbanking_18.fragment.DataFaqFragment

class FaqActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFaqBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showDataFragment()
        binding.txtCari.setOnKeyListener(View.OnKeyListener{ _, keyCode, event->
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP)
            {
                showDataFragment()
                return@OnKeyListener true
            }
            false
        })
        binding.btnAdd.setOnClickListener{
            startActivity(
                Intent(this,
                    FormAddFaqActivity::class.java)
            )
        }
    }

    fun showDataFragment() {
        val mFragmentManager = supportFragmentManager
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        val mFragment = DataFaqFragment()

        val textCari = binding.txtCari.text
        val mBundle = Bundle()
        mBundle.putString("cari", textCari.toString())
        mFragment.arguments = mBundle
        mFragmentTransaction.replace(R.id.fl_data,mFragment).commit()
    }
}