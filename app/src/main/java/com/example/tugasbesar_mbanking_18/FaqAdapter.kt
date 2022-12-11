package com.example.tugasbesar_mbanking_18

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasbesar_mbanking_18.databinding.ListDataFaqBinding

class FaqAdapter (
    private val listFaq:ArrayList<FaqData>,
    private val context: Context
): RecyclerView.Adapter<FaqAdapter.FaqViewHolder>() {

    inner class FaqViewHolder(item: ListDataFaqBinding)
        : RecyclerView.ViewHolder(item.root){
        private val binding = item
        fun bind(faqData: FaqData){
            with(binding) {
                txtId.text = faqData.id
                txtTitle.text = faqData.title

                cvData.setOnClickListener {
                    var i = Intent(context,
                        DetailFaqActivity::class.java).apply {
                        putExtra("id",faqData.id)
                    }
                    context.startActivity(i)
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolder {
        return FaqViewHolder(
            ListDataFaqBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,false
            ))
    }
    override fun onBindViewHolder(holder: FaqViewHolder,position: Int) {
        holder.bind(listFaq[position])
    }
    override fun getItemCount(): Int = listFaq.size
}