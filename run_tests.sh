#!/bin/bash

# Android WebView Login 測試執行腳本
# 此腳本會自動檢查環境、建構專案並執行測試

# 顏色定義
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 函數：印出彩色訊息
print_success() {
    echo -e "${GREEN}✓ $1${NC}"
}

print_error() {
    echo -e "${RED}✗ $1${NC}"
}

print_warning() {
    echo -e "${YELLOW}⚠ $1${NC}"
}

print_info() {
    echo -e "${BLUE}ℹ $1${NC}"
}

# 標題
echo "╔═══════════════════════════════════════════╗"
echo "║  Android WebView Login 測試執行器         ║"
echo "╚═══════════════════════════════════════════╝"
echo ""

# 步驟 1: 檢查 Java
print_info "步驟 1/5: 檢查 Java 環境..."

# 嘗試使用 JDK 17
if [ -d "/Library/Java/JavaVirtualMachines/openjdk-17.jdk" ]; then
    export JAVA_HOME="/Library/Java/JavaVirtualMachines/openjdk-17.jdk/Contents/Home"
    print_success "找到 JDK 17"
elif [ -d "/opt/homebrew/opt/openjdk@17" ]; then
    export JAVA_HOME="/opt/homebrew/opt/openjdk@17"
    print_success "找到 Homebrew JDK 17"
elif [ -d "/Applications/Android Studio.app/Contents/jbr/Contents/Home" ]; then
    export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
    print_warning "使用 Android Studio 內建 JDK"
else
    print_error "找不到 Java！"
    print_info "請安裝 JDK 17: brew install openjdk@17"
    exit 1
fi

JAVA_VERSION=$("$JAVA_HOME/bin/java" -version 2>&1 | head -n 1)
print_info "Java 版本: $JAVA_VERSION"

# 步驟 2: 檢查 Android SDK
print_info "步驟 2/5: 檢查 Android SDK..."

if [ -z "$ANDROID_HOME" ]; then
    export ANDROID_HOME="$HOME/Library/Android/sdk"
fi

if [ ! -d "$ANDROID_HOME" ]; then
    print_error "找不到 Android SDK: $ANDROID_HOME"
    exit 1
fi

print_success "Android SDK: $ANDROID_HOME"

# 設置 PATH
export PATH="$ANDROID_HOME/platform-tools:$ANDROID_HOME/emulator:$PATH"

# 步驟 3: 檢查連接的裝置
print_info "步驟 3/5: 檢查 Android 裝置..."

# 確保 adb server 正在運行
adb start-server > /dev/null 2>&1

DEVICES=$(adb devices | grep -v "List" | grep "device$" | wc -l | tr -d ' ')

if [ "$DEVICES" -eq 0 ]; then
    print_error "未偵測到裝置或模擬器！"
    echo ""
    print_info "請執行以下其中一項："
    echo "  1. 啟動 Android 模擬器"
    echo "  2. 連接實體 Android 裝置並啟用 USB 調試"
    echo ""
    print_info "查看可用模擬器: emulator -list-avds"
    print_info "啟動模擬器: emulator -avd <模擬器名稱> &"
    exit 1
fi

print_success "已連接 $DEVICES 個裝置"
adb devices | grep "device$" | while read line; do
    echo "  • $line"
done

# 步驟 4: 建構專案
print_info "步驟 4/5: 建構專案..."

cd "$(dirname "$0")"

# 檢查是否已經有 APK
APK_PATH="app/build/outputs/apk/debug/app-debug.apk"

if [ -f "$APK_PATH" ]; then
    print_info "發現已存在的 APK"
    read -p "是否要重新建構？ (y/N): " -n 1 -r
    echo
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        BUILD=true
    else
        BUILD=false
    fi
else
    BUILD=true
fi

if [ "$BUILD" = true ]; then
    print_info "正在建構 Debug APK..."
    ./gradlew clean assembleDebug

    if [ $? -ne 0 ]; then
        print_error "建構失敗！"
        echo ""
        print_warning "建議使用 Android Studio 建構："
        echo "  open -a \"Android Studio\" ."
        echo "  然後選擇 Build → Make Project"
        exit 1
    fi

    print_success "建構完成！"
else
    print_info "跳過建構，使用現有 APK"
fi

# 步驟 5: 執行測試
print_info "步驟 5/5: 執行測試..."

# 關閉裝置動畫（提高測試穩定性）
print_info "關閉裝置動畫..."
adb shell settings put global window_animation_scale 0 > /dev/null 2>&1
adb shell settings put global transition_animation_scale 0 > /dev/null 2>&1
adb shell settings put global animator_duration_scale 0 > /dev/null 2>&1

# 安裝 APK
if [ -f "$APK_PATH" ]; then
    print_info "安裝 APK 到裝置..."
    adb install -r "$APK_PATH" > /dev/null 2>&1
    if [ $? -eq 0 ]; then
        print_success "APK 安裝成功"
    else
        print_warning "APK 安裝可能失敗，但繼續執行測試"
    fi
fi

# 執行測試
echo ""
print_info "執行 Espresso UI 測試..."
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"

./gradlew connectedAndroidTest

TEST_RESULT=$?

echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo ""

# 恢復動畫
adb shell settings put global window_animation_scale 1 > /dev/null 2>&1
adb shell settings put global transition_animation_scale 1 > /dev/null 2>&1
adb shell settings put global animator_duration_scale 1 > /dev/null 2>&1

# 結果
if [ $TEST_RESULT -eq 0 ]; then
    print_success "所有測試通過！🎉"
    echo ""

    # 查看測試報告
    REPORT_PATH="app/build/reports/androidTests/connected/index.html"
    if [ -f "$REPORT_PATH" ]; then
        print_info "測試報告: $REPORT_PATH"
        read -p "是否要開啟測試報告？ (Y/n): " -n 1 -r
        echo
        if [[ ! $REPLY =~ ^[Nn]$ ]]; then
            open "$REPORT_PATH"
        fi
    fi
else
    print_error "測試失敗 ❌"
    echo ""
    print_info "查看錯誤日誌："
    echo "  • 測試報告: app/build/reports/androidTests/connected/"
    echo "  • Logcat: adb logcat | grep TestRunner"
    exit 1
fi

echo ""
print_success "測試執行完成！"
