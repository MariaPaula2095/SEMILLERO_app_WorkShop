package edu.ucundinamarca.workshop.features.home.presentation.screen

import android.app.Activity
import edu.ucundinamarca.workshop.R
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucundinamarca.workshop.core.ui.theme.WorkshopTheme
import edu.ucundinamarca.workshop.features.home.presentation.components.ErrorComponent
import edu.ucundinamarca.workshop.features.home.presentation.components.EventDetailCard
import edu.ucundinamarca.workshop.features.home.presentation.components.HeroHeader
import edu.ucundinamarca.workshop.features.home.presentation.components.HomeShimmer
import edu.ucundinamarca.workshop.features.home.presentation.components.SocialSection
import edu.ucundinamarca.workshop.shared.presentation.components.WorkshopAppBar
import edu.ucundinamarca.workshop.features.home.presentation.viewmodel.HomeViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import edu.ucundinamarca.workshop.features.about.presentation.components.Footer

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.foundation.Image
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import coil3.compose.rememberAsyncImagePainter
import edu.ucundinamarca.workshop.core.ui.components.AutoSwipeCarousel
import edu.ucundinamarca.workshop.features.home.data.datasource.HomeMockDataSource
import edu.ucundinamarca.workshop.features.home.domain.model.HomeInfo
import kotlinx.coroutines.delay
@Composable
fun HomeScreen(
    onNavigateToForm: () -> Unit,
    onNavigateToSchedule: () -> Unit,
    onNavigateToAbout: () -> Unit,
    onNavigateToWebView: (String) -> Unit,
    onNavigateToAiChat: () -> Unit,
    onNavigateToEvaluation: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()

) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val spacing = WorkshopTheme.spacing
    val colorScheme = MaterialTheme.colorScheme
    val view = LocalView.current
    val context = view.context as Activity

    //Carrusel
    val source = HomeMockDataSource()
    val homeInfo = source.getMockData()
    val banner = homeInfo.bannerUrl
    AutoSwipeCarousel(imageUrls = banner)

    //icono IA chat
    val logoSize = 90.dp
    val logoURL = "https://drive.google.com/uc?export=download&id=1ta_LZLRpatap0KTaTsL_0OFB2PWkMnoc"

    //variables nuevas para el boton de evaluar
    val infiniteTransition = rememberInfiniteTransition(label = "evaluation_button_animation")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.06f,
        animationSpec = infiniteRepeatable(
            animation = tween(900),
            repeatMode = RepeatMode.Reverse
        ),
        label = "evaluation_button_scale"
    )
    //---------------------------------------------

    DisposableEffect(colorScheme.primary) {
        (context as ComponentActivity).enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                colorScheme.primary.toArgb()
            )
        )
        onDispose { }
    }

    Scaffold(
        topBar = { WorkshopAppBar() },
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            //Icono de Ia Chat con 3 saltos cada 2s
            val offsetY = remember { Animatable(0f) }

            LaunchedEffect(Unit) {
                while (true) {
                    // alturas de cada rebote
                    val bounces = listOf(
                        -30f,
                        -40f,
                        -60f
                    )
                    for (bounce in bounces) {
                        offsetY.animateTo(
                            targetValue = bounce,
                            animationSpec = tween(
                                durationMillis = 300,
                                easing = LinearOutSlowInEasing
                            )
                        )

                        offsetY.animateTo(
                            targetValue = 0f,
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = EaseOutBounce
                            )
                        )
                    }
                    delay(2000)
                }

            }
            FloatingActionButton(
                onClick = onNavigateToAiChat,
                modifier = Modifier.offset(y = offsetY.value.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape,

                ) {
                Image(
                    painter = rememberAsyncImagePainter(logoURL),
                    contentDescription = "Logo",
                    modifier = Modifier.size(logoSize)
                )
            }
        }


    ) { paddingValues ->

        when {
            uiState.isLoading -> {
                HomeShimmer()
            }

            uiState.error != null -> {
                ErrorComponent(
                    message = if (uiState.isOffline) "No hay conexión a internet. Revisa tu red." else uiState.error!!,
                    onRetry = { viewModel.retry() }
                )
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(paddingValues)
                ) {
                    HeroHeader(
                        bannerUrl = uiState.bannerUrl,
                        logoUrl = uiState.logoUrl,
                        title = uiState.eventTitle,
                        onMarathonClick = { onNavigateToWebView(uiState.marathonLink) },
                        onRegisterClick = onNavigateToForm
                    )

                    Spacer(modifier = Modifier.height(spacing.medium))

                    SocialSection(links = uiState.socialLinks)

                    EventDetailCard(
                        date = uiState.eventDate,
                        startTime = uiState.startTime,
                        endTime = uiState.endTime,
                        location = uiState.location,
                        onScheduleClick = onNavigateToSchedule,
                        onAboutClick = onNavigateToAbout
                    )

                    //se agregaa boton de formualrio de evaluacion workshop
                    Spacer(modifier = Modifier.height(spacing.medium))

                    /*
                    Button(
                        onClick = onNavigateToEvaluation,
                        modifier = Modifier
                            .fillMaxWidth() //ocupe todo el ancho
                            .padding(horizontal = spacing.medium) //margen horizontal
                    ) {
                        Text("Evaluar workshop") //texto de boton
                    }
                     */

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = spacing.medium),
                        shape = RoundedCornerShape(24.dp),
                        color = MaterialTheme.colorScheme.primary,
                        tonalElevation = 6.dp,
                        shadowElevation = 6.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            verticalArrangement = Arrangement.Center
                        ) {

                            // TÍTULO
                            Text(
                                text = "Califica el Workshop 2026",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onPrimary
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            // SUBTEXTO
                            Text(
                                text = "Realiza esta encuesta y cuéntanos tu experiencia.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f)
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // BOTÓN CENTRADO ABAJO
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Surface(
                                    onClick = onNavigateToEvaluation,
                                    modifier = Modifier.scale(scale),
                                    shape = CircleShape,
                                    color = MaterialTheme.colorScheme.onPrimary, // BLANCO
                                    shadowElevation = 8.dp
                                ) {
                                    Box(
                                        modifier = Modifier.size(80.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_star_rating),
                                            contentDescription = "Evaluar workshop",
                                            tint = Color.Unspecified,
                                            modifier = Modifier.size(40.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }


                    Spacer(
                        modifier = Modifier
                            .navigationBarsPadding()
                            .height(spacing.extraSmall)
                    )

                    Footer()

                }
            }
        }
    }
}