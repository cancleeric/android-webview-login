# Android WebView + Login Testing Project

This is a native Android application developed in Kotlin, showcasing Login authentication and WebView browsing functionality, with comprehensive Espresso UI automated testing.

## 📋 Project Features

- ✅ **Native Kotlin Development**: Using latest Kotlin syntax and Android best practices
- ✅ **Material Design**: Adopting Material Design specifications
- ✅ **MVVM Architecture**: Implementing MVVM architecture with ViewModel and LiveData
- ✅ **Mock Login Service**: Built-in mock login service, no actual backend required
- ✅ **WebView Integration**: Complete WebView implementation supporting web browsing
- ✅ **Espresso Testing**: Complete UI automated test coverage
- ✅ **ViewBinding**: Using ViewBinding for improved code safety

## 🏗️ Project Structure

```
android-webview-login/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/webviewlogin/
│   │   │   │   ├── model/              # Data models
│   │   │   │   │   ├── User.kt
│   │   │   │   │   └── LoginResult.kt
│   │   │   │   ├── service/            # Service layer
│   │   │   │   │   └── LoginService.kt
│   │   │   │   ├── MainActivity.kt     # Login screen
│   │   │   │   ├── MainViewModel.kt    # Login ViewModel
│   │   │   │   └── WebViewActivity.kt  # WebView screen
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   ├── activity_main.xml
│   │   │   │   │   └── activity_webview.xml
│   │   │   │   └── values/
│   │   │   └── AndroidManifest.xml
│   │   └── androidTest/
│   │       └── java/com/example/webviewlogin/
│   │           ├── MainActivityTest.kt      # Login tests
│   │           └── WebViewActivityTest.kt   # WebView tests
│   └── build.gradle
├── build.gradle
├── settings.gradle
└── README.md
```

## 🚀 Quick Start

### Environment Requirements

- Android Studio Hedgehog (2023.1.1) or newer
- JDK 17 (recommended for command-line compatibility)
- Android SDK API Level 33
- Gradle 8.4

### Installation Steps

1. **Clone or copy the project**
   ```bash
   cd /Users/yinghaowang/Work/android-webview-login
   ```

2. **Open the project in Android Studio**
   - Open Android Studio
   - Select "Open an Existing Project"
   - Choose the `android-webview-login` folder

3. **Wait for Gradle sync to complete**
   - Android Studio will automatically download dependencies

4. **Run the application**
   - Connect a physical device or launch an emulator
   - Click the "Run" button (green triangle)

## 🔐 Test Accounts

The application includes three built-in test accounts:

| Username | Password |
|----------|----------|
| demo | password123 |
| test | test123 |
| admin | admin123 |

## 🧪 Running Tests

### Using Android Studio

1. Find test files in project navigator:
   - `app/src/androidTest/java/com/example/webviewlogin/MainActivityTest.kt`
   - `app/src/androidTest/java/com/example/webviewlogin/WebViewActivityTest.kt`

2. Right-click on test file or test class

3. Select "Run 'MainActivityTest'" or "Run 'WebViewActivityTest'"

### Using Command Line

**Preparation**: Ensure emulator is **API 33** (Android 13)
- API 36 (Android 16 Beta) is **NOT supported** for Espresso testing
- Recommended: Pixel 6 API 33 emulator

```bash
# Set up environment (use JDK 17)
export JAVA_HOME="/opt/homebrew/opt/openjdk@17"
export ANDROID_HOME="$HOME/Library/Android/sdk"

# Run all UI tests
./gradlew connectedAndroidTest

# Run specific test class
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.example.webviewlogin.MainActivityTest
```

## 📊 Test Results

### Latest Test Results (2025-11-07)

**Status**: ✅ **15/15 tests passed (100%)**

| Test Suite | Passed | Failed | Pass Rate | Duration |
|------------|--------|--------|-----------|----------|
| MainActivityTest | 6/6 | 0 | 100% | 27.5s |
| WebViewActivityTest | 9/9 | 0 | 100% | 39.9s |
| **Total** | **15/15** | **0** | **100%** | **1m 7s** |

**Test Environment**:
- Emulator: Pixel 6 API 33
- Build Tools: JDK 17 + Gradle 8.4
- Test Framework: Espresso 3.6.1

## 📱 Feature Description

### 1. Login Feature (MainActivity)

- **Features**:
  - Username and password input
  - Form validation
  - Mock login service authentication
  - Loading state display
  - Navigate to WebView after successful login

- **Test Coverage**:
  - ✅ Screen elements display test
  - ✅ Empty credentials test
  - ✅ Invalid credentials test
  - ✅ Valid credentials test
  - ✅ Login button disabled during login test
  - ✅ Multiple users test

### 2. WebView Feature (WebViewActivity)

- **Features**:
  - Display logged-in user information
  - URL input and loading
  - Automatic HTTPS protocol addition
  - Page loading progress bar
  - Page forward/backward navigation
  - Logout functionality

- **Test Coverage**:
  - ✅ Screen elements display test
  - ✅ Load different URL test
  - ✅ HTTPS URL test
  - ✅ Logout button test
  - ✅ Empty URL handling test
  - ✅ Multiple URL loads test
  - ✅ User information persistence test

## 🛠️ Technical Architecture

### Technologies and Libraries

- **Language**: Kotlin 1.9.20
- **UI Framework**: AndroidX, Material Design Components
- **Architecture Components**:
  - ViewModel
  - LiveData
  - ViewBinding
- **WebView**: AndroidX WebKit
- **Coroutines**: Kotlin Coroutines
- **Testing Framework**:
  - Espresso 3.6.1 (UI testing)
  - JUnit 4.13.2
  - Mockito 5.7.0

### Architecture Pattern

This project adopts **MVVM (Model-View-ViewModel)** architecture:

```
View (Activity) ←→ ViewModel ←→ Model (Service/Repository)
```

- **View**: MainActivity, WebViewActivity
- **ViewModel**: MainViewModel
- **Model**: LoginService, User, LoginResult

## 📝 Test Case Description

### MainActivityTest (Login Tests)

1. **testLoginScreenDisplayed**: Verify login screen elements are displayed correctly
2. **testEmptyCredentials**: Test handling of empty username/password
3. **testInvalidCredentials**: Test handling of invalid credentials
4. **testSuccessfulLogin**: Test successful login and navigation
5. **testLoginButtonDisabledDuringLogin**: Test UI state during login
6. **testDifferentValidUsers**: Test login with multiple valid users

### WebViewActivityTest (WebView Tests)

1. **testWebViewActivityDisplayed**: Verify WebView screen elements
2. **testLoadDifferentUrl**: Test loading different URLs
3. **testLoadUrlWithHttps**: Test HTTPS URL loading
4. **testLogoutButton**: Test logout functionality
5. **testWebViewLoadingProgress**: Test loading progress display
6. **testEmptyUrlHandling**: Test empty URL handling
7. **testMultipleUrlLoads**: Test loading multiple URLs consecutively
8. **testWebViewWithGoogleSearch**: Test loading Google webpage
9. **testUserInfoPersistence**: Test user information persistence

## 🔍 Code Highlights

### 1. LoginService - Mock Login Service

```kotlin
class LoginService {
    suspend fun login(username: String, password: String): LoginResult {
        delay(1000) // Simulate network delay
        // Validation logic...
    }
}
```

### 2. MainViewModel - MVVM Architecture

```kotlin
class MainViewModel : ViewModel() {
    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> = _loginState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            // Asynchronous login logic
        }
    }
}
```

### 3. Espresso Test Example

```kotlin
@Test
fun testSuccessfulLogin() {
    onView(withId(R.id.usernameEditText))
        .perform(typeText("demo"), closeSoftKeyboard())
    onView(withId(R.id.passwordEditText))
        .perform(typeText("password123"), closeSoftKeyboard())
    onView(withId(R.id.loginButton))
        .perform(click())

    // Verify successful navigation
    onView(withId(R.id.webView))
        .check(matches(isDisplayed()))
}
```

## 🐛 Troubleshooting

### Issue: Gradle Sync Failed

**Solution**:
```bash
./gradlew clean
./gradlew build --refresh-dependencies
```

### Issue: Tests Execution Failed

**Solution**:
1. Ensure emulator or physical device is connected
2. Disable device animations (in Developer Options)
3. Ensure network connection is working (some WebView tests require internet)

### Issue: ViewBinding Error

**Solution**:
1. Clean Project: Build → Clean Project
2. Rebuild Project: Build → Rebuild Project

### Issue: JDK Compatibility (Command Line)

**Problem**: JDK 21 incompatible with Android Gradle Plugin in CLI

**Solution**: Use JDK 17
```bash
brew install openjdk@17
export JAVA_HOME="/opt/homebrew/opt/openjdk@17"
```

### Issue: API 36 Emulator Test Failures

**Problem**: All tests fail on API 36 (Android 16 Beta) emulator

**Solution**: Use API 33 emulator instead
- API 36 Beta has internal API changes not supported by Espresso 3.6.1
- Create Pixel 6 API 33 emulator via Android Studio Device Manager

## 📄 License

This project is for educational and demonstration purposes. Free to use and modify.

## 📧 Contact

For questions or suggestions, feel free to open an Issue or Pull Request.

---

**Created**: 2025-11-07
**Version**: 1.0.0
**Status**: ✅ Fully Ready (100% tests passing, command-line compatible with JDK 17)
**Development Tools**: Android Studio Hedgehog | 2023.1.1

## 📚 Additional Documentation

- **[QUICK_START.md](QUICK_START.md)** - Quick start guide for new users
- **[CLI_TEST_GUIDE.md](CLI_TEST_GUIDE.md)** - Command-line testing guide
- **[BUILD_TEST_GUIDE.md](BUILD_TEST_GUIDE.md)** - Detailed build and test guide
- **[TEST_RESULTS.md](TEST_RESULTS.md)** - Complete test execution results and fix history
- **[SUMMARY.md](SUMMARY.md)** - Project summary and final status
