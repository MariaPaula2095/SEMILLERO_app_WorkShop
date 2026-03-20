package edu.ucundinamarca.workshop.features.home.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import edu.ucundinamarca.workshop.core.ui.theme.WorkshopTheme

@Composable
fun HeroHeader(
    bannerUrl: String,
    logoUrl: String,
    title: String,
    onMarathonClick: () -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = WorkshopTheme.spacing
    
    Column(modifier = modifier) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp),
            shape = RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp),
            tonalElevation = 4.dp,
            shadowElevation = 8.dp
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = bannerUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.45f),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(spacing.large),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.padding(bottom = spacing.medium)
                    ) {
                        AsyncImage(
                            model = logoUrl,
                            contentDescription = "Event Logo",
                            modifier = Modifier
                                .height(110.dp)
                                .padding(spacing.small),
                            contentScale = ContentScale.Fit
                        )
                    }
                    
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            textAlign = TextAlign.Center
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
        
        Row(
            modifier = Modifier
                .padding(spacing.medium)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing.small)
        ) {
            Button(
                onClick = onMarathonClick,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    "Maratón de\nProgramación",
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Center,
                    lineHeight = MaterialTheme.typography.labelSmall.lineHeight
                )
            }
            
            OutlinedButton(
                onClick = onRegisterClick,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = MaterialTheme.shapes.medium,
            ) {
                Text(
                    "Inscribirse al\nWorkshop",
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Center,
                    lineHeight = MaterialTheme.typography.labelSmall.lineHeight
                )
            }
        }
    }
}
