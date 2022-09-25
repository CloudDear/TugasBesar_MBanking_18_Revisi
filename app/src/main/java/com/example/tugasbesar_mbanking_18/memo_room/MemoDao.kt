package com.example.tugasbesar_mbanking_18.memo_room

import androidx.room.*

@Dao
interface MemoDao {
    @Insert
    suspend fun addMemo(memo: Memo)
    @Update
    suspend fun updateMemo(memo: Memo)
    @Delete
    suspend fun deleteMemo(memo: Memo)
    @Query("SELECT * FROM memo")
    suspend fun getMemos() : List<Memo>
    @Query("SELECT * FROM memo WHERE id =:memo_id")
    suspend fun getMemo(memo_id: Int) : List<Memo>
}