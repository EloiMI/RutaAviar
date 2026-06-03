plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.rutaaviar"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.rutaaviar"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

        isCoreLibraryDesugaringEnabled = true //date
    }
}

dependencies {
    implementation(libs.activity.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.play.services.maps)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
    implementation ("com.google.code.gson:gson:2.11.0")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4") //date
    //implementation("org.bouncycastle:bcprov-jdk15to18:1.70")
    implementation("org.mindrot:jbcrypt:0.4")
    implementation("com.google.android.gms:play-services-location:21.3.0")
    implementation ("androidx.core:core-ktx:1.10.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    //kapt("com.github.bumptech.glide:compiler:4.16.0")
    implementation("org.osmdroid:osmdroid-android:6.1.18")
}