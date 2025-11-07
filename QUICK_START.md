# Quick Start Guide - Android WebView + Login Test Project

## 🎯 Project Status

✅ **Project Completion**: 100%
✅ **Code Ready**: All feature code complete
✅ **Tests Ready**: 15 test cases complete
📍 **Project Location**: `/Users/yinghaowang/Work/android-webview-login`

---

## 🚀 Recommended Method: Using Android Studio

### Step 1: Open Project

```bash
# Method 1: Open from command line
open -a "Android Studio" /Users/yinghaowang/Work/android-webview-login

# Method 2: In Android Studio
# File → Open → Select /Users/yinghaowang/Work/android-webview-login
```

### Step 2: Wait for Gradle Sync

- Android Studio will automatically start Gradle sync
- First sync takes **5-10 minutes** (downloading dependencies)
- Watch progress bar in bottom right
- Wait for "Gradle sync finished" message

### Step 3: Resolve Possible Issues

If errors appear:

**Issue: Missing SDK components**
```
Solution: Android Studio will auto-prompt
Click "Install missing SDK package(s)"
```

**Issue: JDK version mismatch**
```
Solution:
1. File → Project Structure
2. SDK Location → JDK location
3. Select "Embedded JDK" or JDK 17
```

**Issue: Build Tools version**
```
Solution:
1. Tools → SDK Manager
2. SDK Tools tab
3. Check "Android SDK Build-Tools 33.0.1"
4. Click "Apply"
```

### Step 4: Build Project

```
Method 1: Use menu
Build → Make Project (or press ⌘ + F9)

Method 2: Use toolbar
Click Hammer icon (Build)

Expected time: First build ~2-3 minutes
```

### Step 5: Run Application

#### 5.1 Prepare Device

**Option A: Use Emulator**
```
1. Tools → Device Manager
2. Click "Create Virtual Device"
3. Select Pixel 6 or other device
4. Select API 34 (Android 14) system image
5. Click "Finish"
6. Start emulator
```

**Option B: Use Physical Device**
```
1. Enable Developer Options and USB Debug on phone
2. Connect USB cable
3. Allow USB Debug authorization
```

#### 5.2 Run App

```
1. Ensure device appears in device selector
2. Click green triangle Run button (or press ⌃ + R)
3. Wait for app to install and start
```

### Step 6: Manual Testing

**Test Login**:
1. Enter test account: `demo`
2. Enter password: `password123`
3. Click "Login"
4. Watch loading animation
5. Confirm jump to WebView screen

**Test WebView**:
1. Confirm username displays as "User: demo"
2. Enter `google.com` in URL field
3. Click "Load" button
4. Confirm web page loads
5. Click "Logout" to return to login screen

---

## 🧪 Run Automated Tests

### Method 1: Run in Android Studio

#### Run All Tests
```
1. Find app/src/androidTest in project structure
2. Right-click androidTest folder
3. Select "Run 'Tests in androidTest'"
4. Wait for tests to complete (~3-5 minutes)
```

#### Run Specific Tests
```
Login tests:
1. Open MainActivityTest.kt
2. Right-click file
3. Select "Run 'MainActivityTest'"

WebView tests:
1. Open WebViewActivityTest.kt
2. Right-click file
3. Select "Run 'WebViewActivityTest'"
```

#### Run Single Test Method
```
1. Find test method (has @Test annotation)
2. Click green play icon beside method name
3. Select "Run 'testMethodName()'"
```

### Method 2: Use Command Line (After Android Studio builds successfully)

```bash
# Set environment
export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
export ANDROID_HOME="$HOME/Library/Android/sdk"
cd /Users/yinghaowang/Work/android-webview-login

# Ensure device connected
adb devices

# Run all tests
./gradlew connectedAndroidTest

# View test report
open app/build/reports/androidTests/connected/index.html
```

---

## 📊 Test Case List

### ✅ MainActivityTest (6 Tests)

| # | Test Method | Test Content | Expected Result |
|---|---------|---------|---------|
| 1 | testLoginScreenDisplayed | Verify UI elements | All elements display correctly |
| 2 | testEmptyCredentials | Empty input | Show error message |
| 3 | testInvalidCredentials | Wrong credentials | Login fails, stay on login page |
| 4 | testSuccessfulLogin | Correct login | Jump to WebView page |
| 5 | testLoginButtonDisabledDuringLogin | Loading state | Button disabled, show progress |
| 6 | testDifferentValidUsers | Multiple users | All 3 accounts can login |

### ✅ WebViewActivityTest (9 Tests)

| # | Test Method | Test Content | Expected Result |
|---|---------|---------|---------|
| 1 | testWebViewActivityDisplayed | Verify UI elements | All elements display correctly |
| 2 | testLoadDifferentUrl | URL loading | URL loads correctly |
| 3 | testLoadUrlWithHttps | HTTPS loading | HTTPS URL loads normally |
| 4 | testLogoutButton | Logout functionality | Correctly return to login page |
| 5 | testWebViewLoadingProgress | Loading progress | Progress bar displays |
| 6 | testEmptyUrlHandling | Empty URL | Correct error handling |
| 7 | testMultipleUrlLoads | Multiple loads | Continuous loading works |
| 8 | testWebViewWithGoogleSearch | Google test | External website loads normally |
| 9 | testUserInfoPersistence | Info persistence | User info remains unchanged |

---

## 🔍 Test Preparation Notes

### Pre-Test Preparation

1. **Disable Device Animation** (Important!)
```
Device Settings → Developer Options → Set following to "off":
- Window animation scale
- Transition animation scale
- Animator duration scale
```

2. **Ensure Network Connection**
```
WebView tests load external web pages
Ensure device has internet connection
```

3. **Keep Screen On**
```
Tests need screen on during execution
Recommended: Plug in power and set screen always on
```

### View Test Results

**In Android Studio**:
- Tests complete, Run window shows results
- Green ✓ = passed
- Red ✗ = failed
- Click failed test to see error details

**View HTML Report**:
```bash
# Auto-generated after tests complete
open app/build/reports/androidTests/connected/index.html
```

---

## 🎓 Test Accounts

| Username | Password | Purpose |
|-----------|------|------|
| demo | password123 | Main test account |
| test | test123 | Backup account |
| admin | admin123 | Admin account |

---

## 🐛 Common Questions

### Q1: Gradle sync failed, what to do?

**Solution**:
```
1. File → Invalidate Caches / Restart
2. Select "Invalidate and Restart"
3. Wait for Android Studio to restart
4. Let Gradle resync
```

### Q2: Tests always fail?

**Check items**:
- ✅ Device animation disabled
- ✅ Network connection normal
- ✅ Device screen on
- ✅ No other apps using screen

### Q3: Device not found?

**Physical device**:
```
1. Confirm USB cable connected properly
2. Confirm USB Debug enabled
3. Reauthorize USB Debug
4. Try reconnecting USB
```

**Emulator**:
```
1. Tools → Device Manager
2. Check emulator status is "Running"
3. Try restarting emulator
4. Confirm HAXM/Hyper-V enabled
```

### Q4: Build too slow?

**Optimization tips**:
```
1. Increase Gradle memory:
   Edit gradle.properties:
   org.gradle.jvmargs=-Xmx4096m

2. Enable Gradle daemon:
   org.gradle.daemon=true

3. Enable parallel build:
   org.gradle.parallel=true
```

### Q5: Tests run too slow?

**Optimization tips**:
- Use newer emulator (API 30+)
- Use x86_64 system image (faster than ARM)
- Increase emulator RAM and CPU cores
- Use physical device (usually faster)

---

## 📖 Extended Learning

### Modify Tests

**Add new test case**:
```kotlin
@Test
fun testNewFeature() {
    // 1. Operate UI
    onView(withId(R.id.myButton))
        .perform(click())

    // 2. Verify result
    onView(withId(R.id.myText))
        .check(matches(withText("Expected")))
}
```

**Add new test account**:
```kotlin
// Edit LoginService.kt
private val validUsers = mapOf(
    "demo" to "password123",
    "test" to "test123",
    "admin" to "admin123",
    "newuser" to "newpass"  // Add new account
)
```

### Related Resources

- [Android Testing Guide](https://developer.android.com/training/testing)
- [Espresso Documentation](https://developer.android.com/training/testing/espresso)
- [JUnit 4 Documentation](https://junit.org/junit4/)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)

---

## ✅ Checklist

Before Build:
- [ ] Android Studio installed
- [ ] Project opened in Android Studio
- [ ] Gradle sync completed
- [ ] JDK settings correct

Before Run:
- [ ] Device/emulator connected
- [ ] Device animation disabled
- [ ] Network connection normal
- [ ] Screen on

Before Test:
- [ ] App built successfully
- [ ] App starts normally
- [ ] Manual login test works
- [ ] Ready for automated tests

---

## 📞 Need Help?

If problems occur:

1. **Check build guide**: See `BUILD_TEST_GUIDE.md`
2. **Check project description**: See `README.md`
3. **View error log**: Android Studio → Build → Build Output
4. **Clean project**: Build → Clean Project → Rebuild Project

---

**Project Created**: 2025-11-07
**Last Updated**: 2025-11-07
**Version**: 1.0.0
**Status**: ✅ Ready to Use
