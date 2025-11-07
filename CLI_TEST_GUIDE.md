# 命令列測試執行指南

## ⚠️ 當前狀況

專案在命令列環境遇到 **JDK 21 相容性問題**：
```
Error while executing process jlink with arguments
Failed to transform core-for-system-modules.jar
```

這是因為 Android Studio 內建的 JDK 21 與 Android Gradle Plugin 在命令列環境的相容性問題。

---

## ✅ 解決方案

### 方案 1: 使用 Android Studio（強烈推薦）

這是最簡單且最可靠的方式：

```bash
# 開啟專案
open -a "Android Studio" /Users/yinghaowang/Work/android-webview-login

# 然後在 Android Studio 中：
# 1. 等待 Gradle 同步
# 2. Build → Make Project
# 3. 右鍵 androidTest → Run Tests
```

**優點**：
- ✅ 自動處理 JDK 相容性
- ✅ 圖形化界面易於使用
- ✅ 完整的除錯功能
- ✅ 即時測試結果顯示

---

### 方案 2: 安裝 JDK 17 for 命令列

#### 2.1 安裝 JDK 17

```bash
# 使用 Homebrew 安裝 JDK 17
brew install openjdk@17

# 建立符號連結
sudo ln -sfn /opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk \
  /Library/Java/JavaVirtualMachines/openjdk-17.jdk
```

#### 2.2 設置環境變數

```bash
# 設置 JAVA_HOME 到 JDK 17
export JAVA_HOME="/Library/Java/JavaVirtualMachines/openjdk-17.jdk/Contents/Home"
export ANDROID_HOME="$HOME/Library/Android/sdk"
export PATH="$ANDROID_HOME/platform-tools:$ANDROID_HOME/emulator:$PATH"

# 驗證 Java 版本
java -version
# 應該顯示: openjdk version "17.x.x"
```

#### 2.3 建構專案

```bash
cd /Users/yinghaowang/Work/android-webview-login

# 清理並建構
./gradlew clean assembleDebug

# 檢查 APK
ls -lh app/build/outputs/apk/debug/app-debug.apk
```

---

### 方案 3: 使用專案提供的測試腳本

我們提供了一個自動化測試腳本：

```bash
cd /Users/yinghaowang/Work/android-webview-login

# 賦予執行權限
chmod +x run_tests.sh

# 執行測試
./run_tests.sh
```

這個腳本會：
1. 檢查環境
2. 連接裝置/模擬器
3. 安裝 APK
4. 執行測試
5. 產生報告

---

## 🔧 手動測試流程（不需建構）

如果建構失敗，您仍然可以：

### 1. 使用預建構的 APK（如果有）

```bash
# 如果之前在 Android Studio 成功建構過
APK_PATH="app/build/outputs/apk/debug/app-debug.apk"

if [ -f "$APK_PATH" ]; then
    # 安裝到裝置
    adb install -r "$APK_PATH"

    # 手動測試
    adb shell am start -n com.example.webviewlogin/.MainActivity
fi
```

### 2. 使用 Android Studio 建構，命令列測試

```bash
# 在 Android Studio 中建構成功後：

# 執行測試
cd /Users/yinghaowang/Work/android-webview-login
./gradlew connectedAndroidTest

# 查看報告
open app/build/reports/androidTests/connected/index.html
```

---

## 📱 準備測試裝置

### 選項 A: 使用模擬器

```bash
# 列出可用的模擬器
emulator -list-avds

# 啟動模擬器（替換為您的模擬器名稱）
emulator -avd Pixel_6_API_33 &

# 等待模擬器啟動
adb wait-for-device

# 驗證連接
adb devices
```

### 選項 B: 使用實體裝置

```bash
# 1. 在手機上啟用開發者選項和 USB 調試
# 2. 連接 USB 線
# 3. 允許 USB 調試授權

# 驗證連接
adb devices
# 應該顯示: <device-id>    device
```

---

## 🧪 執行測試命令

### 執行所有測試

```bash
export JAVA_HOME="/Library/Java/JavaVirtualMachines/openjdk-17.jdk/Contents/Home"
export ANDROID_HOME="$HOME/Library/Android/sdk"

cd /Users/yinghaowang/Work/android-webview-login

# 執行所有 UI 測試
./gradlew connectedAndroidTest

# 查看結果
open app/build/reports/androidTests/connected/index.html
```

### 執行特定測試類別

```bash
# Login 測試
./gradlew connectedAndroidTest \
  -Pandroid.testInstrumentationRunnerArguments.class=com.example.webviewlogin.MainActivityTest

# WebView 測試
./gradlew connectedAndroidTest \
  -Pandroid.testInstrumentationRunnerArguments.class=com.example.webviewlogin.WebViewActivityTest
```

### 執行特定測試方法

```bash
# 執行單一測試方法
./gradlew connectedAndroidTest \
  -Pandroid.testInstrumentationRunnerArguments.class=com.example.webviewlogin.MainActivityTest#testSuccessfulLogin
```

---

## 📊 測試結果

### 查看測試報告

```bash
# HTML 報告
open app/build/reports/androidTests/connected/index.html

# XML 報告
cat app/build/outputs/androidTest-results/connected/*.xml
```

### 測試日誌

```bash
# 即時查看測試日誌
adb logcat | grep -E "(TestRunner|AndroidJUnitRunner)"

# 查看特定測試的日誌
adb logcat | grep "MainActivityTest"
```

---

## 🐛 疑難排解

### 問題 1: JDK 版本錯誤

```bash
# 檢查當前 JDK 版本
java -version

# 如果不是 17，設置正確的 JAVA_HOME
export JAVA_HOME="/Library/Java/JavaVirtualMachines/openjdk-17.jdk/Contents/Home"
```

### 問題 2: 找不到裝置

```bash
# 重啟 adb
adb kill-server
adb start-server

# 檢查連接
adb devices
```

### 問題 3: 權限錯誤

```bash
# 賦予 gradlew 執行權限
chmod +x gradlew

# 重新下載 gradle wrapper
./gradlew wrapper --gradle-version=8.4
```

### 問題 4: 測試失敗

```bash
# 關閉裝置動畫
adb shell settings put global window_animation_scale 0
adb shell settings put global transition_animation_scale 0
adb shell settings put global animator_duration_scale 0

# 清理應用資料
adb shell pm clear com.example.webviewlogin

# 重新執行測試
./gradlew connectedAndroidTest --rerun-tasks
```

---

## 📝 測試腳本範例

建立一個簡單的測試腳本 `quick_test.sh`:

```bash
#!/bin/bash

# 顏色定義
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo "🚀 Android WebView Login 測試腳本"
echo "================================="

# 檢查 JAVA_HOME
if [ -z "$JAVA_HOME" ]; then
    export JAVA_HOME="/Library/Java/JavaVirtualMachines/openjdk-17.jdk/Contents/Home"
fi

echo "✓ Java: $(java -version 2>&1 | head -n 1)"

# 檢查裝置
DEVICES=$(adb devices | grep -v "List" | grep "device$" | wc -l)
if [ $DEVICES -eq 0 ]; then
    echo -e "${RED}✗ 未偵測到裝置或模擬器${NC}"
    echo "請啟動模擬器或連接實體裝置"
    exit 1
fi

echo -e "${GREEN}✓ 已連接 $DEVICES 個裝置${NC}"

# 安裝 APK（如果存在）
APK="app/build/outputs/apk/debug/app-debug.apk"
if [ -f "$APK" ]; then
    echo "📦 安裝 APK..."
    adb install -r "$APK"
fi

# 執行測試
echo "🧪 執行測試..."
./gradlew connectedAndroidTest

# 查看結果
if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓ 測試完成！${NC}"
    open app/build/reports/androidTest/connected/index.html
else
    echo -e "${RED}✗ 測試失敗${NC}"
    exit 1
fi
```

使用方式：
```bash
chmod +x quick_test.sh
./quick_test.sh
```

---

## 🎯 推薦工作流程

### 開發階段：使用 Android Studio
1. 開啟專案
2. 修改代碼
3. Run → Run 'app'
4. 手動測試

### 測試階段：使用命令列
1. 在 Android Studio 建構一次
2. 使用命令列執行測試
3. 查看測試報告

### CI/CD：自動化
```bash
# CI 環境執行腳本
export JAVA_HOME="/path/to/jdk17"
export ANDROID_HOME="/path/to/android-sdk"

./gradlew clean
./gradlew assembleDebug
./gradlew connectedAndroidTest
```

---

## 📞 需要協助？

### 常見命令速查

```bash
# 列出所有 Gradle 任務
./gradlew tasks

# 建構 Debug APK
./gradlew assembleDebug

# 安裝到裝置
adb install -r app/build/outputs/apk/debug/app-debug.apk

# 啟動應用
adb shell am start -n com.example.webviewlogin/.MainActivity

# 執行測試
./gradlew connectedAndroidTest

# 查看日誌
adb logcat
```

### 環境檢查腳本

```bash
# check_env.sh
echo "Java: $JAVA_HOME"
java -version
echo "Android SDK: $ANDROID_HOME"
adb version
echo "Devices:"
adb devices
```

---

**最後更新**: 2025-11-07
**推薦方式**: Android Studio
**備用方式**: JDK 17 + 命令列
