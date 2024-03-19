// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.51")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
        // Add any other dependencies if needed
    }
}
plugins {
    id("com.android.application") version "8.0.2" apply false
    id ("com.android.library") version "8.0.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.20" apply false
    id("com.google.dagger.hilt.android") version "2.51" apply false
}