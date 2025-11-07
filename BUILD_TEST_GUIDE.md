# Android WebView + Login 測試專案 - 建構與測試指南

## 📋 專案概況

**專案位置**: `/Users/yinghaowang/Work/android-webview-login`
**建立日期**: 2025-11-07
**狀態**: ✅ 專案結構完成，代碼就緒

## 🎯 專案內容

### 功能模組

1. **Login 登入系統**
   - 檔案: `MainActivity.kt`, `MainViewModel.kt`, `LoginService.kt`
   - 功能: Mock 登入驗證、MVVM 架構
   - 測試帳號:
     - demo / password123
     - test / test123
     - admin / admin123

2. **WebView 瀏覽器**
   - 檔案: `WebViewActivity.kt`
   - 功能: 網頁載入、URL 輸入、使用者資訊顯示

3. **測試套件**
   - `MainActivityTest.kt` - 6 個登入測試案例
   - `WebViewActivityTest.kt` - 9 個 WebView 測試案例

## 🚀 建構與測試步驟

### 方法 1: 使用 Android Studio（推薦）

#### 1. 開啟專案
```bash
# 在 Finder 中導航到
/Users/yinghaowang/Work/android-webview-login

# 或使用命令列
open -a "Android Studio" /Users/yinghaowang/Work/android-webview-login
```

#### 2. 等待 Gradle 同步
- Android Studio 會自動開始 Gradle 同步
- 等待進度條完成（首次可能需要 5-10 分鐘）

#### 3. 建構專案
```
Menu → Build → Make Project
或按 ⌘ + F9
```

#### 4. 執行應用程式
```
點擊工具列的 Run 按鈕（綠色三角形）
或按 ⌃ + R
```

#### 5. 執行測試

**執行所有測試**:
```
右鍵點擊 app/src/androidTest → Run 'Tests in androidTest'
```

**執行特定測試**:
```
# Login 測試
右鍵點擊 MainActivityTest.kt → Run 'MainActivityTest'

# WebView 測試
右鍵點擊 WebViewActivityTest.kt → Run 'WebViewActivityTest'
```

### 方法 2: 使用命令列

#### 前置條件
```bash
# 設置環境變數
export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
export ANDROID_HOME="$HOME/Library/Android/sdk"
export PATH="$ANDROID_HOME/platform-tools:$ANDROID_HOME/emulator:$PATH"
```

#### 建構應用程式
```bash
cd /Users/yinghaowang/Work/android-webview-login

# 清理並建構
./gradlew clean assembleDebug

# 查看建構結果
ls -lh app/build/outputs/apk/debug/
```

#### 安裝到裝置
```bash
# 確保裝置已連接或模擬器已啟動
adb devices

# 安裝 APK
adb install app/build/outputs/apk/debug/app-debug.apk
```

#### 執行測試（需要連接裝置或模擬器）
```bash
# 執行所有 UI 測試
./gradlew connectedAndroidTest

# 執行特定測試類別
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.example.webviewlogin.MainActivityTest

# 查看測試報告
open app/build/reports/androidTests/connected/index.html
```

## 🔧 疑難排解

### 問題 1: Gradle 同步失敗

**解決方案**:
```bash
cd /Users/yinghaowang/Work/android-webview-login
./gradlew clean
rm -rf .gradle
# 然後在 Android Studio 重新同步
```

### 問題 2: Build Tools 版本不符

**解決方案**:
在 Android Studio 中：
1. Tools → SDK Manager
2. SDK Tools 標籤
3. 勾選並安裝 "Android SDK Build-Tools 33.0.1"

### 問題 3: 測試裝置動畫影響測試

**解決方案**:
在測試裝置上關閉動畫：
1. Settings → Developer Options
2. 將以下三項設為 "Animation off":
   - Window animation scale
   - Transition animation scale
   - Animator duration scale

### 問題 4: JDK 版本問題

**解決方案**:
在 Android Studio 中：
1. File → Project Structure
2. SDK Location → JDK location
3. 選擇 "Embedded JDK" 或 JDK 17/21

## 📊 測試案例清單

### MainActivityTest (Login 測試)

| 測試名稱 | 描述 | 預期結果 |
|---------|------|---------|
| testLoginScreenDisplayed | 驗證登入畫面元素 | 所有 UI 元素顯示正確 |
| testEmptyCredentials | 測試空白帳號密碼 | 顯示錯誤訊息 |
| testInvalidCredentials | 測試錯誤帳號密碼 | 登入失敗 |
| testSuccessfulLogin | 測試成功登入 | 跳轉到 WebView 畫面 |
| testLoginButtonDisabledDuringLogin | 測試載入狀態 | 按鈕禁用，進度條顯示 |
| testDifferentValidUsers | 測試多組使用者 | 所有有效帳號都能登入 |

### WebViewActivityTest (WebView 測試)

| 測試名稱 | 描述 | 預期結果 |
|---------|------|---------|
| testWebViewActivityDisplayed | 驗證 WebView 畫面元素 | 所有 UI 元素顯示正確 |
| testLoadDifferentUrl | 測試載入不同網址 | URL 正確載入和顯示 |
| testLoadUrlWithHttps | 測試 HTTPS 網址 | 網頁正確載入 |
| testLogoutButton | 測試登出功能 | Activity 正確結束 |
| testWebViewLoadingProgress | 測試載入進度 | 進度條正常顯示 |
| testEmptyUrlHandling | 測試空白 URL | 正確處理錯誤 |
| testMultipleUrlLoads | 測試多次載入 | 所有網址都能載入 |
| testWebViewWithGoogleSearch | 測試載入 Google | 成功載入外部網站 |
| testUserInfoPersistence | 測試使用者資訊持久性 | 資訊保持不變 |

## 📁 重要檔案位置

```
專案根目錄: /Users/yinghaowang/Work/android-webview-login/

主要代碼:
├── app/src/main/java/com/example/webviewlogin/
│   ├── MainActivity.kt                 # 登入畫面
│   ├── MainViewModel.kt                # 登入邏輯
│   ├── WebViewActivity.kt              # WebView 畫面
│   ├── model/
│   │   ├── User.kt                     # 使用者資料
│   │   └── LoginResult.kt              # 登入結果
│   └── service/
│       └── LoginService.kt             # Mock 登入服務

測試代碼:
├── app/src/androidTest/java/com/example/webviewlogin/
│   ├── MainActivityTest.kt             # Login 測試
│   └── WebViewActivityTest.kt          # WebView 測試

資源檔案:
├── app/src/main/res/
│   ├── layout/
│   │   ├── activity_main.xml           # 登入畫面佈局
│   │   └── activity_webview.xml        # WebView 畫面佈局
│   └── values/
│       ├── strings.xml                 # 字串資源
│       ├── colors.xml                  # 顏色資源
│       └── themes.xml                  # 主題

建構輸出:
├── app/build/outputs/apk/debug/
│   └── app-debug.apk                   # Debug APK
└── app/build/reports/
    └── androidTests/connected/         # 測試報告
```

## 🎬 快速測試步驟

### 手動測試流程

1. **啟動應用程式**
   - 在模擬器或實體裝置上安裝並啟動 App

2. **測試登入**
   - 輸入測試帳號: `demo` / `password123`
   - 點擊「登入」按鈕
   - 觀察載入動畫和結果

3. **測試 WebView**
   - 登入成功後自動跳轉
   - 確認使用者名稱顯示正確
   - 在 URL 欄輸入 `google.com`
   - 點擊「載入」按鈕
   - 確認網頁載入正常

4. **測試登出**
   - 點擊「登出」按鈕
   - 確認返回登入畫面

### 自動化測試流程

1. **在 Android Studio 中**
   ```
   右鍵點擊 app/src/androidTest
   → Run 'Tests in androidTest'
   ```

2. **觀察測試執行**
   - 測試會自動執行所有案例
   - 觀察 Run 視窗的測試進度

3. **查看測試報告**
   - 測試完成後自動顯示結果
   - 綠色 ✓ 表示通過
   - 紅色 ✗ 表示失敗

## 💡 開發建議

### 添加新測試

1. 在 `app/src/androidTest/java/com/example/webviewlogin/` 建立新測試檔
2. 繼承測試類別並添加 `@Test` 註解
3. 使用 Espresso API 編寫測試邏輯

```kotlin
@Test
fun testNewFeature() {
    onView(withId(R.id.myView))
        .perform(click())
        .check(matches(isDisplayed()))
}
```

### 修改測試帳號

編輯 `LoginService.kt`:
```kotlin
private val validUsers = mapOf(
    "newuser" to "newpassword"  // 添加新帳號
)
```

### 調整 UI

修改 layout XML 檔案:
- `app/src/main/res/layout/activity_main.xml`
- `app/src/main/res/layout/activity_webview.xml`

## 📞 技術支援

### 常見問題

**Q: 測試一直失敗怎麼辦？**
A: 確保關閉裝置動畫，並確認網路連線正常

**Q: 無法建構專案？**
A: 嘗試 File → Invalidate Caches / Restart

**Q: Gradle 下載太慢？**
A: 可以設置 Gradle mirror 加速下載

### 有用的連結

- [Android Testing Guide](https://developer.android.com/training/testing)
- [Espresso Documentation](https://developer.android.com/training/testing/espresso)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)

---

**專案建立者**: Claude Code
**最後更新**: 2025-11-07
**版本**: 1.0.0
