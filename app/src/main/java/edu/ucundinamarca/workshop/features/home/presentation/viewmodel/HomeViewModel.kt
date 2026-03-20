package edu.ucundinamarca.workshop.features.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import edu.ucundinamarca.workshop.features.home.domain.usecase.GetHomeInfoUseCase
import edu.ucundinamarca.workshop.core.network.ConnectivityObserver
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeInfoUseCase: GetHomeInfoUseCase,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var fetchJob: kotlinx.coroutines.Job? = null

    init {
        observeConnectivity()
        fetchHomeData()
    }

    private fun observeConnectivity() {
        viewModelScope.launch {
            connectivityObserver.observe().collect { status ->
                _uiState.update { it.copy(isOffline = status != ConnectivityObserver.Status.Available) }
            }
        }
    }

    fun retry() {
        fetchHomeData()
    }

    private fun fetchHomeData() {
        fetchJob?.cancel()
        _uiState.update { it.copy(isLoading = true, error = null) }
        
        fetchJob = viewModelScope.launch {
            getHomeInfoUseCase().collect { result ->
                result.onSuccess { info ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            eventTitle = info.eventTitle,
                            bannerUrl = info.bannerUrl,
                            logoUrl = info.logoUrl,
                            eventDate = info.eventDate,
                            startTime = info.startTime,
                            endTime = info.endTime,
                            location = info.location,
                            marathonLink = info.marathonLink,
                            socialLinks = SocialLinks(
                                facebookUrl = info.socialLinks.facebookUrl,
                                facebookIconUrl = info.socialLinks.facebookIconUrl,
                                youtubeUrl = info.socialLinks.youtubeUrl,
                                youtubeIconUrl = info.socialLinks.youtubeIconUrl,
                                instagramUrl = info.socialLinks.instagramUrl,
                                instagramIconUrl = info.socialLinks.instagramIconUrl
                            )
                        )
                    }
                }.onFailure { error ->
                    val userFriendlyMessage = when {
                        error.message?.contains("permission-denied", ignoreCase = true) == true -> 
                            "No tienes permisos para ver esta información."
                        error.message?.contains("unavailable", ignoreCase = true) == true -> 
                            "El servicio no está disponible temporalmente."
                        else -> "No pudimos cargar la información. Inténtalo de nuevo."
                    }
                    _uiState.update { it.copy(isLoading = false, error = userFriendlyMessage) }
                }
            }
        }
    }
}