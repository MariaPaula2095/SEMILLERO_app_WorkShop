package edu.ucundinamarca.workshop.features.ai_chat.data.remote

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import edu.ucundinamarca.workshop.features.ai_chat.domain.model.AiMessage
import edu.ucundinamarca.workshop.features.ai_chat.domain.repository.AiProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GeminiAiProviderImpl @Inject constructor(
    private val generativeModel: GenerativeModel
) : AiProvider {

    override fun chat(messages: List<AiMessage>, systemContext: String?): Flow<String> = flow {
        val history = messages.dropLast(1).map { msg ->
            content(role = if (msg.role == AiMessage.Role.USER) "user" else "model") {
                text(msg.content)
            }
        }

        val chat = generativeModel.startChat(history)
        val lastMessage = messages.lastOrNull()?.content ?: ""
        
        val prompt = if (!systemContext.isNullOrBlank()) {
            "Contexto de la App:\n$systemContext\n\nPregunta del usuario: $lastMessage"
        } else {
            lastMessage
        }

        val response = chat.sendMessage(prompt)
        emit(response.text ?: "No se pudo generar una respuesta.")
    }
}
