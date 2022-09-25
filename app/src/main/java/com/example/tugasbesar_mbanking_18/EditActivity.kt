package com.example.tugasbesar_mbanking_18

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.tugasbesar_mbanking_18.memo_room.Constant
import com.example.tugasbesar_mbanking_18.memo_room.Memo
import com.example.tugasbesar_mbanking_18.memo_room.MemoDB
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {
    val db by lazy { MemoDB(this) }
    private var memoId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setupView()
        setupListener()
//
// Toast.makeText(this, noteId.toString(),Toast.LENGTH_SHORT).show()
    }
    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType){
            Constant.TYPE_CREATE -> {
                button_update.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                button_save.visibility = View.GONE
                button_update.visibility = View.GONE
                getMemo()
            }
            Constant.TYPE_UPDATE -> {
                button_save.visibility = View.GONE
                getMemo()
            }
        }
    }
    private fun setupListener() {
        button_save.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.memoDao().addMemo(
                    Memo(0,edit_title.text.toString(),
                        edit_memo.text.toString())
                )
                finish()
            }
        }
        button_update.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.memoDao().updateMemo(
                    Memo(memoId, edit_title.text.toString(),
                        edit_memo.text.toString())
                )
                finish()
            }
        }
    }
    fun getMemo() {
        memoId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val memos = db.memoDao().getMemo(memoId)[0]
            edit_title.setText(memos.title)
            edit_memo.setText(memos.memo)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}