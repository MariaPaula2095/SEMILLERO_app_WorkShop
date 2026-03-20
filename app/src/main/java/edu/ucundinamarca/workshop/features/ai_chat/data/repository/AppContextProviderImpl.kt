package edu.ucundinamarca.workshop.features.ai_chat.data.repository

import edu.ucundinamarca.workshop.features.ai_chat.domain.repository.AppContextProvider
import edu.ucundinamarca.workshop.features.attendance.domain.repository.IAttendanceRepository
import edu.ucundinamarca.workshop.features.schedule.domain.repository.IScheduleRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class AppContextProviderImpl @Inject constructor(
    private val scheduleRepo: IScheduleRepository,
    private val attendanceRepo: IAttendanceRepository
) : AppContextProvider {

    override suspend fun getCurrentContext(): String {
        val scheduleResult = scheduleRepo.getSchedule().firstOrNull()
        val attendanceResult = attendanceRepo.getAttendanceForm().firstOrNull()

        val scheduleInfo = scheduleResult?.getOrNull()?.joinToString("\n") { 
            "- ${it.title} (${it.startTime} - ${it.endTime})" 
        } ?: "No disponible"

        val attendanceInfo = attendanceResult?.getOrNull()?.let { 
            "Formulario de asistencia disponible: ${it.description}"
        } ?: "No hay registros de asistencia activos"

        return """
            Estado actual de la Aplicación:
            
            Horario:
            $scheduleInfo
            
            Asistencia:
            $attendanceInfo
            
            Instrucciones para la IA:
            Eres un asistente personal de la App Workshop de la Universidad de Cundinamarca.
            Responde de forma amable y concisa utilizando la información técnica proporcionada arriba si el usuario pregunta sobre sus clases o asistencias.
        """.trimIndent()
    }
}
