package edu.ucundinamarca.workshop.shared.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucundinamarca.workshop.R

@Composable
fun WorkshopAppBar(
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        val logoSize = maxWidth / 7

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = contentColor
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }

            Image(
                painter = painterResource(id = R.drawable.ic_udec_logo),
                contentDescription = "Logo",
                modifier = Modifier.size(logoSize)
            )

            Spacer(modifier = Modifier.width(4.dp))

            Column {
                Text(
                    text = "UNIVERSIDAD DE",
                    fontSize = 12.sp,
                    color = Color.White,
                    lineHeight = 14.sp
                )
                Text(
                    text = "CUNDINAMARCA",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    color = Color.White,
                    lineHeight = 22.sp
                )
            }
        }
    }
}
