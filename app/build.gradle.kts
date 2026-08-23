plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.kotlin.plugin.parcelize")
    id("com.diffplug.spotless")
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

    buildFeatures {
        compose = true
    }

    defaultConfig {
        applicationId = "com.example.capybara"
        minSdk = 36
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2026.06.01")
    implementation(composeBom)
    implementation("androidx.core:core-ktx:1.18.0")
    implementation("androidx.activity:activity:1.13.0")
    implementation("androidx.activity:activity-compose:1.13.0")
    implementation("androidx.compose.material3:material3:1.3.2")
}

spotless {
    kotlin {
        ktfmt().googleStyle().configure {
            it.setRemoveUnusedImports(true)
        }
    }
}