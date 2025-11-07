# 快速開始指南 - Android WebView + Login 測試專案

## 🎯 專案狀態

✅ **專案完成度**: 100%
✅ **代碼就緒**: 所有功能代碼已完成
✅ **測試就緒**: 15 個測試案例已完成
📍 **專案位置**: `/Users/yinghaowang/Work/android-webview-login`

---

## 🚀 推薦方式：使用 Android Studio

### 步驟 1: 開啟專案

```bash
# 方法 1: 使用命令列開啟
open -a "Android Studio" /Users/yinghaowang/Work/android-webview-login

# 方法 2: 在 Android Studio 中
# File → Open → 選擇 /Users/yinghaowang/Work/android-webview-login
```

### 步驟 2: 等待 Gradle 同步

- Android Studio 會自動開始 Gradle 同步
- 首次同步需要 **5-10 分鐘**（下載依賴套件）
- 觀察右下角的進度條
- 等待顯示 "Gradle sync finished"

### 步驟 3: 解決可能的問題

如果出現錯誤：

**問題：Missing SDK components**
```
解決：Android Studio 會自動提示安裝
點擊 "Install missing SDK package(s)"
```

**問題：JDK 版本不符**
```
解決：
1. File → Project Structure
2. SDK Location → JDK location
3. 選擇 "Embedded JDK" 或 JDK 17
```

**問題：Build Tools 版本**
```
解決：
1. Tools → SDK Manager
2. SDK Tools 標籤頁
3. 勾選 "Android SDK Build-Tools 33.0.1"
4. 點擊 "Apply"
```

### 步驟 4: 建構專案

```
方法 1: 使用選單
Build → Make Project (或按 ⌘ + F9)

方法 2: 使用工具列
點擊工具列的 Hammer 圖示（Build）

預計時間：首次建構約 2-3 分鐘
```

### 步驟 5: 執行應用程式

#### 5.1 準備裝置

**選項 A: 使用模擬器**
```
1. Tools → Device Manager
2. 點擊 "Create Virtual Device"
3. 選擇 Pixel 6 或其他裝置
4. 選擇 API 34 (Android 14) 系統映像
5. 點擊 "Finish"
6. 啟動模擬器
```

**選項 B: 使用實體裝置**
```
1. 在手機上啟用開發者選項和 USB 調試
2. 連接 USB 線
3. 允許 USB 調試授權
```

#### 5.2 執行應用

```
1. 確保裝置在裝置選擇器中顯示
2. 點擊綠色三角形 Run 按鈕（或按 ⌃ + R）
3. 等待應用安裝和啟動
```

### 步驟 6: 手動測試

**登入測試**:
1. 輸入測試帳號：`demo`
2. 輸入密碼：`password123`
3. 點擊「登入」
4. 觀察載入動畫
5. 確認跳轉到 WebView 畫面

**WebView 測試**:
1. 確認使用者名稱顯示為「使用者: demo」
2. 在 URL 欄輸入 `google.com`
3. 點擊「載入」按鈕
4. 確認網頁正常載入
5. 點擊「登出」返回登入畫面

---

## 🧪 執行自動化測試

### 方法 1: 在 Android Studio 中執行

#### 執行所有測試
```
1. 在專案結構中找到 app/src/androidTest
2. 右鍵點擊 androidTest 資料夾
3. 選擇 "Run 'Tests in androidTest'"
4. 等待測試完成（約 3-5 分鐘）
```

#### 執行特定測試
```
Login 測試:
1. 打開 MainActivityTest.kt
2. 右鍵點擊檔案
3. 選擇 "Run 'MainActivityTest'"

WebView 測試:
1. 打開 WebViewActivityTest.kt
2. 右鍵點擊檔案
3. 選擇 "Run 'WebViewActivityTest'"
```

#### 執行單一測試方法
```
1. 在測試檔案中找到測試方法（@Test）
2. 點擊方法左邊的綠色播放圖示
3. 選擇 "Run 'testMethodName()'"
```

### 方法 2: 使用命令列（需先確保 Android Studio 建構成功）

```bash
# 設置環境
export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
export ANDROID_HOME="$HOME/Library/Android/sdk"
cd /Users/yinghaowang/Work/android-webview-login

# 確保裝置已連接
adb devices

# 執行所有測試
./gradlew connectedAndroidTest

# 查看測試報告
open app/build/reports/androidTests/connected/index.html
```

---

## 📊 測試案例清單

### ✅ MainActivityTest (6 個測試)

| # | 測試方法 | 測試內容 | 預期結果 |
|---|---------|---------|---------|
| 1 | testLoginScreenDisplayed | 驗證 UI 元素 | 所有元素正確顯示 |
| 2 | testEmptyCredentials | 空白輸入 | 顯示錯誤訊息 |
| 3 | testInvalidCredentials | 錯誤帳密 | 登入失敗保持在登入頁 |
| 4 | testSuccessfulLogin | 正確登入 | 跳轉到 WebView 頁面 |
| 5 | testLoginButtonDisabledDuringLogin | 載入狀態 | 按鈕禁用顯示進度 |
| 6 | testDifferentValidUsers | 多使用者 | 3組帳號都能登入 |

### ✅ WebViewActivityTest (9 個測試)

| # | 測試方法 | 測試內容 | 預期結果 |
|---|---------|---------|---------|
| 1 | testWebViewActivityDisplayed | 驗證 UI 元素 | 所有元素正確顯示 |
| 2 | testLoadDifferentUrl | URL 載入 | 網址正確載入 |
| 3 | testLoadUrlWithHttps | HTTPS 載入 | HTTPS 網址正常 |
| 4 | testLogoutButton | 登出功能 | 正確返回登入頁 |
| 5 | testWebViewLoadingProgress | 載入進度 | 進度條正常顯示 |
| 6 | testEmptyUrlHandling | 空白 URL | 正確處理錯誤 |
| 7 | testMultipleUrlLoads | 多次載入 | 連續載入無問題 |
| 8 | testWebViewWithGoogleSearch | Google 測試 | 外部網站正常 |
| 9 | testUserInfoPersistence | 資訊持久 | 使用者資訊不變 |

---

## 🔍 測試執行注意事項

### 測試前準備

1. **關閉裝置動畫**（重要！）
```
裝置設定 → 開發者選項 → 將以下三項設為 "off":
- Window animation scale
- Transition animation scale
- Animator duration scale
```

2. **確保網路連線**
```
WebView 測試需要載入外部網頁
確保測試裝置有網路連線
```

3. **保持螢幕亮起**
```
測試期間不要讓裝置進入休眠
建議插上電源並設定螢幕常亮
```

### 查看測試結果

**在 Android Studio 中**:
- 測試完成後，Run 視窗會顯示結果
- 綠色 ✓ 表示通過
- 紅色 ✗ 表示失敗
- 點擊失敗的測試可查看詳細錯誤

**查看 HTML 報告**:
```bash
# 測試完成後自動生成
open app/build/reports/androidTests/connected/index.html
```

---

## 🎓 測試帳號資訊

| 使用者名稱 | 密碼 | 用途 |
|-----------|------|------|
| demo | password123 | 主要測試帳號 |
| test | test123 | 備用測試帳號 |
| admin | admin123 | 管理員測試帳號 |

---

## 🐛 常見問題

### Q1: Gradle 同步失敗怎麼辦？

**解決方案**:
```
1. File → Invalidate Caches / Restart
2. 選擇 "Invalidate and Restart"
3. 等待 Android Studio 重新啟動
4. 讓 Gradle 重新同步
```

### Q2: 測試一直失敗？

**檢查項目**:
- ✅ 裝置動畫是否已關閉
- ✅ 網路連線是否正常
- ✅ 裝置是否保持亮屏
- ✅ 是否有其他應用佔用螢幕

### Q3: 無法連接裝置？

**實體裝置**:
```
1. 確認 USB 線連接正常
2. 確認已啟用 USB 調試
3. 重新授權 USB 調試
4. 嘗試重新插拔 USB
```

**模擬器**:
```
1. Tools → Device Manager
2. 確認模擬器狀態為 "Running"
3. 嘗試重新啟動模擬器
4. 確認 HAXM/Hyper-V 已啟用
```

### Q4: 建構太慢？

**優化建議**:
```
1. 增加 Gradle 記憶體:
   編輯 gradle.properties:
   org.gradle.jvmargs=-Xmx4096m

2. 啟用 Gradle daemon:
   org.gradle.daemon=true

3. 啟用 parallel build:
   org.gradle.parallel=true
```

### Q5: 測試執行太慢？

**優化建議**:
- 使用較新的模擬器（API 30+）
- 使用 x86_64 系統映像（比 ARM 快）
- 增加模擬器的 RAM 和 CPU 核心數
- 使用實體裝置測試（通常更快）

---

## 📖 延伸學習

### 修改測試

**添加新測試案例**:
```kotlin
@Test
fun testNewFeature() {
    // 1. 操作 UI
    onView(withId(R.id.myButton))
        .perform(click())

    // 2. 驗證結果
    onView(withId(R.id.myText))
        .check(matches(withText("Expected")))
}
```

**添加新測試帳號**:
```kotlin
// 編輯 LoginService.kt
private val validUsers = mapOf(
    "demo" to "password123",
    "test" to "test123",
    "admin" to "admin123",
    "newuser" to "newpass"  // 添加新帳號
)
```

### 相關資源

- [Android Testing Guide](https://developer.android.com/training/testing)
- [Espresso Documentation](https://developer.android.com/training/testing/espresso)
- [JUnit 4 Documentation](https://junit.org/junit4/)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)

---

## ✅ 檢查清單

建構前：
- [ ] Android Studio 已安裝
- [ ] 專案已在 Android Studio 中開啟
- [ ] Gradle 同步已完成
- [ ] JDK 設定正確

執行前：
- [ ] 裝置/模擬器已連接
- [ ] 裝置動畫已關閉
- [ ] 網路連線正常
- [ ] 裝置保持亮屏

測試前：
- [ ] 應用已成功建構
- [ ] 應用可正常啟動
- [ ] 手動測試登入功能正常
- [ ] 準備執行自動化測試

---

## 📞 需要協助？

如果遇到問題：

1. **檢查建構指南**: 詳見 `BUILD_TEST_GUIDE.md`
2. **檢查專案說明**: 詳見 `README.md`
3. **查看錯誤日誌**: Android Studio → Build → Build Output
4. **清理專案**: Build → Clean Project → Rebuild Project

---

**專案建立**: 2025-11-07
**最後更新**: 2025-11-07
**版本**: 1.0.0
**狀態**: ✅ 就緒可用
