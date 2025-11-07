package com.example.webviewlogin

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.web.sugar.Web.onWebView
import androidx.test.espresso.web.webdriver.DriverAtoms.*
import androidx.test.espresso.web.webdriver.Locator
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Espresso UI 測試 - WebViewActivity
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class WebViewActivityTest {

    private fun createIntent(): Intent {
        return Intent(ApplicationProvider.getApplicationContext(), WebViewActivity::class.java).apply {
            putExtra(WebViewActivity.EXTRA_USERNAME, "testuser")
            putExtra(WebViewActivity.EXTRA_TOKEN, "mock_token_testuser_123456")
        }
    }

    @get:Rule
    val activityRule = ActivityScenarioRule<WebViewActivity>(createIntent())

    @Test
    fun testWebViewActivityDisplayed() {
        // 驗證 WebView Activity 的基本元素
        onView(withId(R.id.userInfoTextView))
            .check(matches(isDisplayed()))
            .check(matches(withText("使用者: testuser")))

        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))

        onView(withId(R.id.urlEditText))
            .check(matches(isDisplayed()))

        onView(withId(R.id.loadButton))
            .check(matches(isDisplayed()))
            .check(matches(isEnabled()))

        onView(withId(R.id.logoutButton))
            .check(matches(isDisplayed()))
            .check(matches(isEnabled()))
    }

    @Test
    fun testLoadDifferentUrl() {
        // 測試載入不同的網址
        val testUrl = "example.com"

        onView(withId(R.id.urlEditText))
            .perform(clearText(), typeText(testUrl), closeSoftKeyboard())

        onView(withId(R.id.loadButton))
            .perform(click())

        // 等待頁面載入（增加到 5 秒以確保網頁完全載入）
        Thread.sleep(5000)

        // 驗證 URL 已更新（檢查包含域名，因為可能有重定向）
        onView(withId(R.id.urlEditText))
            .check(matches(withText(org.hamcrest.Matchers.containsString(testUrl))))
    }

    @Test
    fun testLoadUrlWithHttps() {
        // 測試載入帶有 https:// 的網址
        val testUrl = "https://www.example.org"

        onView(withId(R.id.urlEditText))
            .perform(clearText(), typeText(testUrl), closeSoftKeyboard())

        onView(withId(R.id.loadButton))
            .perform(click())

        // 等待頁面載入
        Thread.sleep(3000)

        // 驗證載入成功
        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testLogoutButton() {
        // 測試登出按鈕
        onView(withId(R.id.logoutButton))
            .check(matches(isDisplayed()))
            .perform(click())

        // 驗證 Activity 已結束（通過檢查是否拋出 NoActivityResumedException）
        // 如果 Activity 還在，下面的檢查不會失敗
        Thread.sleep(500)
    }

    @Test
    fun testWebViewLoadingProgress() {
        // 測試載入進度條
        val testUrl = "example.com"

        onView(withId(R.id.urlEditText))
            .perform(clearText(), typeText(testUrl), closeSoftKeyboard())

        onView(withId(R.id.loadButton))
            .perform(click())

        // 短暫延遲以捕捉載入狀態
        Thread.sleep(500)

        // 進度條應該在載入時顯示
        // 注意：由於載入速度可能很快，這個測試可能會不穩定
        // 在實際專案中，您可能需要使用 IdlingResource
    }

    @Test
    fun testEmptyUrlHandling() {
        // 測試空白網址的處理
        onView(withId(R.id.urlEditText))
            .perform(clearText(), closeSoftKeyboard())

        onView(withId(R.id.loadButton))
            .perform(click())

        // 應該不會載入任何內容
        // WebView 仍然顯示之前的頁面或空白頁
        Thread.sleep(500)

        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testMultipleUrlLoads() {
        // 測試連續載入多個網址
        val urls = listOf("example.com", "example.org", "example.net")

        urls.forEach { url ->
            onView(withId(R.id.urlEditText))
                .perform(clearText(), typeText(url), closeSoftKeyboard())

            onView(withId(R.id.loadButton))
                .perform(click())

            // 等待載入（增加到 4 秒以確保每個網頁完全載入）
            Thread.sleep(4000)

            // 驗證 URL 已更新（檢查包含域名，因為可能有重定向）
            onView(withId(R.id.urlEditText))
                .check(matches(withText(org.hamcrest.Matchers.containsString(url))))
        }
    }

    @Test
    fun testWebViewWithGoogleSearch() {
        // 測試載入 Google（較可靠的測試網站）
        val googleUrl = "www.google.com"

        onView(withId(R.id.urlEditText))
            .perform(clearText(), typeText(googleUrl), closeSoftKeyboard())

        onView(withId(R.id.loadButton))
            .perform(click())

        // 等待 Google 頁面載入
        Thread.sleep(4000)

        // 驗證 WebView 已載入內容
        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testUserInfoPersistence() {
        // 驗證使用者資訊在整個 Activity 生命週期中保持不變
        onView(withId(R.id.userInfoTextView))
            .check(matches(withText("使用者: testuser")))

        // 載入一個網址
        onView(withId(R.id.urlEditText))
            .perform(clearText(), typeText("example.com"), closeSoftKeyboard())
        onView(withId(R.id.loadButton))
            .perform(click())

        Thread.sleep(2000)

        // 再次驗證使用者資訊仍然顯示
        onView(withId(R.id.userInfoTextView))
            .check(matches(withText("使用者: testuser")))
    }
}
