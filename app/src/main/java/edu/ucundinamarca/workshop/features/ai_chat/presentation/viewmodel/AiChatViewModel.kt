package edu.ucundinamarca.workshop.features.ai_chat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucundinamarca.workshop.features.ai_chat.domain.model.AiMessage
import edu.ucundinamarca.workshop.features.ai_chat.domain.repository.AiProvider
import edu.ucundinamarca.workshop.features.ai_chat.domain.repository.AppContextProvider
import edu.ucundinamarca.workshop.features.ai_chat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AiChatState(
    val messages: List<AiMessage> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentChatId: String? = null
)

sealed class AiChatEvent {
    data class SendMessage(val text: String) : AiChatEvent()
    object LoadHistory : AiChatEvent()
    data class SelectChat(val chatId: String) : AiChatEvent()
}

@HiltViewModel
class AiChatViewModel @Inject constructor(
    private val aiProvider: AiProvider,
    private val chatRepo: ChatRepository,
    private val contextProvider: AppContextProvider
) : ViewModel() {

    private val _state = MutableStateFlow(AiChatState())
    val state: StateFlow<AiChatState> = _state.asStateFlow()

    init {
        onEvent(AiChatEvent.LoadHistory)
    }

    fun onEvent(event: AiChatEvent) {
        when (event) {
            is AiChatEvent.SendMessage -> sendMessage(event.text)
            AiChatEvent.LoadHistory -> loadHistory()
            is AiChatEvent.SelectChat -> selectChat(event.chatId)
        }
    }

    private fun loadHistory() {
        viewModelScope.launch {
            chatRepo.getChats().collectLatest { chats ->
                if (chats.isNotEmpty() && _state.value.currentChatId == null) {
                    selectChat(chats.first().id)
                } else if (chats.isEmpty()) {
                    val newId = chatRepo.createChat("Chat ${System.currentTimeMillis()}")
                    selectChat(newId)
                }
            }
        }
    }

    private fun selectChat(chatId: String) {
        _state.update { it.copy(currentChatId = chatId) }
        viewModelScope.launch {
            chatRepo.getMessages(chatId).collectLatest { messages ->
                _state.update { it.copy(messages = messages) }
            }
        }
    }

    private fun sendMessage(text: String) {
        val chatId = _state.value.currentChatId ?: return
        val userMessage = AiMessage(content = text, role = AiMessage.Role.USER)
        
        viewModelScope.launch {
            chatRepo.saveMessage(chatId, userMessage)
            _state.update { it.copy(isLoading = true, error = null) }
            
            try {
                val context = contextProvider.getCurrentContext()
                val history = _state.value.messages
                
                aiProvider.chat(history + userMessage, context).collect { response ->
                    val assistantMessage = AiMessage(content = response, role = AiMessage.Role.ASSISTANT)
                    chatRepo.saveMessage(chatId, assistantMessage)
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = "Error: ${e.message}") }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
}
