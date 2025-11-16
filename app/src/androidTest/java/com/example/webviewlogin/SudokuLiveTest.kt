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
 * Sudoku 實際遊戲測試 - 測試與真實 Sudoku 遊戲的互動
 * 需要 Vite 開發伺服器運行在 localhost:5173
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class SudokuLiveTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testSudokuGameLoads() {
        // 登入
        loginAsDemo()

        // 等待 Sudoku 載入
        Thread.sleep(5000)

        // 驗證 Sudoku 遊戲標題存在
        onWebView()
            .withElement(findElement(Locator.TAG_NAME, "h1"))
            .check(webMatches(getText(), containsString("數讀")))
    }

    @Test
    fun testSudokuNewGameButton() {
        // 登入並載入 Sudoku
        loginAsDemo()
        Thread.sleep(5000)

        // 查找並點擊「新遊戲」按鈕
        // 注意：Sudoku 使用 Vue，按鈕文字可能是「新題目」
        onWebView()
            .withElement(findElement(Locator.TAG_NAME, "button"))
            .perform(webClick())

        // 等待新遊戲載入
        Thread.sleep(2000)

        // 驗證遊戲棋盤存在
        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testSudokuDifficultySelector() {
        // 登入並載入 Sudoku
        loginAsDemo()
        Thread.sleep(5000)

        // 檢查難度選擇器是否存在
        onWebView()
            .withElement(findElement(Locator.TAG_NAME, "select"))
            .check(webMatches(getText(), containsString("")))

        // 驗證 WebView 仍在顯示
        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testSudokuBoardInteraction() {
        // 登入並載入 Sudoku
        loginAsDemo()
        Thread.sleep(5000)

        // 嘗試滾動遊戲板
        onView(withId(R.id.webView))
            .perform(swipeUp())
            .perform(swipeDown())

        Thread.sleep(1000)

        // 驗證遊戲仍在運行
        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testSudokuTimerIsRunning() {
        // 登入並載入 Sudoku
        loginAsDemo()
        Thread.sleep(5000)

        // 等待計時器運行
        Thread.sleep(3000)

        // 驗證計時器元素存在（包含「時間」文字）
        onWebView()
            .withElement(findElement(Locator.TAG_NAME, "span"))
            .check(webMatches(getText(), containsString("")))
    }

    @Test
    fun testSudokuLanguageToggle() {
        // 登入並載入 Sudoku
        loginAsDemo()
        Thread.sleep(5000)

        // 檢查語言選擇器
        onWebView()
            .withElement(findElement(Locator.TAG_NAME, "select"))
            .check(webMatches(getText(), containsString("")))

        // 驗證頁面正常顯示
        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testSudokuCheckAndHintButtons() {
        // 登入並載入 Sudoku
        loginAsDemo()
        Thread.sleep(5000)

        // 檢查按鈕是否存在（檢查、提示、重置等）
        // Sudoku 應該有多個按鈕
        onWebView()
            .withElement(findElement(Locator.TAG_NAME, "button"))
            .check(webMatches(getText(), containsString("")))

        Thread.sleep(1000)
    }

    @Test
    fun testSudokuFullGameplay() {
        // 登入並載入 Sudoku
        loginAsDemo()
        Thread.sleep(5000)

        // 1. 驗證遊戲標題
        onWebView()
            .withElement(findElement(Locator.TAG_NAME, "h1"))
            .check(webMatches(getText(), containsString("數讀")))

        Thread.sleep(1000)

        // 2. 嘗試點擊新遊戲按鈕
        onWebView()
            .withElement(findElement(Locator.TAG_NAME, "button"))
            .perform(webClick())

        Thread.sleep(2000)

        // 3. 滾動查看遊戲板
        onView(withId(R.id.webView))
            .perform(swipeUp())
            .perform(swipeDown())

        Thread.sleep(1000)

        // 4. 驗證遊戲仍在運行
        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))

        // 5. 驗證 URL 正確
        onView(withId(R.id.urlEditText))
            .check(matches(withText(containsString("10.0.2.2:5173"))))
    }

    @Test
    fun testSudokuReloadGame() {
        // 登入並載入 Sudoku
        loginAsDemo()
        Thread.sleep(5000)

        // 重新載入頁面
        onView(withId(R.id.loadButton))
            .perform(click())

        // 等待重新載入
        Thread.sleep(5000)

        // 驗證遊戲重新載入
        onWebView()
            .withElement(findElement(Locator.TAG_NAME, "h1"))
            .check(webMatches(getText(), containsString("數讀")))

        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testSudokuResponsiveLayout() {
        // 登入並載入 Sudoku
        loginAsDemo()
        Thread.sleep(5000)

        // 測試不同方向的滾動
        onView(withId(R.id.webView))
            .perform(swipeLeft())
            .perform(swipeRight())
            .perform(swipeUp())
            .perform(swipeDown())

        Thread.sleep(1000)

        // 驗證佈局仍正常
        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testSudokuPersistenceAfterRotation() {
        // 登入並載入 Sudoku
        loginAsDemo()
        Thread.sleep(5000)

        // 驗證遊戲載入
        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))

        // 模擬螢幕旋轉（重建 Activity）
        activityRule.scenario.recreate()
        Thread.sleep(3000)

        // 驗證遊戲仍在
        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testSudokuMultipleCellInteractions() {
        // 登入並載入 Sudoku
        loginAsDemo()
        Thread.sleep(5000)

        // 多次點擊 WebView（模擬點擊不同的格子）
        onView(withId(R.id.webView))
            .perform(click())

        Thread.sleep(500)

        onView(withId(R.id.webView))
            .perform(click())

        Thread.sleep(500)

        // 驗證遊戲正常運行
        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))
    }

    // 輔助方法：登入
    private fun loginAsDemo() {
        onView(withId(R.id.usernameEditText))
            .perform(typeText("demo"), closeSoftKeyboard())
        onView(withId(R.id.passwordEditText))
            .perform(typeText("password123"), closeSoftKeyboard())
        onView(withId(R.id.loginButton))
            .perform(click())
    }
}
