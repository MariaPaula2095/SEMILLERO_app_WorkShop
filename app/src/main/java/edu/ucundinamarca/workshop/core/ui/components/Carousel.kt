package edu.ucundinamarca.workshop.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
/*
Este archivo solo se encarga de:

mostrar el pager
moverlo automáticamente
mostrar indicadores
renderizar el contenido que le manden
 */

@Composable
fun WorkshopCarousel(
    itemCount: Int,
    modifier: Modifier = Modifier,
    autoScroll: Boolean = true,
    autoScrollDelayMillis: Long = 3000L,
    indicatorActiveColor: Color = Color.White,
    indicatorInactiveColor: Color = Color.LightGray,
    content: @Composable (page: Int) -> Unit
) {
    if (itemCount <= 0) return

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { itemCount }
    )

    LaunchedEffect(Unit) {
        if (!autoScroll || itemCount <= 1) return@LaunchedEffect

        while (true) {
            delay(autoScrollDelayMillis)
            val nextPage = (pagerState.currentPage + 1) % itemCount
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        HorizontalPager(
            state = pagerState
        ) { page ->
            content(page)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(itemCount) { index ->
                val color = if (pagerState.currentPage == index) {
                    indicatorActiveColor
                } else {
                    indicatorInactiveColor
                }

                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(8.dp)
                        .background(
                            color = color,
                            shape = CircleShape
                        )
                )
            }
        }
    }
}