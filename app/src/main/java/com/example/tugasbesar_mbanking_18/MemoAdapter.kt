package com.example.tugasbesar_mbanking_18

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasbesar_mbanking_18.memo_room.Memo
import kotlinx.android.synthetic.main.adapter_memo.view.*

class MemoAdapter (private val memos: ArrayList<Memo>, private val
listener: OnAdapterListener) :
    RecyclerView.Adapter<MemoAdapter.MemoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            MemoViewHolder {
        return MemoViewHolder(

            LayoutInflater.from(parent.context).inflate(R.layout.adapter_memo,parent, false)
        )
    }
    override fun onBindViewHolder(holder: MemoViewHolder, position:
    Int) {
        val memo = memos[position]
        holder.view.text_title.text = memo.title
        holder.view.text_title.setOnClickListener{
            listener.onClick(memo)
        }
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate(memo)
        }
        holder.view.icon_delete.setOnClickListener {
            listener.onDelete(memo)
        }
    }
    override fun getItemCount() = memos.size
    inner class MemoViewHolder( val view: View) :
        RecyclerView.ViewHolder(view)
    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<Memo>){
        memos.clear()
        memos.addAll(list)
        notifyDataSetChanged()
    }
    interface OnAdapterListener {
        fun onClick(memo: Memo)
        fun onUpdate(memo: Memo)
        fun onDelete(memo: Memo)
    }
}