package edu.ucundinamarca.workshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import edu.ucundinamarca.workshop.core.navigation.AppNavigation
import edu.ucundinamarca.workshop.core.ui.theme.WorkshopTheme
import edu.ucundinamarca.workshop.core.network.ConnectivityObserver
import edu.ucundinamarca.workshop.core.ui.components.OfflineBanner
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import javax.inject.Inject
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val status by connectivityObserver.observe().collectAsState(initial = ConnectivityObserver.Status.Available)
            val isOffline = status != ConnectivityObserver.Status.Available

            WorkshopTheme(useDynamicColor = false) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column {
                        OfflineBanner(isVisible = isOffline)
                        AppNavigation()
                    }
                }
            }
        }
    }
}
