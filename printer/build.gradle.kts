plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlinKapt)
}

android {
    namespace = "com.zenenta.printer"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        ndk {
            abiFilters += listOf("armeabi", "armeabi-v7a", "arm64-v8a", "x86", "x86_64")
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

    // PackagingOptions (Kotlin DSL)
    packaging {
        jniLibs {
            useLegacyPackaging = true
        }
        resources {
            excludes += setOf("lib/arm64/libepos2.so")
        }
    }

    // SourceSets (Kotlin DSL)
    sourceSets {
        getByName("main") {
            jniLibs.srcDirs("src/main/jniLibs")
            aidl.srcDirs("src/main/aidl")
        }
    }

    afterEvaluate {
        tasks.withType<Copy>().configureEach {
            from(zipTree(configurations.api.get().singleFile)) {
                include("**/*.so")
                into("src/main/jniLibs")
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        aidl = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Glide
    implementation(libs.glide)
    kapt(libs.compiler)

    //noinspection UseTomlInstead
    api("net.java.dev.jna:jna:5.18.1@aar")

    implementation(libs.zxing.android.embedded)
    implementation(libs.qrgenerator)
    implementation(libs.timber)
}