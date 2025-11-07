# Command-Line Testing Execution Guide

## ✅ Current Status

This project has been successfully tested in the command-line environment with **JDK 17**:

**Final Test Results**:
```
✅ 15/15 tests passed (100%)
Build Tool: JDK 17 + Gradle 8.4
Execution Time: 1m 12s
Emulator: Pixel 6 API 33
```

---

## 🎯 Resolved Issues

### Issue 1: JDK 21 Compatibility ✅ SOLVED

**Original Problem**: JDK 21 incompatible with Android Gradle Plugin in CLI
```
Error while executing process jlink with arguments
Failed to transform core-for-system-modules.jar
```

**Solution**: Install and use JDK 17
```bash
brew install openjdk@17
export JAVA_HOME="/opt/homebrew/opt/openjdk@17"
```

### Issue 2: API 36 Emulator Test Failures ✅ SOLVED

**Original Problem**: API 36 (Android 16 Beta) incompatible with Espresso
```
java.lang.NoSuchMethodException: android.hardware.input.InputManager.getInstance
All 15 tests failed (100% failure)
```

**Solution**: Use API 33 emulator
- Create Pixel 6 API 33 emulator via Android Studio Device Manager
- All tests now pass successfully

### Issue 3: WebView URL Verification Failures ✅ SOLVED

**Original Problem**: 2 tests failed (testLoadDifferentUrl, testMultipleUrlLoads)
- Cause: WebView performs URL redirects during loading
- Example: `example.com` → `https://www.example.com/`

**Solution**: Improved test logic
- Increased wait times (5s for single, 4s for multiple loads)
- Used `containsString()` matcher instead of exact match
- **Final Result**: 15/15 tests passed

---

## 🚀 Recommended Solutions

### Solution 1: Use Android Studio (Strongly Recommended) ⭐

This is the **simplest and most reliable** approach:

```bash
# Open project
open -a "Android Studio" /Users/yinghaowang/Work/android-webview-login

# Then in Android Studio:
# 1. Wait for Gradle sync
# 2. Build → Make Project
# 3. Right-click androidTest → Run Tests
```

**Advantages**:
- ✅ Automatically handles JDK compatibility
- ✅ Graphical interface easy to use
- ✅ Complete debugging tools
- ✅ Real-time test results display
- ✅ Automatic test report generation

---

### Solution 2: Install JDK 17 + Command Line

For command-line execution:

#### 2.1 Install JDK 17

```bash
# Install JDK 17
brew install openjdk@17

# Create symbolic link
sudo ln -sfn /opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk \
  /Library/Java/JavaVirtualMachines/openjdk-17.jdk
```

#### 2.2 Set Environment Variables

```bash
# Set environment variables
export JAVA_HOME="/opt/homebrew/opt/openjdk@17"
export ANDROID_HOME="$HOME/Library/Android/sdk"
export PATH="$ANDROID_HOME/platform-tools:$PATH"

# Verify
java -version
# Should display: openjdk version "17.x.x"
```

#### 2.3 Build and Test

```bash
cd /Users/yinghaowang/Work/android-webview-login

# Build
./gradlew clean assembleDebug

# Execute tests (requires device)
./gradlew connectedAndroidTest

# View report
open app/build/reports/androidTests/connected/index.html
```

---

### Solution 3: Use Automated Test Script

We provide an automated testing script:

```bash
cd /Users/yinghaowang/Work/android-webview-login

# Execute test script
./run_tests.sh
```

Script functions:
- ✅ Automatically detect Java environment
- ✅ Check Android SDK
- ✅ Verify device connection
- ✅ Build project (optional)
- ✅ Execute tests
- ✅ Generate reports

---

## 📱 Test Device Preparation

### Option A: Use Emulator

**IMPORTANT**: Must use **API 33** (Android 13)
- API 36 (Android 16 Beta) is **NOT supported** by Espresso 3.6.1
- Recommended: Pixel 6 API 33

```bash
# List available emulators
emulator -list-avds

# Launch emulator (replace with your emulator name)
emulator -avd Pixel_6_API_33 &

# Wait for emulator to start
adb wait-for-device

# Verify connection
adb devices
```

**Create New API 33 Emulator**:
```
1. Open Android Studio Device Manager
2. Click "Create Virtual Device"
3. Select Pixel 6
4. Select API 33 (Android 13) system image
5. Click "Finish"
```

### Option B: Use Physical Device

```bash
# 1. Enable Developer Options and USB Debugging on phone
# 2. Connect USB cable
# 3. Allow USB debugging authorization

# Verify connection
adb devices
# Should display: <device-id>    device
```

### Disable Device Animations (Important!)

```bash
# Disable animations via command
adb shell settings put global window_animation_scale 0
adb shell settings put global transition_animation_scale 0
adb shell settings put global animator_duration_scale 0

# Or manually in device settings:
# Settings → Developer Options → Set all three to "Animation off":
# • Window animation scale
# • Transition animation scale
# • Animator duration scale
```

---

## 🧪 Execute Test Commands

### Run All Tests

```bash
# Set environment (use JDK 17)
export JAVA_HOME="/opt/homebrew/opt/openjdk@17"
export ANDROID_HOME="$HOME/Library/Android/sdk"

cd /Users/yinghaowang/Work/android-webview-login

# Execute all UI tests
./gradlew connectedAndroidTest

# View results
open app/build/reports/androidTests/connected/index.html
```

### Run Specific Test Class

```bash
# Login tests
./gradlew connectedAndroidTest \
  -Pandroid.testInstrumentationRunnerArguments.class=com.example.webviewlogin.MainActivityTest

# WebView tests
./gradlew connectedAndroidTest \
  -Pandroid.testInstrumentationRunnerArguments.class=com.example.webviewlogin.WebViewActivityTest
```

### Run Specific Test Method

```bash
# Execute single test method
./gradlew connectedAndroidTest \
  -Pandroid.testInstrumentationRunnerArguments.class=com.example.webviewlogin.MainActivityTest#testSuccessfulLogin
```

---

## 📊 Test Results

### Latest Test Report (2025-11-07)

| Item | Result |
|------|--------|
| **Total Tests** | 15 |
| **Passed** | 15 ✅ (100%) |
| **Failed** | 0 |
| **Execution Time** | 1m 12s |
| **Emulator** | Pixel 6 API 33 |
| **Build Tools** | JDK 17 |

### Test Details

**MainActivityTest** (6/6 passed):
- ✅ testLoginScreenDisplayed
- ✅ testEmptyCredentials
- ✅ testInvalidCredentials
- ✅ testSuccessfulLogin
- ✅ testLoginButtonDisabledDuringLogin
- ✅ testDifferentValidUsers

**WebViewActivityTest** (9/9 passed):
- ✅ testWebViewActivityDisplayed
- ✅ testLoadDifferentUrl (fixed)
- ✅ testLoadUrlWithHttps
- ✅ testLogoutButton
- ✅ testWebViewLoadingProgress
- ✅ testEmptyUrlHandling
- ✅ testMultipleUrlLoads (fixed)
- ✅ testWebViewWithGoogleSearch
- ✅ testUserInfoPersistence

### View Test Report

```bash
# HTML report
open app/build/reports/androidTests/connected/index.html

# XML report
cat app/build/outputs/androidTest-results/connected/*.xml
```

### Test Logs

```bash
# View test logs in real-time
adb logcat | grep -E "(TestRunner|AndroidJUnitRunner)"

# View specific test logs
adb logcat | grep "MainActivityTest"
```

---

## 🐛 Troubleshooting

### Issue 1: JDK Version Error

```bash
# Check current JDK version
java -version

# If not version 17, set correct JAVA_HOME
export JAVA_HOME="/opt/homebrew/opt/openjdk@17"
```

### Issue 2: Device Not Found

```bash
# Restart adb
adb kill-server
adb start-server

# Check connection
adb devices
```

### Issue 3: Permission Error

```bash
# Grant gradlew execution permission
chmod +x gradlew

# Re-download gradle wrapper
./gradlew wrapper --gradle-version=8.4
```

### Issue 4: Test Failures

```bash
# Disable device animations
adb shell settings put global window_animation_scale 0
adb shell settings put global transition_animation_scale 0
adb shell settings put global animator_duration_scale 0

# Clear app data
adb shell pm clear com.example.webviewlogin

# Re-run tests
./gradlew connectedAndroidTest --rerun-tasks
```

### Issue 5: Gradle Build Error

```bash
# Clean and rebuild
./gradlew clean
rm -rf .gradle build
./gradlew assembleDebug
```

---

## 📝 Quick Test Script

Create a simple test script `quick_test.sh`:

```bash
#!/bin/bash

# Color definitions
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo "🚀 Android WebView Login Test Script"
echo "===================================="

# Check JAVA_HOME
if [ -z "$JAVA_HOME" ]; then
    export JAVA_HOME="/opt/homebrew/opt/openjdk@17"
fi

echo "✓ Java: $(java -version 2>&1 | head -n 1)"

# Check devices
DEVICES=$(adb devices | grep -v "List" | grep "device$" | wc -l)
if [ $DEVICES -eq 0 ]; then
    echo -e "${RED}✗ No device or emulator detected${NC}"
    echo "Please launch emulator or connect physical device"
    exit 1
fi

echo -e "${GREEN}✓ Connected devices: $DEVICES${NC}"

# Install APK (if exists)
APK="app/build/outputs/apk/debug/app-debug.apk"
if [ -f "$APK" ]; then
    echo "📦 Installing APK..."
    adb install -r "$APK"
fi

# Execute tests
echo "🧪 Executing tests..."
./gradlew connectedAndroidTest

# View results
if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓ Tests completed!${NC}"
    open app/build/reports/androidTests/connected/index.html
else
    echo -e "${RED}✗ Tests failed${NC}"
    exit 1
fi
```

Usage:
```bash
chmod +x quick_test.sh
./quick_test.sh
```

---

## 🎯 Recommended Workflow

### Development Phase: Use Android Studio
1. Open project
2. Modify code
3. Run → Run 'app'
4. Manual testing

### Testing Phase: Use Command Line
1. Build once in Android Studio
2. Execute tests via command line
3. View test reports

### CI/CD: Automation
```bash
# CI environment execution script
export JAVA_HOME="/path/to/jdk17"
export ANDROID_HOME="/path/to/android-sdk"

./gradlew clean
./gradlew assembleDebug
./gradlew connectedAndroidTest
```

---

## 📞 Need Help?

### Quick Command Reference

```bash
# List all Gradle tasks
./gradlew tasks

# Build Debug APK
./gradlew assembleDebug

# Install to device
adb install -r app/build/outputs/apk/debug/app-debug.apk

# Launch app
adb shell am start -n com.example.webviewlogin/.MainActivity

# Execute tests
./gradlew connectedAndroidTest

# View logs
adb logcat
```

### Environment Check Script

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

## 📚 Additional Documentation

- **[README.md](README.md)** - Complete project description
- **[QUICK_START.md](QUICK_START.md)** - Quick start guide
- **[BUILD_TEST_GUIDE.md](BUILD_TEST_GUIDE.md)** - Build and test guide
- **[TEST_RESULTS.md](TEST_RESULTS.md)** - Test execution results and fixes
- **[SUMMARY.md](SUMMARY.md)** - Project summary

---

**Last Updated**: 2025-11-07
**Recommended Approach**: Android Studio
**Alternative Approach**: JDK 17 + Command Line
**Status**: ✅ Fully Ready (100% tests passing)
