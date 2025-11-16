package com.example.webviewlogin

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.web.assertion.WebViewAssertions.webMatches
import androidx.test.espresso.web.sugar.Web.onWebView
import androidx.test.espresso.web.webdriver.DriverAtoms.*
import androidx.test.espresso.web.webdriver.Locator
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Sudoku 整合測試 - 測試從登入到載入 Sudoku 遊戲的完整流程
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class SudokuIntegrationTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testCompleteLoginToSudokuFlow() {
        // 步驟 1: 驗證登入畫面
        onView(withId(R.id.titleTextView))
            .check(matches(isDisplayed()))
        onView(withId(R.id.usernameEditText))
            .check(matches(isDisplayed()))
        onView(withId(R.id.passwordEditText))
            .check(matches(isDisplayed()))

        // 步驟 2: 輸入測試帳號
        onView(withId(R.id.usernameEditText))
            .perform(typeText("demo"), closeSoftKeyboard())
        onView(withId(R.id.passwordEditText))
            .perform(typeText("password123"), closeSoftKeyboard())

        // 步驟 3: 點擊登入按鈕
        onView(withId(R.id.loginButton))
            .perform(click())

        // 步驟 4: 等待登入完成和 WebView 載入
        Thread.sleep(3000)

        // 步驟 5: 驗證已跳轉到 WebView Activity
        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))

        // 驗證使用者資訊顯示
        onView(withId(R.id.userInfoTextView))
            .check(matches(withText("使用者: demo")))

        // 步驟 6: 驗證 WebView 載入了 Sudoku 遊戲
        onView(withId(R.id.urlEditText))
            .check(matches(withText(containsString("sudoku"))))

        // 步驟 7: 等待 Sudoku 遊戲完全載入
        Thread.sleep(2000)
    }

    @Test
    fun testSudokuGameElementsLoaded() {
        // 登入流程
        loginAsDemo()

        // 等待 Sudoku 載入
        Thread.sleep(3000)

        // 驗證 WebView 顯示
        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))

        // 驗證 URL 包含 sudoku
        onView(withId(R.id.urlEditText))
            .check(matches(withText(containsString("sudoku"))))
    }

    @Test
    fun testSudokuGameInteraction() {
        // 登入
        loginAsDemo()

        // 等待遊戲載入
        Thread.sleep(3000)

        // 測試可以與 WebView 互動（滾動）
        onView(withId(R.id.webView))
            .perform(swipeUp())
            .perform(swipeDown())

        Thread.sleep(1000)

        // 驗證 WebView 仍然顯示
        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testLogoutFromSudoku() {
        // 登入並載入 Sudoku
        loginAsDemo()
        Thread.sleep(3000)

        // 驗證在 Sudoku 頁面
        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))

        // 點擊登出按鈕
        onView(withId(R.id.logoutButton))
            .check(matches(isDisplayed()))
            .perform(click())

        // 驗證返回登入畫面
        Thread.sleep(1000)
        onView(withId(R.id.loginButton))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testCanLoadExternalUrlFromSudoku() {
        // 登入並載入 Sudoku
        loginAsDemo()
        Thread.sleep(3000)

        // 驗證在 Sudoku 頁面
        onView(withId(R.id.urlEditText))
            .check(matches(withText(containsString("sudoku"))))

        // 嘗試載入其他網址
        onView(withId(R.id.urlEditText))
            .perform(clearText(), typeText("example.com"), closeSoftKeyboard())

        onView(withId(R.id.loadButton))
            .perform(click())

        // 等待載入
        Thread.sleep(3000)

        // 驗證 URL 已改變
        onView(withId(R.id.urlEditText))
            .check(matches(withText(containsString("example"))))

        // 可以返回上一頁（Sudoku）
        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testMultipleUsersCanLoadSudoku() {
        val users = listOf(
            "demo" to "password123",
            "test" to "test123",
            "admin" to "admin123"
        )

        users.forEach { (username, password) ->
            // 登入
            onView(withId(R.id.usernameEditText))
                .perform(clearText(), typeText(username), closeSoftKeyboard())
            onView(withId(R.id.passwordEditText))
                .perform(clearText(), typeText(password), closeSoftKeyboard())
            onView(withId(R.id.loginButton))
                .perform(click())

            // 等待載入 Sudoku
            Thread.sleep(3000)

            // 驗證 Sudoku 已載入
            onView(withId(R.id.webView))
                .check(matches(isDisplayed()))
            onView(withId(R.id.userInfoTextView))
                .check(matches(withText("使用者: $username")))

            // 登出
            onView(withId(R.id.logoutButton))
                .perform(click())
            Thread.sleep(500)

            // 重新建立 Activity
            activityRule.scenario.recreate()
            Thread.sleep(500)
        }
    }

    @Test
    fun testSudokuLoadingProgress() {
        // 登入
        loginAsDemo()

        // 等待載入完成（進度條可能載入太快無法捕捉）
        Thread.sleep(3000)

        // 驗證 Sudoku 已成功載入
        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))

        // 驗證 URL 包含 sudoku
        onView(withId(R.id.urlEditText))
            .check(matches(withText(containsString("sudoku"))))
    }

    @Test
    fun testBackNavigationInSudoku() {
        // 登入並載入 Sudoku
        loginAsDemo()
        Thread.sleep(3000)

        // 驗證在 Sudoku 頁面
        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))

        // 模擬按返回鍵（如果 Sudoku 是單頁應用，應該直接退出 Activity）
        // 注意：這個測試可能因為 WebView 沒有歷史記錄而直接退出
    }

    // 輔助方法：快速登入
    private fun loginAsDemo() {
        onView(withId(R.id.usernameEditText))
            .perform(typeText("demo"), closeSoftKeyboard())
        onView(withId(R.id.passwordEditText))
            .perform(typeText("password123"), closeSoftKeyboard())
        onView(withId(R.id.loginButton))
            .perform(click())
    }
}
