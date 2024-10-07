plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.behruzbek0430.movieapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.behruzbek0430.movieapp"
        minSdk = 26
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    viewBinding{
     enable = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation ("com.ToxicBakery.viewpager.transforms:view-pager-transforms:2.0.24")
    implementation ("androidx.viewpager2:viewpager2:1.0.0")
    implementation ("androidx.recyclerview:recyclerview:1.2.1")
    implementation ("androidx.appcompat:appcompat:1.4.0")

    implementation ("androidx.appcompat:appcompat:1.3.1")
    implementation ("com.google.firebase:firebase-auth:23.0.0")
    //noinspection GradleDependency,UseTomlInstead
    implementation("com.google.android.gms:play-services-auth:19.2.0")
    implementation ("com.squareup.picasso:picasso:2.8")
    implementation("com.google.code.gson:gson:2.10.1")
    //noinspection UseTomlInstead
    implementation ("com.google.zxing:core:3.4.1")
    //noinspection UseTomlInstead
    implementation ("com.journeyapps:zxing-android-embedded:4.2.0")

    implementation("com.google.firebase:firebase-auth-ktx:23.0.0")
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")
}