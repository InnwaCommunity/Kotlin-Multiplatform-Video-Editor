plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.orgJetbrainsCompose)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation("org.jetbrains.compose.components:components-resources:1.6.0-dev1275")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

            // Kotlin Coroutines 1.7.1 contains Dispatchers.IO for iOS
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            api("androidx.activity:activity-compose:1.7.2")
            api("androidx.appcompat:appcompat:1.6.1")
            api("androidx.core:core-ktx:1.10.1")
            implementation("androidx.camera:camera-camera2:1.2.3")
            implementation("androidx.camera:camera-lifecycle:1.2.3")
            implementation("androidx.camera:camera-view:1.2.3")
            implementation("com.google.accompanist:accompanist-permissions:0.29.2-rc")
            implementation("com.google.android.gms:play-services-maps:18.1.0")
            implementation("com.google.android.gms:play-services-location:21.0.1")
            implementation("com.google.maps.android:maps-compose:2.11.2")
        }
    }
}

android {
    namespace = "com.manage.videoeditor"
    compileSdk = 34
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlin {
        jvmToolchain(17)
    }
}
