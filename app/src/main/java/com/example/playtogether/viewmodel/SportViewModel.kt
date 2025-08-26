package com.example.playtogether.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playtogether.model.Sport
import com.example.playtogether.repository.SportRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SportViewModel(private val repository: SportRepository) : ViewModel() {

    // État exposé pour l'UI
    private val _sports = MutableStateFlow<List<Sport>>(emptyList())
    val sports: StateFlow<List<Sport>> = _sports

    // Charger la liste des sports depuis la DB ou réseau
    fun loadSports() {
        viewModelScope.launch {
            _sports.value = repository.getSports()
        }
    }
}
