package edu.ucundinamarca.workshop.features.ai_chat.domain.repository

interface AppContextProvider {
    suspend fun getCurrentContext(): String
}
