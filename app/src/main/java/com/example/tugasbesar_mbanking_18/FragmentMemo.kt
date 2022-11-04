package com.example.tugasbesar_mbanking_18

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugasbesar_mbanking_18.R.layout.fragment_memo
import com.example.tugasbesar_mbanking_18.memo_room.Constant
import com.example.tugasbesar_mbanking_18.memo_room.Memo
import com.example.tugasbesar_mbanking_18.memo_room.MemoDB
import kotlinx.android.synthetic.main.fragment_memo.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentMemo : Fragment() {
    val db by lazy {MemoDB(requireActivity())}
    lateinit var memoAdapter: MemoAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(fragment_memo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        memoAdapter = MemoAdapter(arrayListOf(), object :
            MemoAdapter.OnAdapterListener{
            override fun onClick(memo: Memo) {
                //Toast.makeText(applicationContext, memo.title, Toast.LENGTH_SHORT).show()
                intentEdit(memo.id, Constant.TYPE_READ)
            }
            override fun onUpdate(memo: Memo) {
                intentEdit(memo.id, Constant.TYPE_UPDATE)
            }
            override fun onDelete(memo: Memo) {
                deleteDialog(memo)
            }
        })
        list_memo.apply {
            layoutManager = LinearLayoutManager(requireActivity().applicationContext)
            adapter = memoAdapter
        }
    }
    private fun deleteDialog(memo: Memo){
        val alertDialog = AlertDialog.Builder(requireActivity())
        alertDialog.apply {
            setTitle("Confirmation")
            setMessage("Are You Sure to delete this data From${memo.title}?")
            setNegativeButton("Cancel") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Delete") { dialogInterface, _ ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.memoDao().deleteMemo(memo)
                    loadData()
                }
            }
        }
        alertDialog.show()
    }
    override fun onStart() {
        super.onStart()
        loadData()
    }
    //untuk load data yang tersimpan pada database yang sudah create data
    fun loadData() {
        if (::memoAdapter.isInitialized) {
            CoroutineScope(Dispatchers.Default).launch {
                val memos = db.memoDao().getMemos()
                Log.d("FragmentMemo","dbResponse: $memos")
                withContext(Dispatchers.Main){
                    memoAdapter.setData(memos)
                }
            }
        }

    }

    fun setupListener() {
        button_create.setOnClickListener { intentEdit(0, Constant.TYPE_CREATE) }
    }
    //pick data dari Id yang sebagai primary key
    fun intentEdit(memoId : Int, intentType: Int) = startActivity(
        Intent(requireActivity(), EditActivity::class.java)
            .putExtra("intent_id", memoId)
            .putExtra("intent_type", intentType)
    )

}