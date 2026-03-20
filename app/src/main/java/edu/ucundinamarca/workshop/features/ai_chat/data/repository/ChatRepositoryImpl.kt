package edu.ucundinamarca.workshop.features.ai_chat.data.repository

import edu.ucundinamarca.workshop.features.ai_chat.data.local.ChatDao
import edu.ucundinamarca.workshop.features.ai_chat.data.local.ChatEntity
import edu.ucundinamarca.workshop.features.ai_chat.data.local.MessageEntity
import edu.ucundinamarca.workshop.features.ai_chat.domain.model.AiChat
import edu.ucundinamarca.workshop.features.ai_chat.domain.model.AiMessage
import edu.ucundinamarca.workshop.features.ai_chat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatDao: ChatDao
) : ChatRepository {

    override fun getChats(): Flow<List<AiChat>> = chatDao.getAllChats().map { entities ->
        entities.map { it.toDomain() }
    }

    override fun getMessages(chatId: String): Flow<List<AiMessage>> = chatDao.getMessagesForChat(chatId).map { entities ->
        entities.map { it.toDomain() }
    }

    override suspend fun saveMessage(chatId: String, message: AiMessage) {
        chatDao.insertMessage(message.toEntity(chatId))
    }

    override suspend fun createChat(title: String): String {
        val chatId = java.util.UUID.randomUUID().toString()
        chatDao.insertChat(ChatEntity(chatId, title, System.currentTimeMillis()))
        return chatId
    }

    override suspend fun deleteChat(chatId: String) {
        chatDao.deleteChat(chatId)
    }

    // Mappers
    private fun ChatEntity.toDomain() = AiChat(id, title, emptyList(), createdAt)
    private fun MessageEntity.toDomain() = AiMessage(id.toString(), content, AiMessage.Role.valueOf(role), timestamp)
    private fun AiMessage.toEntity(chatId: String) = MessageEntity(
        chatId = chatId,
        content = content,
        role = role.name,
        timestamp = timestamp
    )
}
