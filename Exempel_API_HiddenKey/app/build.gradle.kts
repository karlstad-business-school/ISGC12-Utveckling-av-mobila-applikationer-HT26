import java.util.Properties

val properties = Properties()
properties.load(rootProject.file("local.properties").inputStream())

val apiKey = properties.getProperty("HIDDEN_API_KEY")


plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.exempel_api_hiddenkey"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.exempel_api_hiddenkey"
        minSdk = 26
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "HIDDEN_API_KEY", "\"$apiKey\"")
    }

    buildFeatures{
        buildConfig = true
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }


}

dependencies {
    implementation(libs.activity.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ext.junit)
    implementation("com.android.volley:volley:1.2.1")
}