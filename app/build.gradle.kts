plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ktlint)
}

android {
    namespace = "com.omooooori.githubuserclient"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.omooooori.githubuserclient"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":api"))
    implementation(project(":design"))
    implementation(project(":domain"))
    implementation(project(":feature-userdetail"))
    implementation(project(":feature-userlist"))
    implementation(project(":data"))
    implementation(project(":model"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.bundles.compose)

    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.koin.core)

    implementation(libs.bundles.ktor)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.bundles.compose.test)
    androidTestImplementation(libs.bundles.paging)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    androidTestImplementation(project(":model"))
    androidTestImplementation(project(":feature-userlist"))
    androidTestImplementation(project(":feature-userdetail"))
}

tasks.register("jacocoTestReport", JacocoReport::class) {
    dependsOn("testDebugUnitTest")

    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    val fileFilter =
        listOf(
            "**/R.class",
            "**/R$*.class",
            "**/BuildConfig.*",
            "**/Manifest*.*",
        )

    val debugTree =
        fileTree("${project.buildDir}/tmp/kotlin-classes/debug") {
            exclude(fileFilter)
        }

    sourceDirectories.setFrom(files("${project.projectDir}/src/main/java"))
    classDirectories.setFrom(files(debugTree))
    executionData.setFrom(
        fileTree(project.buildDir) {
            include("/jacoco/testDebugUnitTest.exec")
        },
    )
}
