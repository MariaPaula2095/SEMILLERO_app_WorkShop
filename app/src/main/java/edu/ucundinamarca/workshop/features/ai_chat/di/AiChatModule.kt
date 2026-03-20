package edu.ucundinamarca.workshop.features.ai_chat.di

import android.content.Context
import androidx.room.*
import com.google.ai.client.generativeai.GenerativeModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucundinamarca.workshop.features.ai_chat.data.local.ChatDao
import edu.ucundinamarca.workshop.features.ai_chat.data.local.ChatDatabase
import edu.ucundinamarca.workshop.features.ai_chat.data.remote.GeminiAiProviderImpl
import edu.ucundinamarca.workshop.features.ai_chat.data.repository.ChatRepositoryImpl
import edu.ucundinamarca.workshop.features.ai_chat.data.repository.AppContextProviderImpl
import edu.ucundinamarca.workshop.features.ai_chat.domain.repository.AppContextProvider
import edu.ucundinamarca.workshop.features.schedule.domain.repository.IScheduleRepository
import edu.ucundinamarca.workshop.features.attendance.domain.repository.IAttendanceRepository
import edu.ucundinamarca.workshop.features.ai_chat.domain.repository.AiProvider
import edu.ucundinamarca.workshop.features.ai_chat.domain.repository.ChatRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AiChatModule {

    @Provides
    @Singleton
    fun provideChatDatabase(@ApplicationContext context: Context): ChatDatabase {
        return Room.databaseBuilder(
            context,
            ChatDatabase::class.java,
            "ai_chat_db"
        ).build()
    }

    @Provides
    fun provideChatDao(db: ChatDatabase): ChatDao = db.chatDao()

    @Provides
    @Singleton
    fun provideChatRepository(chatDao: ChatDao): ChatRepository = ChatRepositoryImpl(chatDao)

    @Provides
    @Singleton
    fun provideGenerativeModel(): GenerativeModel {
        return GenerativeModel(
            modelName = "gemini-1.5-flash", 
            apiKey = edu.ucundinamarca.workshop.BuildConfig.GEMINI_API_KEY
        )
    }

    @Provides
    @Singleton
    fun provideAiProvider(generativeModel: GenerativeModel): AiProvider {
        return GeminiAiProviderImpl(generativeModel)
    }

    @Provides
    @Singleton
    fun provideAppContextProvider(
        scheduleRepo: IScheduleRepository,
        attendanceRepo: IAttendanceRepository
    ): AppContextProvider {
        return AppContextProviderImpl(scheduleRepo, attendanceRepo)
    }
}
