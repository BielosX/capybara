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
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.capybara"
        minSdk = 36
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.18.0")
    implementation("androidx.activity:activity:1.13.0")
}