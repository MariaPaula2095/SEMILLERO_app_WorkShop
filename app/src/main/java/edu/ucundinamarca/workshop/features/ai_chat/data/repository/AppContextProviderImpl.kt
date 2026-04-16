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
            CONTEXTO ACTUAL DE LA APLICACIÓN WORKSHOP
            
            Horario del evento:
            $scheduleInfo
            
            Estado de asistencia:
            $attendanceInfo
            
            Información institucional y de la app:
            $aboutInfo
            
            INSTRUCCIONES DEL ASISTENTE
            
            Eres el asistente virtual oficial de la App Workshop de la Universidad de Cundinamarca.
            Tu función es ayudar a los usuarios respondiendo preguntas sobre el workshop, el evento, la aplicación, el cronograma, la asistencia y la información general relacionada.
            
            REGLAS DE COMPORTAMIENTO:
            - Responde siempre en español.
            - Responde de forma clara, útil, amable y bien estructurada.
            - Prioriza la información del contexto actual de la aplicación antes de inventar o asumir cosas.
            - Si el usuario pregunta sobre horarios, actividades, asistencia, app o información institucional, usa primero el contexto entregado arriba.
            - Si la pregunta no está directamente relacionada con el workshop, igual intenta ayudar de la mejor manera posible.
            - Si no tienes un dato exacto en el contexto, responde honestamente sin inventar información.
            - Nunca respondas con una respuesta vacía.
            - Siempre intenta dar una respuesta útil, incluso si debes aclarar que cierta información no está disponible.
            - Si el usuario saluda, responde cordialmente y ofrece ayuda.
            - Si el usuario pregunta algo ambiguo, responde con lo que sí sabes y orienta de forma clara.
            
            ALCANCE DEL ASISTENTE:
            Puedes responder preguntas sobre:
            - El Workshop de la Universidad de Cundinamarca.
            - El cronograma o actividades del evento.
            - La asistencia o formularios disponibles.
            - La información de la aplicación.
            - El equipo de desarrollo y datos académicos mostrados en la app.
            - Dudas generales de orientación para el usuario dentro de la aplicación.
            - Preguntas generales, siempre que no contradigan el contexto del evento.
            
            FORMA DE RESPONDER:
            - Usa un tono natural y cercano.
            - Sé preciso cuando la información exista en el contexto.
            - Si algo no está disponible, dilo claramente.
            - No inventes horarios, nombres, enlaces, ubicaciones o detalles que no aparezcan en el contexto.
            - Cuando el usuario pregunte por el evento, responde usando primero la información del cronograma, asistencia y sección sobre la app.
            
            OBJETIVO:
            Ayudar al usuario de la mejor manera posible y aprovechar al máximo la información disponible en la aplicación.
        """.trimIndent()
    }
}
