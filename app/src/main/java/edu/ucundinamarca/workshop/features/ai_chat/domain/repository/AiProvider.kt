package edu.ucundinamarca.workshop.features.ai_chat.domain.repository

import edu.ucundinamarca.workshop.features.ai_chat.domain.model.AiMessage
import kotlinx.coroutines.flow.Flow

interface AiProvider {
    fun chat(
        messages: List<AiMessage>,
        systemContext: String? = null
    ): Flow<String>
}
