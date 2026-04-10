package edu.ucundinamarca.workshop.features.welcome.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucundinamarca.workshop.R
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(
    onNavigateToHome: () -> Unit
) {
    var showUniversityLogo by remember { mutableStateOf(false) }
    var showWelcomeText by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        showUniversityLogo = true
        delay(1500)

        showUniversityLogo = false
        delay(300)

        showWelcomeText = true
        delay(1500)

        onNavigateToHome()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = showUniversityLogo,
            enter = slideInVertically(
                initialOffsetY = { it / 2 },
                animationSpec = tween(700)
            ) + fadeIn(animationSpec = tween(700)),
            exit = fadeOut(animationSpec = tween(450)) +
                    slideOutVertically(
                        targetOffsetY = { -it / 6 },
                        animationSpec = tween(450)
                    )
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_udec_logo),
                contentDescription = "Logo Universidad de Cundinamarca",
                modifier = Modifier.size(290.dp)
            )
        }

        AnimatedVisibility(
            visible = showWelcomeText,
            enter = fadeIn(animationSpec = tween(800)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Bienvenido al Workshop",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "UCundinamarca 2026",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.92f),
                    textAlign = TextAlign.Center,
                    lineHeight = 30.sp
                )
            }
        }
    }
}