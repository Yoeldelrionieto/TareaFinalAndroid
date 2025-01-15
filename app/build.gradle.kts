plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    kotlin("kapt")
    //Firebase
    id("com.google.gms.google-services")
}

android {
    namespace = "com.composse.tareafinal"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.composse.tareafinal"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation(libs.firebase.analytics)
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation ("com.google.firebase:firebase-firestore-ktx")

    implementation ("androidx.compose.material:material:1.0.0")
    implementation ("androidx.compose.material3:material3:1.0.0-alpha01")
    implementation ("androidx.compose.material:material:1.0.5")
    implementation ("androidx.compose.material:material-icons-extended:1.0.5")
    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // Room
    implementation ("androidx.room:room-runtime:2.5.2")
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(libs.androidx.espresso.core)
    annotationProcessor ("androidx.room:room-compiler:2.5.2")
    kapt ("androidx.room:room-compiler:2.5.2")

    // Navigation
    // implementation ("androidx.navigation:navigation-compose:2.7.2")
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.activity.compose)

    // Notifications
    implementation ("androidx.core:core-ktx:1.12.0")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}