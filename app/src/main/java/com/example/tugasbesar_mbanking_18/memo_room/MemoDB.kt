package com.example.tugasbesar_mbanking_18.memo_room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Memo::class],
    version = 1
)
abstract class MemoDB: RoomDatabase() {
    abstract fun memoDao() : MemoDao
    companion object {
        @Volatile private var instance : MemoDB? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?:
        synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MemoDB::class.java,
                "user12345.db"
            ).build()
    }
}