package edu.ucundinamarca.workshop.core.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {

    @Serializable
    data object Welcome : Route
    @Serializable
    data object Home : Route

    @Serializable
    data object Attendance : Route

    @Serializable
    data object Schedule : Route

    @Serializable
    data object About : Route

    @Serializable
    data object Privacy : Route

    @Serializable
    data object AiChat : Route
    //serializamos la ruta de la evaluacion
    @Serializable
    data object EvaluationWorkshop : Route

    /**
     * Ruta para visualizar contenido externo.
     * @param url La dirección web a cargar.
     */
    @Serializable
    data class WebView(val url: String) : Route


}