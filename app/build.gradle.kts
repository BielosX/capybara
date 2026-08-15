plugins {
    id("com.android.application")
}

/*
    https://developer.android.com/build/jdks
 */
kotlin {
    jvmToolchain(17)
}

android {
    namespace = "com.example.capybara"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.capybara"
        minSdk = 34
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
}