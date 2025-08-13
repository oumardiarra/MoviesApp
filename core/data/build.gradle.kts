plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.example.data"
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
        freeCompilerArgs = listOf("-XXLanguage:+PropertyParamAnnotationDefaultTargetMode")
    }
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:network"))
    //Dagger Hilt
    implementation(libs.dagger.hilt)
    ksp(libs.dagger.hilt.compiler)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.paging.common.android)
    implementation(libs.retrofit)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    // webserver
    testImplementation(libs.okhttp3.mockwebserver)

    //Mockk
    testImplementation(libs.mockk)
    testImplementation (libs.mockk.android)
    testImplementation (libs.mockk.agent)
    testImplementation(libs.assertj.core)
    // Turbine
    testImplementation(libs.turbine)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}