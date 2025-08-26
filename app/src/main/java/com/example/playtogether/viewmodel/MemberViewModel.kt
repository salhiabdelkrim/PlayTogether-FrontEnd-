package com.example.playtogether.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playtogether.model.Member
import com.example.playtogether.repository.MemberRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MemberViewModel : ViewModel() {
    private val _member = MutableStateFlow<Member?>(null)
    val member: StateFlow<Member?> get() = _member



    fun loadMember(username: String) {
        viewModelScope.launch {
            try {
                _member.value = MemberRepository.getMemberByUsername(username)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getMemberByUsername(username: String): Member? {
        return if (_member.value?.username == username) {
            _member.value
        } else {
            // Optionnel : charger depuis le repository de manière synchrone
            // ATTENTION : si repository fait un appel réseau, il faut l'appeler dans un coroutine
            null
        }
    }

}