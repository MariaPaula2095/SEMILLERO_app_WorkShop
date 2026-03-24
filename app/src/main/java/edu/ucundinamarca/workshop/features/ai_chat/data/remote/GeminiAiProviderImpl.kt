package edu.ucundinamarca.workshop.features.ai_chat.data.remote

import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import edu.ucundinamarca.workshop.features.ai_chat.domain.model.AiMessage
import edu.ucundinamarca.workshop.features.ai_chat.domain.repository.AiProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class GeminiAiProviderImpl @Inject constructor(
    private val generativeModel: GenerativeModel
) : AiProvider {

    override fun chat(messages: List<AiMessage>, systemContext: String?): Flow<String> = flow {
        if (edu.ucundinamarca.workshop.BuildConfig.DEBUG) {
            Log.d("GeminiAiProvider", "Starting chat flow with ${messages.size} messages; systemContext present=${!systemContext.isNullOrBlank()}")
        }

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

        if (edu.ucundinamarca.workshop.BuildConfig.DEBUG) {
            Log.d("GeminiAiProvider", "Sending prompt to Gemini: ${prompt.take(1000)}")
        }

        val response = try {
            chat.sendMessage(prompt)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            if (edu.ucundinamarca.workshop.BuildConfig.DEBUG) {
                Log.e("GeminiAiProvider", "Error al consumir Gemini: ${e.message}", e)
            }
            null
        }

        if (response != null) {
            if (edu.ucundinamarca.workshop.BuildConfig.DEBUG) {
                Log.d("GeminiAiProvider", "Received response: ${response.text?.take(2000)}")
            }
            emit(response.text ?: "No se pudo generar una respuesta.")
        } else {
            emit("Error al generar respuesta. Por favor verifica tu conexión o API Key.")
        }
    }
}
