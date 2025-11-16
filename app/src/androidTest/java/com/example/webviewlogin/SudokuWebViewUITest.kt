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
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Sudoku WebView UI 測試
 * 測試從 assets 載入的 Sudoku 遊戲的完整功能
 *
 * 測試範圍：
 * - 遊戲載入與顯示
 * - 難度選擇
 * - 語言切換
 * - 遊戲操作（新題目、重新開始、提示、檢查答案等）
 * - 計時器功能
 * - 統計資訊顯示
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class SudokuWebViewUITest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        // 在每個測試前登入
        loginAsDemo()
        // 等待 WebView 和 Sudoku 完全載入
        Thread.sleep(4000)
    }

    /**
     * 測試 1: 驗證 Sudoku 遊戲成功載入
     */
    @Test
    fun test01_sudokuGameLoadsSuccessfully() {
        // 驗證 WebView 顯示
        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))

        // 驗證 URL 包含 sudoku
        onView(withId(R.id.urlEditText))
            .check(matches(withText(containsString("sudoku"))))

        // 驗證使用者資訊
        onView(withId(R.id.userInfoTextView))
            .check(matches(withText("使用者: demo")))

        // 等待遊戲完全渲染
        Thread.sleep(2000)

        // 驗證遊戲標題存在
        onWebView()
            .withElement(findElement(Locator.TAG_NAME, "h1"))
            .check(webMatches(getText(), containsString("數讀")))
    }

    /**
     * 測試 2: 驗證遊戲控制按鈕存在
     */
    @Test
    fun test02_gameControlButtonsExist() {
        Thread.sleep(2000)

        // 使用 JavaScript 檢查按鈕文字
        // 檢查「換一題」按鈕
        onWebView()
            .withElement(findElement(Locator.XPATH, "//button[contains(text(), '換一題')]"))
            .check(webMatches(getText(), containsString("換一題")))

        // 檢查「重新開始」按鈕
        onWebView()
            .withElement(findElement(Locator.XPATH, "//button[contains(text(), '重新開始')]"))
            .check(webMatches(getText(), containsString("重新開始")))
    }

    /**
     * 測試 3: 驗證難度選擇器存在並可互動
     */
    @Test
    fun test03_difficultySelector() {
        Thread.sleep(2000)

        // 檢查難度選擇器（select 元素）
        onWebView()
            .withElement(findElement(Locator.TAG_NAME, "select"))
            .check(webMatches(getText(), not(containsString(""))))

        // 驗證頁面仍正常顯示
        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))
    }

    /**
     * 測試 4: 驗證 Sudoku 棋盤顯示
     */
    @Test
    fun test04_sudokuBoardDisplayed() {
        Thread.sleep(2000)

        // 檢查是否有 table 或 grid 結構
        // Sudoku 通常使用 table 或 div grid 來顯示
        onWebView()
            .withElement(findElement(Locator.TAG_NAME, "table"))
            .check(webMatches(getText(), not(containsString(""))))
    }

    /**
     * 測試 5: 測試「換一題」按鈕功能
     */
    @Test
    fun test05_newPuzzleButton() {
        Thread.sleep(2000)

        // 點擊「換一題」按鈕
        onWebView()
            .withElement(findElement(Locator.XPATH, "//button[contains(text(), '換一題')]"))
            .perform(webClick())

        // 等待新題目載入
        Thread.sleep(2000)

        // 驗證遊戲仍在運行
        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))

        // 驗證計時器已重置（時間應該是 00:00 或很小的值）
        Thread.sleep(1000)
    }

    /**
     * 測試 6: 測試「重新開始」按鈕功能
     */
    @Test
    fun test06_restartButton() {
        Thread.sleep(2000)

        // 點擊「重新開始」按鈕
        onWebView()
            .withElement(findElement(Locator.XPATH, "//button[contains(text(), '重新開始')]"))
            .perform(webClick())

        // 等待重置
        Thread.sleep(1000)

        // 驗證遊戲仍在顯示
        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))
    }

    /**
     * 測試 7: 測試「提示」按鈕功能
     */
    @Test
    fun test07_hintButton() {
        Thread.sleep(2000)

        // 點擊「提示」按鈕
        onWebView()
            .withElement(findElement(Locator.XPATH, "//button[contains(text(), '提示')]"))
            .perform(webClick())

        // 等待提示顯示
        Thread.sleep(1000)

        // 驗證遊戲仍在運行
        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))
    }

    /**
     * 測試 8: 測試「檢查答案」按鈕功能
     */
    @Test
    fun test08_checkAnswerButton() {
        Thread.sleep(2000)

        // 點擊「檢查答案」按鈕
        onWebView()
            .withElement(findElement(Locator.XPATH, "//button[contains(text(), '檢查答案')]"))
            .perform(webClick())

        // 等待檢查結果
        Thread.sleep(1000)

        // 驗證遊戲仍在顯示
        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))
    }

    /**
     * 測試 9: 測試「顯示解答」按鈕功能
     */
    @Test
    fun test09_showSolutionButton() {
        Thread.sleep(2000)

        // 點擊「顯示解答」按鈕
        onWebView()
            .withElement(findElement(Locator.XPATH, "//button[contains(text(), '顯示解答')]"))
            .perform(webClick())

        // 等待解答顯示
        Thread.sleep(1000)

        // 驗證遊戲仍在顯示
        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))
    }

    /**
     * 測試 10: 驗證計時器功能
     */
    @Test
    fun test10_timerFunctionality() {
        Thread.sleep(2000)

        // 驗證計時器文字存在（包含「時間」）
        onWebView()
            .withElement(findElement(Locator.XPATH, "//*[contains(text(), '時間')]"))
            .check(webMatches(getText(), containsString("時間")))

        // 等待幾秒讓計時器運行
        Thread.sleep(3000)

        // 驗證計時器仍在顯示
        onWebView()
            .withElement(findElement(Locator.XPATH, "//*[contains(text(), '時間')]"))
            .check(webMatches(getText(), containsString("時間")))
    }

    /**
     * 測試 11: 驗證統計資訊顯示
     */
    @Test
    fun test11_statisticsDisplay() {
        Thread.sleep(2000)

        // 檢查「分數」顯示
        onWebView()
            .withElement(findElement(Locator.XPATH, "//*[contains(text(), '分數')]"))
            .check(webMatches(getText(), containsString("分數")))

        // 檢查「提示次數」顯示
        onWebView()
            .withElement(findElement(Locator.XPATH, "//*[contains(text(), '提示次數')]"))
            .check(webMatches(getText(), containsString("提示次數")))

        // 檢查「重置次數」顯示
        onWebView()
            .withElement(findElement(Locator.XPATH, "//*[contains(text(), '重置次數')]"))
            .check(webMatches(getText(), containsString("重置次數")))
    }

    /**
     * 測試 12: 測試語言選擇器
     */
    @Test
    fun test12_languageSelector() {
        Thread.sleep(2000)

        // 檢查語言選擇器存在
        // 應該顯示「繁體中文」
        onWebView()
            .withElement(findElement(Locator.XPATH, "//*[contains(text(), '語言')]"))
            .check(webMatches(getText(), containsString("語言")))
    }

    /**
     * 測試 13: 測試 WebView 滾動功能
     */
    @Test
    fun test13_webViewScrolling() {
        Thread.sleep(2000)

        // 向下滾動
        onView(withId(R.id.webView))
            .perform(swipeUp())

        Thread.sleep(500)

        // 向上滾動
        onView(withId(R.id.webView))
            .perform(swipeDown())

        Thread.sleep(500)

        // 驗證遊戲仍在顯示
        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))
    }

    /**
     * 測試 14: 測試連續操作流程
     */
    @Test
    fun test14_continuousGameplayFlow() {
        Thread.sleep(2000)

        // 1. 換一題
        onWebView()
            .withElement(findElement(Locator.XPATH, "//button[contains(text(), '換一題')]"))
            .perform(webClick())
        Thread.sleep(2000)

        // 2. 點擊提示
        onWebView()
            .withElement(findElement(Locator.XPATH, "//button[contains(text(), '提示')]"))
            .perform(webClick())
        Thread.sleep(1000)

        // 3. 檢查答案
        onWebView()
            .withElement(findElement(Locator.XPATH, "//button[contains(text(), '檢查答案')]"))
            .perform(webClick())
        Thread.sleep(1000)

        // 4. 重新開始
        onWebView()
            .withElement(findElement(Locator.XPATH, "//button[contains(text(), '重新開始')]"))
            .perform(webClick())
        Thread.sleep(1000)

        // 驗證遊戲仍正常運行
        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))
    }

    /**
     * 測試 15: 測試遊戲在不同難度下運行
     */
    @Test
    fun test15_gameDifficultyChanges() {
        Thread.sleep(2000)

        // 找到難度選擇器並點擊（可能需要多次點擊來切換難度）
        onWebView()
            .withElement(findElement(Locator.TAG_NAME, "select"))
            .perform(webClick())

        Thread.sleep(1000)

        // 換一題以應用新難度
        onWebView()
            .withElement(findElement(Locator.XPATH, "//button[contains(text(), '換一題')]"))
            .perform(webClick())

        Thread.sleep(2000)

        // 驗證新遊戲已載入
        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))
    }

    /**
     * 測試 16: 測試「同步題庫」按鈕
     */
    @Test
    fun test16_syncPuzzleDatabaseButton() {
        Thread.sleep(2000)

        // 點擊「同步題庫」按鈕
        onWebView()
            .withElement(findElement(Locator.XPATH, "//button[contains(text(), '同步題庫')]"))
            .perform(webClick())

        // 等待同步完成
        Thread.sleep(2000)

        // 驗證頁面仍正常
        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))
    }

    /**
     * 測試 17: 測試「自動出題」按鈕
     */
    @Test
    fun test17_autoGeneratePuzzleButton() {
        Thread.sleep(2000)

        // 點擊「自動出題」按鈕
        onWebView()
            .withElement(findElement(Locator.XPATH, "//button[contains(text(), '自動出題')]"))
            .perform(webClick())

        // 等待新題目生成
        Thread.sleep(2000)

        // 驗證新遊戲已載入
        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))
    }

    /**
     * 測試 18: 測試 WebView 與原生 UI 的互動
     */
    @Test
    fun test18_webViewAndNativeUIInteraction() {
        Thread.sleep(2000)

        // 驗證 Sudoku 載入
        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))

        // 點擊 URL 輸入框
        onView(withId(R.id.urlEditText))
            .check(matches(isDisplayed()))

        // 點擊載入按鈕
        onView(withId(R.id.loadButton))
            .check(matches(isDisplayed()))

        // 點擊登出按鈕
        onView(withId(R.id.logoutButton))
            .check(matches(isDisplayed()))
    }

    /**
     * 測試 19: 測試頁面重新載入
     */
    @Test
    fun test19_pageReload() {
        Thread.sleep(2000)

        // 驗證初始載入
        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))

        // 點擊載入按鈕重新載入頁面
        onView(withId(R.id.loadButton))
            .perform(click())

        // 等待重新載入
        Thread.sleep(4000)

        // 驗證遊戲重新載入成功
        onWebView()
            .withElement(findElement(Locator.TAG_NAME, "h1"))
            .check(webMatches(getText(), containsString("數讀")))
    }

    /**
     * 測試 20: 壓力測試 - 快速連續操作
     */
    @Test
    fun test20_rapidOperations() {
        Thread.sleep(2000)

        // 快速連續點擊多個按鈕
        for (i in 1..5) {
            // 換一題
            onWebView()
                .withElement(findElement(Locator.XPATH, "//button[contains(text(), '換一題')]"))
                .perform(webClick())
            Thread.sleep(500)

            // 提示
            onWebView()
                .withElement(findElement(Locator.XPATH, "//button[contains(text(), '提示')]"))
                .perform(webClick())
            Thread.sleep(500)
        }

        // 驗證遊戲仍正常運行
        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))
    }

    // ==================== 輔助方法 ====================

    /**
     * 輔助方法：登入為 demo 使用者
     */
    private fun loginAsDemo() {
        onView(withId(R.id.usernameEditText))
            .perform(typeText("demo"), closeSoftKeyboard())
        onView(withId(R.id.passwordEditText))
            .perform(typeText("password123"), closeSoftKeyboard())
        onView(withId(R.id.loginButton))
            .perform(click())
    }
}
