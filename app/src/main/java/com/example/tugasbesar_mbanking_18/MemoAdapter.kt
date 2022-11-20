package com.example.tugasbesar_mbanking_18

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasbesar_mbanking_18.databinding.ListDataMemoBinding

class MemoAdapter (
    private val listMemo:ArrayList<MemoData>,
    private val context: Context
): RecyclerView.Adapter<MemoAdapter.MemoViewHolder>() {

    inner class MemoViewHolder(item: ListDataMemoBinding)
        : RecyclerView.ViewHolder(item.root){
        private val binding = item
        fun bind(memoData: MemoData){
            with(binding) {
                txtId.text = memoData.id
                txtTitle.text = memoData.title

                cvData.setOnClickListener {
                    var i = Intent(context,
                        DetailMemoActivity::class.java).apply {
                        putExtra("id",memoData.id)
                    }
                    context.startActivity(i)
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        return MemoViewHolder(
            ListDataMemoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,false
            ))
    }
    override fun onBindViewHolder(holder: MemoViewHolder,position: Int) {
        holder.bind(listMemo[position])
    }
    override fun getItemCount(): Int = listMemo.size
}