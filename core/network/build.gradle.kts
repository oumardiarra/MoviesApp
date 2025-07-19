
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.secret.gradle.plugin)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.example.network"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures{
        buildConfig = true
    }
}

dependencies {
    implementation(project(":core:model"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // Kotlinx serialization
    implementation(libs.kotlinx.serialization.json)

    // Retrofit and kotlinx serialization converter
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx.serialization)

    //Paging
    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)

    //Dagger Hilt
    implementation(libs.dagger.hilt)
    implementation(libs.androidx.paging.testing.android)
    ksp(libs.dagger.hilt.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.paging.common)

    //Mockk
    testImplementation(libs.mockk)
    testImplementation (libs.mockk.android)
    testImplementation (libs.mockk.agent)

    // webserver
    testImplementation(libs.okhttp3.mockwebserver)
    // Coroutine test
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.assertj.core)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}