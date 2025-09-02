package com.example.playtogether.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.playtogether.repository.SportRepository

class SportViewModelFactory(
    private val repository: SportRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SportViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SportViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
