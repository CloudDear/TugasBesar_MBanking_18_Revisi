package com.example.tugasbesar_mbanking_18


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasbesar_mbanking_18.entity.Transaksi
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_transaksi.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FragmentTransaksi : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_transaksi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        val adapter : RVTransaksiAdapter = RVTransaksiAdapter(Transaksi.listofTransaksi)


        val rvTransaksi : RecyclerView = view.findViewById(R.id.rv_transaksi)


        rvTransaksi.layoutManager = layoutManager


        rvTransaksi.setHasFixedSize(true)


        rvTransaksi.adapter = adapter
        btn_pengeluaran.setOnClickListener{
            setupListener()
        }
    }
    fun setupListener() {
        startActivity(
            Intent(requireActivity().applicationContext, ChartActivity::class.java)
        )
    }
}