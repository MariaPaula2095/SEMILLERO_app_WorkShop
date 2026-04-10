package edu.ucundinamarca.workshop.features.evaluation.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import edu.ucundinamarca.workshop.features.evaluation.domain.model.QuestionType
import edu.ucundinamarca.workshop.features.evaluation.presentation.components.EvaluationRadioGroup
import edu.ucundinamarca.workshop.features.evaluation.presentation.components.EvaluationTextField
import edu.ucundinamarca.workshop.features.evaluation.presentation.viewmodel.EvaluationViewModel
import edu.ucundinamarca.workshop.shared.presentation.components.WorkshopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EvaluacionWorkshopScreen(
    onNavigateBack: () -> Unit,
    viewModel: EvaluationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val showSuccessDialog = uiState.isSuccess

    /*
    if (uiState.isSuccess) {
        onNavigateBack()
    }
     */

    //Mostrar mensaje de confirmacion de envio
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.resetState()
                        onNavigateBack()
                    }
                ) {
                    Text("Aceptar")
                }
            },
            title = {
                Text("¡Gracias por tus respuestas!")
            },
            text = {
                Text("El formulario fue enviado correctamente.")
            }
        )
    }



    Scaffold(
        topBar = {
            WorkshopAppBar(onBackClick = onNavigateBack)
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
                        enabled = !uiState.isSubmitting,
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
                                "Enviar evaluación",
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
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
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
                            QuestionType.TEXT -> {
                                EvaluationTextField(
                                    label = question.label,
                                    value = answer,
                                    onValueChange = { viewModel.onAnswerChange(question.id, it) },
                                    placeholder = question.placeholder,
                                    errorMessage = uiState.errors[question.id]
                                )
                            }

                            QuestionType.RADIO_GROUP -> {
                                EvaluationRadioGroup(
                                    label = question.label,
                                    options = question.options,
                                    selectedOption = answer,
                                    onOptionSelected = { viewModel.onAnswerChange(question.id, it) },
                                    errorMessage = uiState.errors[question.id]
                                )
                            }

                            else -> {}
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}