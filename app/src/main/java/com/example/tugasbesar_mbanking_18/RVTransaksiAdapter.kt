package com.example.tugasbesar_mbanking_18


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasbesar_mbanking_18.entity.Transaksi

class RVTransaksiAdapter (private val data: Array<Transaksi>) : RecyclerView.Adapter<RVTransaksiAdapter.viewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder{

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_transaksi, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentItem = data [position]
        holder.tvBalance.text = currentItem.balance
        holder.tvDetails.text = "${currentItem.nama}"
    }

    override fun getItemCount(): Int {

        return data.size
    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvBalance : TextView = itemView.findViewById(R.id.tv_balance)
        val tvDetails : TextView = itemView.findViewById(R.id.tv_details)
    }
}