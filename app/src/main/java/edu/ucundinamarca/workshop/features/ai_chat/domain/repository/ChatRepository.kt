package edu.ucundinamarca.workshop.features.ai_chat.domain.repository

import edu.ucundinamarca.workshop.features.ai_chat.domain.model.AiChat
import edu.ucundinamarca.workshop.features.ai_chat.domain.model.AiMessage
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getChats(): Flow<List<AiChat>>
    fun getMessages(chatId: String): Flow<List<AiMessage>>
    suspend fun saveMessage(chatId: String, message: AiMessage)
    suspend fun createChat(title: String): String
    suspend fun deleteChat(chatId: String)
}
