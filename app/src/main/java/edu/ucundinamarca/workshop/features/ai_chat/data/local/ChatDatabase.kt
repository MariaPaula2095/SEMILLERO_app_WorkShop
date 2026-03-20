package edu.ucundinamarca.workshop.features.ai_chat.data.local

import androidx.room.*

@Database(entities = [ChatEntity::class, MessageEntity::class], version = 1, exportSchema = false)
abstract class ChatDatabase : RoomDatabase() {
    abstract fun chatDao(): ChatDao
}
