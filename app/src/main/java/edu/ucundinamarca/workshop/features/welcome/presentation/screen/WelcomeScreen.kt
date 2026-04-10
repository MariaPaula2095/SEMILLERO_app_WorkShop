package edu.ucundinamarca.workshop.features.welcome.presentation.screen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import edu.ucundinamarca.workshop.R
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(
    onNavigateToHome: () -> Unit
) {
    var startAnimation by remember { mutableStateOf(false) }
    var showText by remember { mutableStateOf(false) }

    val logoOffsetY by animateDpAsState(
        targetValue = if (startAnimation) (-120).dp else 0.dp,
        animationSpec = tween(durationMillis = 900),
        label = "logoOffsetY"
    )

    val logoScale by animateFloatAsState(
        targetValue = if (startAnimation) 0.55f else 1f,
        animationSpec = tween(durationMillis = 900),
        label = "logoScale"
    )

    val textOffsetY by animateDpAsState(
        targetValue = if (showText) 0.dp else 40.dp,
        animationSpec = tween(durationMillis = 800),
        label = "textOffsetY"
    )

    val textAlpha by animateFloatAsState(
        targetValue = if (showText) 1f else 0f,
        animationSpec = tween(durationMillis = 800),
        label = "textAlpha"
    )

    LaunchedEffect(Unit) {
        delay(1200)
        startAnimation = true

        delay(500)
        showText = true

        delay(2200)
        onNavigateToHome()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_udec_logo),
            contentDescription = "Logo Universidad de Cundinamarca",
            modifier = Modifier
                .size(260.dp)
                .graphicsLayer {
                    scaleX = logoScale
                    scaleY = logoScale
                    translationY = logoOffsetY.toPx()
                }
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 140.dp)
                .offset(y = textOffsetY)
                .alpha(textAlpha),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Workshop",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "UCundinamarca 2026",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f),
                textAlign = TextAlign.Center
            )
        }
    }
}