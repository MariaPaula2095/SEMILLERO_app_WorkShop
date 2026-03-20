package edu.ucundinamarca.workshop.shared.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import edu.ucundinamarca.workshop.shared.presentation.components.WorkshopAppBar

@Composable
fun PrivacyScreen(
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            WorkshopAppBar(onBackClick = onNavigateBack)
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Aviso de Privacidad",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            PrivacySection(
                title = "1. Tratamiento de Datos",
                content = "La aplicación Workshop de la Universidad de Cundinamarca recopila únicamente los datos necesarios para el registro en eventos y la calificación de la aplicación."
            )
            
            PrivacySection(
                title = "2. Finalidad",
                content = "Los datos se utilizan exclusivamente para la gestión académica del Workshop de Ingeniería y la mejora continua de nuestras herramientas digitales."
            )
            
            PrivacySection(
                title = "3. Seguridad",
                content = "Implementamos medidas de seguridad técnicas y administrativas para proteger su información contra acceso no autorizado, pérdida o alteración."
            )
            
            PrivacySection(
                title = "4. Derechos del Usuario",
                content = "Usted tiene derecho a conocer, actualizar y rectificar sus datos personales en cualquier momento a través de los canales oficiales de la Universidad."
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Text(
                text = "Última actualización: Marzo 2026",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun PrivacySection(title: String, content: String) {
    Column(modifier = Modifier.padding(bottom = 20.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
