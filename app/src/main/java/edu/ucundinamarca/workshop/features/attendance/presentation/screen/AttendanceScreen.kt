package edu.ucundinamarca.workshop.features.attendance.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.clickable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import edu.ucundinamarca.workshop.features.attendance.domain.model.QuestionType
import edu.ucundinamarca.workshop.features.attendance.presentation.components.AttendanceDropdown
import edu.ucundinamarca.workshop.features.attendance.presentation.components.AttendanceRadioGroup
import edu.ucundinamarca.workshop.features.attendance.presentation.components.AttendanceTextField
import edu.ucundinamarca.workshop.features.attendance.presentation.viewmodel.AttendanceViewModel
import edu.ucundinamarca.workshop.shared.presentation.components.WorkshopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceScreen(
    onBack: () -> Unit,
    onNavigateToPrivacy: () -> Unit,
    onSuccess: () -> Unit,
    viewModel: AttendanceViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isSuccess) {
        onSuccess()
    }

    Scaffold(
        topBar = {
            WorkshopAppBar(onBackClick = onBack)
        },
        bottomBar = {
            if (uiState.form != null && !uiState.isLoading && uiState.error == null) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding(),
                    tonalElevation = 8.dp,
                    shadowElevation = 8.dp
                ) {
                    Button(
                        onClick = { viewModel.submitForm() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                            .height(56.dp),
                        enabled = !uiState.isSubmitting && uiState.isPrivacyAccepted,
                        shape = MaterialTheme.shapes.large
                    ) {
                        if (uiState.isSubmitting) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                "Enviar Registro",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (uiState.error != null) {
                Text(
                    text = "Error: ${uiState.error}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center).padding(16.dp)
                )
            } else if (uiState.form != null) {
                val form = uiState.form!!
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp)
                ) {
                    item {
                        Column(modifier = Modifier.padding(vertical = 16.dp)) {
                            Text(
                                text = form.title,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = form.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                        }
                    }

                    items(form.questions) { question ->
                        val answer = uiState.answers[question.id] ?: ""
                        
                        when (question.type) {
                            QuestionType.TEXT, QuestionType.EMAIL, QuestionType.NUMBER -> {
                                AttendanceTextField(
                                    label = question.label,
                                    value = answer,
                                    onValueChange = { viewModel.onAnswerChange(question.id, it) },
                                    placeholder = question.placeholder,
                                    keyboardType = when (question.type) {
                                        QuestionType.EMAIL -> KeyboardType.Email
                                        QuestionType.NUMBER -> KeyboardType.Number
                                        else -> KeyboardType.Text
                                    },
                                    errorMessage = uiState.errors[question.id]
                                )
                            }
                            QuestionType.RADIO_GROUP -> {
                                AttendanceRadioGroup(
                                    label = question.label,
                                    options = question.options,
                                    selectedOption = answer,
                                    onOptionSelected = { viewModel.onAnswerChange(question.id, it) },
                                    errorMessage = uiState.errors[question.id]
                                )
                            }
                            QuestionType.DROPDOWN -> {
                                Column {
                                    AttendanceDropdown(
                                        label = question.label,
                                        options = question.options,
                                        selectedOption = answer,
                                        onOptionSelected = { viewModel.onAnswerChange(question.id, it) },
                                        placeholder = question.placeholder,
                                        errorMessage = uiState.errors[question.id]
                                    )
                                    
                                    // Handle "Otro" selection
                                    if (answer == "Otro") {
                                        AttendanceTextField(
                                            label = "Especifique ${question.label.lowercase()}",
                                            value = uiState.answers["${question.id}_other"] ?: "",
                                            onValueChange = { viewModel.onAnswerChange("${question.id}_other", it) },
                                            placeholder = "Indica tu respuesta aquí",
                                            errorMessage = uiState.errors["${question.id}_other"]
                                        )
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            tonalElevation = 2.dp,
                            shape = MaterialTheme.shapes.medium,
                            color = MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { viewModel.onPrivacyAcceptedChange(!uiState.isPrivacyAccepted) }
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = uiState.isPrivacyAccepted,
                                    onCheckedChange = { viewModel.onPrivacyAcceptedChange(it) }
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Acepto el tratamiento de datos personales",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = "Ver políticas de privacidad",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.clickable { onNavigateToPrivacy() }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}

