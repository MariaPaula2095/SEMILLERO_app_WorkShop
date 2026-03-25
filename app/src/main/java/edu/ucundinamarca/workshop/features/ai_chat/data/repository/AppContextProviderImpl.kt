package edu.ucundinamarca.workshop.features.ai_chat.data.repository

import edu.ucundinamarca.workshop.features.ai_chat.domain.repository.AppContextProvider
import edu.ucundinamarca.workshop.features.attendance.domain.repository.IAttendanceRepository
import edu.ucundinamarca.workshop.features.schedule.domain.repository.IScheduleRepository
import edu.ucundinamarca.workshop.features.about.domain.repository.IAboutRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class AppContextProviderImpl @Inject constructor(
    private val scheduleRepo: IScheduleRepository,
    private val attendanceRepo: IAttendanceRepository,
    private val aboutRepo: IAboutRepository
) : AppContextProvider {

    override suspend fun getCurrentContext(): String {
        val scheduleResult = scheduleRepo.getSchedule().firstOrNull()
        val attendanceResult = attendanceRepo.getAttendanceForm().firstOrNull()
        val aboutResult = aboutRepo.getAboutInfo().firstOrNull()

        val scheduleInfo = scheduleResult?.getOrNull()?.joinToString("\n") { 
            "- ${it.title} (${it.startTime} - ${it.endTime})" 
        } ?: "No disponible"

        val attendanceInfo = attendanceResult?.getOrNull()?.let { 
            "Formulario de asistencia disponible: ${it.description}"
        } ?: "No hay registros de asistencia activos"

        val aboutInfo = aboutResult?.getOrNull()?.let { info ->
            val devTeamStr = info.developmentTeam.items.joinToString("\n") { item ->
                "- ${item.title}: ${item.content.joinToString(", ")}"
            }
            val academicStr = info.academicInfo.items.joinToString("\n") { item ->
                "- ${item.title}: ${item.content.joinToString(", ")}"
            }
            
            """
            Información de la App:
            Equipo de Desarrollo:
            $devTeamStr
            
            Información Académica:
            $academicStr
            
            Versión: ${info.appVersion}
            """.trimIndent()
        } ?: "Información de la app no disponible"

        return """
            Estado actual de la Aplicación:
            
            Horario:
            $scheduleInfo
            
            Asistencia:
            ${attendanceInfo}
            
            Sobre la App:
            ${aboutInfo}
            
            Instrucciones para la IA:
            Eres un asistente personal de la App Workshop de la Universidad de Cundinamarca.
            Responde de forma amable y concisa utilizando la información técnica proporcionada arriba si el usuario pregunta sobre el evento.
        """.trimIndent()
    }
}
