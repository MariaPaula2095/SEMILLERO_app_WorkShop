package edu.ucundinamarca.workshop.features.ai_chat.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import edu.ucundinamarca.workshop.features.ai_chat.domain.model.AiMessage
import edu.ucundinamarca.workshop.features.ai_chat.presentation.viewmodel.AiChatEvent
import edu.ucundinamarca.workshop.features.ai_chat.presentation.viewmodel.AiChatViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiChatScreen(
    viewModel: AiChatViewModel = hiltViewModel(),
    onBackToMain: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    var textInput by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Asistente IA", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackToMain) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "volver",
                            tint = Color.White
                        )
                    }
                },
            )
        },
        contentWindowInsets = WindowInsets.statusBars
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                reverseLayout = true
            ) {
                items(state.messages.reversed()) { message ->
                    ChatMessageItem(message)
                }
            }

            if (state.error != null) {
                Text(
                    text = state.error!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            ChatInputBar(
                modifier = Modifier.windowInsetsPadding(
                    WindowInsets.ime.union(WindowInsets.navigationBars)
                ),
                textInput = textInput,
                onValueChange = { textInput = it },
                onSend = {
                    if (textInput.isNotBlank()) {
                        viewModel.onEvent(AiChatEvent.SendMessage(textInput))
                        textInput = ""
                    }
                },
                isLoading = state.isLoading
            )
        }
    }
}

@Composable
fun ChatMessageItem(message: AiMessage) {
    val isUser = message.role == AiMessage.Role.USER
    val alignment = if (isUser) Alignment.CenterEnd else Alignment.CenterStart
    val bgColor = if (isUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer
    val textColor = if (isUser) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = alignment
    ) {
        Column(
            modifier = Modifier
                .background(bgColor, RoundedCornerShape(12.dp))
                .padding(12.dp)
                .widthIn(max = 280.dp)
        ) {
            Text(
                text = if (isUser) "Tú" else "Gemini",
                style = MaterialTheme.typography.labelSmall,
                color = textColor.copy(alpha = 0.7f)
            )
            Text(
                text = message.content,
                color = textColor,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun ChatInputBar(
    modifier: Modifier = Modifier,
    textInput: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    isLoading: Boolean
) {
    Surface(
        tonalElevation = 8.dp,
        modifier = modifier
            .fillMaxWidth()
            .imePadding(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = textInput,
                onValueChange = onValueChange,
                modifier = Modifier.weight(1f),
                placeholder = { Text("Escribe aquí...") },
                maxLines = 4,
                enabled = !isLoading,
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            FilledIconButton(
                onClick = onSend,
                enabled = !isLoading && textInput.isNotBlank(),
                modifier = Modifier.size(48.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                } else {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Enviar")
                }
            }
        }
    }
}
