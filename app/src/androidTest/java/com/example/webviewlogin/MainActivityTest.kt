package com.example.webviewlogin

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Espresso UI 測試 - MainActivity (Login 功能)
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testLoginScreenDisplayed() {
        // 驗證登入畫面的元素都有正確顯示
        onView(withId(R.id.titleTextView))
            .check(matches(isDisplayed()))
        onView(withId(R.id.usernameEditText))
            .check(matches(isDisplayed()))
        onView(withId(R.id.passwordEditText))
            .check(matches(isDisplayed()))
        onView(withId(R.id.loginButton))
            .check(matches(isDisplayed()))
            .check(matches(isEnabled()))
    }

    @Test
    fun testEmptyCredentials() {
        // 測試空白帳號密碼
        onView(withId(R.id.loginButton))
            .perform(click())

        // 應該不會跳轉，仍在登入畫面
        onView(withId(R.id.loginButton))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testInvalidCredentials() {
        // 測試錯誤的帳號密碼
        onView(withId(R.id.usernameEditText))
            .perform(typeText("wronguser"), closeSoftKeyboard())
        onView(withId(R.id.passwordEditText))
            .perform(typeText("wrongpass"), closeSoftKeyboard())

        onView(withId(R.id.loginButton))
            .perform(click())

        // 等待登入處理
        Thread.sleep(2000)

        // 應該仍在登入畫面（因為登入失敗）
        onView(withId(R.id.loginButton))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testSuccessfulLogin() {
        // 測試正確的帳號密碼
        onView(withId(R.id.usernameEditText))
            .perform(typeText("demo"), closeSoftKeyboard())
        onView(withId(R.id.passwordEditText))
            .perform(typeText("password123"), closeSoftKeyboard())

        onView(withId(R.id.loginButton))
            .perform(click())

        // 等待登入處理和頁面跳轉
        Thread.sleep(2000)

        // 驗證已跳轉到 WebView 畫面
        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))
        onView(withId(R.id.userInfoTextView))
            .check(matches(withText("使用者: demo")))
    }

    @Test
    fun testLoginButtonDisabledDuringLogin() {
        // 測試登入期間按鈕應該被禁用
        onView(withId(R.id.usernameEditText))
            .perform(typeText("demo"), closeSoftKeyboard())
        onView(withId(R.id.passwordEditText))
            .perform(typeText("password123"), closeSoftKeyboard())

        onView(withId(R.id.loginButton))
            .check(matches(isEnabled()))
            .perform(click())

        // 檢查進度條顯示
        Thread.sleep(500) // 短暫延遲以捕捉載入狀態
        onView(withId(R.id.progressBar))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testDifferentValidUsers() {
        // 測試不同的有效使用者
        val testUsers = listOf(
            "demo" to "password123",
            "test" to "test123",
            "admin" to "admin123"
        )

        testUsers.forEach { (username, password) ->
            activityRule.scenario.recreate()
            Thread.sleep(500)

            onView(withId(R.id.usernameEditText))
                .perform(clearText(), typeText(username), closeSoftKeyboard())
            onView(withId(R.id.passwordEditText))
                .perform(clearText(), typeText(password), closeSoftKeyboard())

            onView(withId(R.id.loginButton))
                .perform(click())

            Thread.sleep(2000)

            // 驗證登入成功並跳轉
            onView(withId(R.id.webView))
                .check(matches(isDisplayed()))
            onView(withId(R.id.userInfoTextView))
                .check(matches(withText("使用者: $username")))

            // 返回到登入畫面
            onView(withId(R.id.logoutButton))
                .perform(click())
            Thread.sleep(500)
        }
    }
}
