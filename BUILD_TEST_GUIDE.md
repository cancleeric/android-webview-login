# Android WebView + Login Test Project - Build and Test Guide

## 📋 Project Overview

**Project Location**: `/Users/yinghaowang/Work/android-webview-login`
**Created**: 2025-11-07
**Status**: ✅ Project structure complete, code ready

## 🎯 Project Contents

### Function Modules

1. **Login System**
   - Files: `MainActivity.kt`, `MainViewModel.kt`, `LoginService.kt`
   - Features: Mock login verification, MVVM architecture
   - Test Accounts:
     - demo / password123
     - test / test123
     - admin / admin123

2. **WebView Browser**
   - File: `WebViewActivity.kt`
   - Features: Web page loading, URL input, user info display

3. **Test Suite**
   - `MainActivityTest.kt` - 6 login test cases
   - `WebViewActivityTest.kt` - 9 WebView test cases

## 🚀 Build and Test Steps

### Method 1: Using Android Studio (Recommended)

#### 1. Open Project
```bash
# Navigate in Finder to
/Users/yinghaowang/Work/android-webview-login

# Or use command line
open -a "Android Studio" /Users/yinghaowang/Work/android-webview-login
```

#### 2. Wait for Gradle Sync
- Android Studio will automatically start Gradle sync
- Wait for progress bar to complete (first time may take 5-10 minutes)

#### 3. Build Project
```
Menu → Build → Make Project
Or press ⌘ + F9
```

#### 4. Run Application
```
Click Run button in toolbar (green triangle)
Or press ⌃ + R
```

#### 5. Run Tests

**Run All Tests**:
```
Right-click app/src/androidTest → Run 'Tests in androidTest'
```

**Run Specific Tests**:
```
# Login tests
Right-click MainActivityTest.kt → Run 'MainActivityTest'

# WebView tests
Right-click WebViewActivityTest.kt → Run 'WebViewActivityTest'
```

### Method 2: Using Command Line

#### Prerequisites
```bash
# Set environment variables
export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
export ANDROID_HOME="$HOME/Library/Android/sdk"
export PATH="$ANDROID_HOME/platform-tools:$ANDROID_HOME/emulator:$PATH"
```

#### Build Application
```bash
cd /Users/yinghaowang/Work/android-webview-login

# Clean and build
./gradlew clean assembleDebug

# View build results
ls -lh app/build/outputs/apk/debug/
```

#### Install to Device
```bash
# Ensure device is connected or emulator is running
adb devices

# Install APK
adb install app/build/outputs/apk/debug/app-debug.apk
```

#### Run Tests (requires connected device or emulator)
```bash
# Run all UI tests
./gradlew connectedAndroidTest

# Run specific test class
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.example.webviewlogin.MainActivityTest

# View test report
open app/build/reports/androidTests/connected/index.html
```

## 🔧 Troubleshooting

### Issue 1: Gradle Sync Failed

**Solution**:
```bash
cd /Users/yinghaowang/Work/android-webview-login
./gradlew clean
rm -rf .gradle
# Then resync in Android Studio
```

### Issue 2: Build Tools Version Mismatch

**Solution**:
In Android Studio:
1. Tools → SDK Manager
2. SDK Tools tab
3. Check and install "Android SDK Build-Tools 33.0.1"

### Issue 3: Device Animation Affecting Tests

**Solution**:
On test device, disable animations:
1. Settings → Developer Options
2. Set following three to "Animation off":
   - Window animation scale
   - Transition animation scale
   - Animator duration scale

### Issue 4: JDK Version Issue

**Solution**:
In Android Studio:
1. File → Project Structure
2. SDK Location → JDK location
3. Select "Embedded JDK" or JDK 17/21

## 📊 Test Case List

### MainActivityTest (Login Tests)

| Test Name | Description | Expected Result |
|---------|------|---------|
| testLoginScreenDisplayed | Verify login screen elements | All UI elements display correctly |
| testEmptyCredentials | Test empty username password | Show error message |
| testInvalidCredentials | Test wrong username password | Login fails |
| testSuccessfulLogin | Test successful login | Jump to WebView screen |
| testLoginButtonDisabledDuringLogin | Test loading state | Button disabled, progress shows |
| testDifferentValidUsers | Test multiple users | All valid accounts can login |

### WebViewActivityTest (WebView Tests)

| Test Name | Description | Expected Result |
|---------|------|---------|
| testWebViewActivityDisplayed | Verify WebView screen elements | All UI elements display correctly |
| testLoadDifferentUrl | Test loading different URLs | URL correctly loaded and displayed |
| testLoadUrlWithHttps | Test HTTPS URL | Web page correctly loaded |
| testLogoutButton | Test logout functionality | Activity correctly ends |
| testWebViewLoadingProgress | Test loading progress | Progress bar displays normally |
| testEmptyUrlHandling | Test empty URL | Error correctly handled |
| testMultipleUrlLoads | Test multiple loads | All URLs load successfully |
| testWebViewWithGoogleSearch | Test Google search loading | External website loads successfully |
| testUserInfoPersistence | Test user info persistence | Info remains unchanged |

## 📁 Important File Locations

```
Project root: /Users/yinghaowang/Work/android-webview-login/

Main code:
├── app/src/main/java/com/example/webviewlogin/
│   ├── MainActivity.kt                 # Login screen
│   ├── MainViewModel.kt                # Login logic
│   ├── WebViewActivity.kt              # WebView screen
│   ├── model/
│   │   ├── User.kt                     # User data
│   │   └── LoginResult.kt              # Login result
│   └── service/
│       └── LoginService.kt             # Mock login service

Test code:
├── app/src/androidTest/java/com/example/webviewlogin/
│   ├── MainActivityTest.kt             # Login tests
│   └── WebViewActivityTest.kt          # WebView tests

Resource files:
├── app/src/main/res/
│   ├── layout/
│   │   ├── activity_main.xml           # Login screen layout
│   │   └── activity_webview.xml        # WebView screen layout
│   └── values/
│       ├── strings.xml                 # String resources
│       ├── colors.xml                  # Color resources
│       └── themes.xml                  # Theme

Build output:
├── app/build/outputs/apk/debug/
│   └── app-debug.apk                   # Debug APK
└── app/build/reports/
    └── androidTests/connected/         # Test reports
```

## 🎬 Quick Test Steps

### Manual Test Flow

1. **Start Application**
   - Install and start App on emulator or physical device

2. **Test Login**
   - Enter test account: `demo` / `password123`
   - Click "Login" button
   - Observe loading animation and result

3. **Test WebView**
   - Confirm username displays correctly
   - Enter `google.com` in URL field
   - Click "Load" button
   - Confirm web page loads normally

4. **Test Logout**
   - Click "Logout" button
   - Confirm return to login screen

### Automated Test Flow

1. **In Android Studio**
   ```
   Right-click app/src/androidTest
   → Run 'Tests in androidTest'
   ```

2. **Observe Test Execution**
   - Tests run automatically through all cases
   - Watch test progress in Run window

3. **View Test Report**
   - Results display automatically after tests complete
   - Green ✓ means passed
   - Red ✗ means failed

## 💡 Development Suggestions

### Add New Test

1. Create new test file in `app/src/androidTest/java/com/example/webviewlogin/`
2. Extend test class and add `@Test` annotation
3. Write test logic using Espresso API

```kotlin
@Test
fun testNewFeature() {
    onView(withId(R.id.myView))
        .perform(click())
        .check(matches(isDisplayed()))
}
```

### Modify Test Accounts

Edit `LoginService.kt`:
```kotlin
private val validUsers = mapOf(
    "newuser" to "newpassword"  // Add new account
)
```

### Adjust UI

Modify layout XML files:
- `app/src/main/res/layout/activity_main.xml`
- `app/src/main/res/layout/activity_webview.xml`

## 📞 Technical Support

### Common Questions

**Q: Tests always fail, what should I do?**
A: Ensure device animations are disabled and network connection is normal

**Q: Unable to build project?**
A: Try File → Invalidate Caches / Restart

**Q: Gradle download too slow?**
A: Can set Gradle mirror to speed up downloads

### Useful Links

- [Android Testing Guide](https://developer.android.com/training/testing)
- [Espresso Documentation](https://developer.android.com/training/testing/espresso)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)

---

**Project Creator**: Claude Code
**Last Updated**: 2025-11-07
**Version**: 1.0.0
