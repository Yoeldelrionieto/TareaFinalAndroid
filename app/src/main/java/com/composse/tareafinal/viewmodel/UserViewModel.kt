package com.composse.tareafinal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composse.tareafinal.network.RetrofitInstance
import com.composse.tareafinal.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            try {
                val users = RetrofitInstance.api.getUsers()
                _users.value = users
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
