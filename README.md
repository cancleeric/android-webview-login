# Android WebView + Login 測試專案

這是一個使用 Kotlin 開發的 Android 原生應用程式，展示了 Login 登入功能和 WebView 網頁瀏覽功能，並包含完整的 Espresso UI 自動化測試。

## 📋 專案特色

- ✅ **Kotlin 原生開發**：使用最新的 Kotlin 語法和 Android 最佳實踐
- ✅ **Material Design**：採用 Material Design 設計規範
- ✅ **MVVM 架構**：使用 ViewModel 和 LiveData 實現 MVVM 架構
- ✅ **Mock 登入服務**：內建模擬登入服務，無需實際後端
- ✅ **WebView 整合**：完整的 WebView 實作，支援網頁瀏覽
- ✅ **Espresso 測試**：完整的 UI 自動化測試覆蓋
- ✅ **ViewBinding**：使用 ViewBinding 提高代碼安全性

## 🏗️ 專案結構

```
android-webview-login/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/webviewlogin/
│   │   │   │   ├── model/              # 資料模型
│   │   │   │   │   ├── User.kt
│   │   │   │   │   └── LoginResult.kt
│   │   │   │   ├── service/            # 服務層
│   │   │   │   │   └── LoginService.kt
│   │   │   │   ├── MainActivity.kt     # 登入畫面
│   │   │   │   ├── MainViewModel.kt    # 登入 ViewModel
│   │   │   │   └── WebViewActivity.kt  # WebView 畫面
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   ├── activity_main.xml
│   │   │   │   │   └── activity_webview.xml
│   │   │   │   └── values/
│   │   │   └── AndroidManifest.xml
│   │   └── androidTest/
│   │       └── java/com/example/webviewlogin/
│   │           ├── MainActivityTest.kt      # Login 測試
│   │           └── WebViewActivityTest.kt   # WebView 測試
│   └── build.gradle
├── build.gradle
├── settings.gradle
└── README.md
```

## 🚀 快速開始

### 環境需求

- Android Studio Hedgehog (2023.1.1) 或更新版本
- JDK 17 或更新版本
- Android SDK API Level 34
- Gradle 8.1.4

### 安裝步驟

1. **Clone 或複製專案**
   ```bash
   cd /Users/yinghaowang/Work/android-webview-login
   ```

2. **使用 Android Studio 開啟專案**
   - 開啟 Android Studio
   - 選擇 "Open an Existing Project"
   - 選擇 `android-webview-login` 資料夾

3. **等待 Gradle 同步完成**
   - Android Studio 會自動下載相依套件

4. **執行應用程式**
   - 連接實體裝置或啟動模擬器
   - 點擊 "Run" 按鈕（綠色三角形）

## 🔐 測試帳號

應用程式內建了三組測試帳號：

| 使用者名稱 | 密碼 |
|-----------|------|
| demo | password123 |
| test | test123 |
| admin | admin123 |

## 🧪 執行測試

### 使用 Android Studio

1. 在專案導航中找到測試檔案：
   - `app/src/androidTest/java/com/example/webviewlogin/MainActivityTest.kt`
   - `app/src/androidTest/java/com/example/webviewlogin/WebViewActivityTest.kt`

2. 右鍵點擊測試檔案或測試類別

3. 選擇 "Run 'MainActivityTest'" 或 "Run 'WebViewActivityTest'"

### 使用命令列

```bash
# 執行所有 UI 測試
./gradlew connectedAndroidTest

# 執行特定測試類別
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.example.webviewlogin.MainActivityTest
```

## 📱 功能說明

### 1. 登入功能 (MainActivity)

- **功能**：
  - 使用者名稱和密碼輸入
  - 表單驗證
  - Mock 登入服務驗證
  - 載入狀態顯示
  - 登入成功後跳轉到 WebView

- **測試覆蓋**：
  - ✅ 畫面元素顯示測試
  - ✅ 空白帳號密碼測試
  - ✅ 錯誤帳號密碼測試
  - ✅ 正確帳號密碼測試
  - ✅ 登入期間按鈕禁用測試
  - ✅ 多組使用者測試

### 2. WebView 功能 (WebViewActivity)

- **功能**：
  - 顯示登入使用者資訊
  - URL 輸入和載入
  - 自動添加 HTTPS 協議
  - 網頁載入進度條
  - 頁面前進/後退
  - 登出功能

- **測試覆蓋**：
  - ✅ 畫面元素顯示測試
  - ✅ 載入不同 URL 測試
  - ✅ HTTPS URL 測試
  - ✅ 登出按鈕測試
  - ✅ 空白 URL 處理測試
  - ✅ 多次載入測試
  - ✅ 使用者資訊持久性測試

## 🛠️ 技術架構

### 使用的技術和函式庫

- **語言**：Kotlin 1.9.20
- **UI 框架**：AndroidX, Material Design Components
- **架構元件**：
  - ViewModel
  - LiveData
  - ViewBinding
- **WebView**：AndroidX WebKit
- **協程**：Kotlin Coroutines
- **測試框架**：
  - Espresso (UI 測試)
  - JUnit 4
  - Mockito

### 架構模式

本專案採用 **MVVM (Model-View-ViewModel)** 架構：

```
View (Activity) ←→ ViewModel ←→ Model (Service/Repository)
```

- **View**：MainActivity, WebViewActivity
- **ViewModel**：MainViewModel
- **Model**：LoginService, User, LoginResult

## 📝 測試案例說明

### MainActivityTest (登入測試)

1. **testLoginScreenDisplayed**：驗證登入畫面元素正確顯示
2. **testEmptyCredentials**：測試空白帳號密碼的處理
3. **testInvalidCredentials**：測試錯誤帳號密碼的處理
4. **testSuccessfulLogin**：測試成功登入並跳轉
5. **testLoginButtonDisabledDuringLogin**：測試登入期間 UI 狀態
6. **testDifferentValidUsers**：測試多組有效使用者登入

### WebViewActivityTest (WebView 測試)

1. **testWebViewActivityDisplayed**：驗證 WebView 畫面元素
2. **testLoadDifferentUrl**：測試載入不同網址
3. **testLoadUrlWithHttps**：測試 HTTPS 網址載入
4. **testLogoutButton**：測試登出功能
5. **testWebViewLoadingProgress**：測試載入進度
6. **testEmptyUrlHandling**：測試空白 URL 處理
7. **testMultipleUrlLoads**：測試連續載入多個網址
8. **testWebViewWithGoogleSearch**：測試載入 Google 網頁
9. **testUserInfoPersistence**：測試使用者資訊持久性

## 🔍 程式碼亮點

### 1. LoginService - Mock 登入服務

```kotlin
class LoginService {
    suspend fun login(username: String, password: String): LoginResult {
        delay(1000) // 模擬網路延遲
        // 驗證邏輯...
    }
}
```

### 2. MainViewModel - MVVM 架構

```kotlin
class MainViewModel : ViewModel() {
    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> = _loginState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            // 非同步登入邏輯
        }
    }
}
```

### 3. Espresso 測試範例

```kotlin
@Test
fun testSuccessfulLogin() {
    onView(withId(R.id.usernameEditText))
        .perform(typeText("demo"), closeSoftKeyboard())
    onView(withId(R.id.passwordEditText))
        .perform(typeText("password123"), closeSoftKeyboard())
    onView(withId(R.id.loginButton))
        .perform(click())

    // 驗證跳轉成功
    onView(withId(R.id.webView))
        .check(matches(isDisplayed()))
}
```

## 🐛 疑難排解

### 問題：Gradle 同步失敗

**解決方案**：
```bash
./gradlew clean
./gradlew build --refresh-dependencies
```

### 問題：測試執行失敗

**解決方案**：
1. 確保模擬器或實體裝置已連接
2. 關閉裝置的動畫效果（開發者選項中）
3. 確保網路連線正常（部分 WebView 測試需要網路）

### 問題：ViewBinding 錯誤

**解決方案**：
1. Clean Project：Build → Clean Project
2. Rebuild Project：Build → Rebuild Project

## 📄 授權

本專案為教學示範專案，可自由使用和修改。

## 📧 聯絡資訊

如有問題或建議，歡迎提出 Issue 或 Pull Request。

---

**建立日期**：2025-11-07
**版本**：1.0.0
**開發工具**：Android Studio Hedgehog | 2023.1.1
