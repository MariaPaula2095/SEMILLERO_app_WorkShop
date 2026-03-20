package edu.ucundinamarca.workshop.features.home.presentation.components

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.core.net.toUri
import edu.ucundinamarca.workshop.core.ui.theme.WorkshopTheme
import edu.ucundinamarca.workshop.features.home.presentation.viewmodel.SocialLinks

@Composable
fun SocialSection(
    links: SocialLinks,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val spacing = WorkshopTheme.spacing
    
    val socialItems = listOf(
        SocialItem("Facebook", links.facebookUrl, links.facebookIconUrl),
        SocialItem("YouTube", links.youtubeUrl, links.youtubeIconUrl),
        SocialItem("Instagram", links.instagramUrl, links.instagramIconUrl)
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(spacing.medium),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Text(
            text = "Transmisión en Vivo",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = spacing.medium)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            socialItems.forEach { item ->
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { 
                            if (item.url.isNotEmpty()) {
                                val intent = Intent(Intent.ACTION_VIEW, item.url.toUri())
                                context.startActivity(intent)
                            }
                        },
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = coil3.compose.rememberAsyncImagePainter(item.iconUrl),
                        contentDescription = item.name,
                        modifier = Modifier.size(WorkshopTheme.iconography.extraLarge)
                    )
                    Spacer(modifier = Modifier.height(spacing.extraSmall))
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

private data class SocialItem(val name: String, val url: String, val iconUrl: String)
