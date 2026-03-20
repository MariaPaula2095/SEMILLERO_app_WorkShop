package edu.ucundinamarca.workshop.features.ai_chat.domain.model

import java.util.UUID

data class AiChat(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "Nueva Conversación",
    val messages: List<AiMessage> = emptyList(),
    val createdAt: Long = System.currentTimeMillis()
)

data class AiMessage(
    val id: String = UUID.randomUUID().toString(),
    val content: String,
    val role: Role,
    val timestamp: Long = System.currentTimeMillis()
) {
    enum class Role {
        USER, ASSISTANT, SYSTEM
    }
}
