plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")
}


android {
    namespace = "com.example.vortexcar"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.vortexcar"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation ("com.google.android.material:material:1.4.0")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-firestore")
    implementation ("com.google.firebase:firebase-auth")
    implementation ("com.google.firebase:firebase-messaging")
    implementation ("com.google.firebase:firebase-inappmessaging-display:21.0.0messaging")
    implementation(platform("com.google.firebase:firebase-bom:33.1.0"))
    implementation ("com.android.volley:volley:1.2.1")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    implementation ("androidx.recyclerview:recyclerview:1.2.1")
    implementation(libs.appcompat);
    implementation(libs.material);
    implementation(libs.activity);
    implementation(libs.constraintlayout);
    testImplementation(libs.junit);
    androidTestImplementation(libs.ext.junit);
    androidTestImplementation(libs.espresso.core);
}