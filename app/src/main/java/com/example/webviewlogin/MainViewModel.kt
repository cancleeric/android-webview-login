package com.example.webviewlogin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.webviewlogin.model.LoginResult
import com.example.webviewlogin.model.User
import com.example.webviewlogin.service.LoginService
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val loginService = LoginService()

    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> = _loginState

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _loginState.value = LoginState.Error("請輸入帳號和密碼")
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            try {
                when (val result = loginService.login(username, password)) {
                    is LoginResult.Success -> {
                        _loginState.value = LoginState.Success(result.user)
                    }
                    is LoginResult.Error -> {
                        _loginState.value = LoginState.Error(result.message)
                    }
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("登入失敗：${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetLoginState() {
        _loginState.value = null
    }

    sealed class LoginState {
        data class Success(val user: User) : LoginState()
        data class Error(val message: String) : LoginState()
    }
}
