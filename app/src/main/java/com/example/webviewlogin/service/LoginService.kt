package com.example.webviewlogin.service

import com.example.webviewlogin.model.LoginResult
import com.example.webviewlogin.model.User
import kotlinx.coroutines.delay

/**
 * Mock Login Service
 * 模擬登入服務，用於測試和開發
 */
class LoginService {

    // 模擬的測試帳號
    private val validUsers = mapOf(
        "demo" to "password123",
        "test" to "test123",
        "admin" to "admin123"
    )

    /**
     * 執行登入
     * @param username 使用者名稱
     * @param password 密碼
     * @return LoginResult 登入結果
     */
    suspend fun login(username: String, password: String): LoginResult {
        // 模擬網路延遲
        delay(1000)

        // 驗證輸入
        if (username.isBlank() || password.isBlank()) {
            return LoginResult.Error("使用者名稱或密碼不可為空")
        }

        // 驗證帳號密碼
        val validPassword = validUsers[username]
        return if (validPassword != null && validPassword == password) {
            val token = generateMockToken(username)
            LoginResult.Success(User(username, token))
        } else {
            LoginResult.Error("帳號或密碼錯誤")
        }
    }

    /**
     * 生成模擬的 token
     */
    private fun generateMockToken(username: String): String {
        return "mock_token_${username}_${System.currentTimeMillis()}"
    }

    /**
     * 驗證 token (用於測試)
     */
    fun validateToken(token: String): Boolean {
        return token.startsWith("mock_token_")
    }
}
