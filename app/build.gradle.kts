plugins {
    alias(libs.plugins.githubapp.android.application)
    alias(libs.plugins.githubapp.jacoco)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ktlint)
}

android {
    namespace = "com.omooooori.githubuserclient"

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
