import org.jetbrains.kotlin.config.KotlinCompilerVersion
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
  id("com.android.application")
  id("kotlin-android")
  id("kotlin-android-extensions")
  id("io.gitlab.arturbosch.detekt")
  id("org.jlleitschuh.gradle.ktlint")
}

android {
  buildToolsVersion = "28.0.3"
  compileSdkVersion(28)
  defaultConfig {
    minSdkVersion(21)
    targetSdkVersion(28)
    multiDexEnabled = true
    versionCode = 1
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
    }
  }

  lintOptions {
    setAbortOnError(false)
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  sourceSets {
    getByName("main").java.srcDirs("src/main/kotlin")
    getByName("androidTest").java.srcDirs("src/androidTest/kotlin")
  }
  packagingOptions {
    exclude("META-INF/*")
  }
}

dependencies {
  implementation(kotlin("stdlib-jdk7", KotlinCompilerVersion.VERSION))
  implementation(project(":shared"))

  implementation("androidx.constraintlayout:constraintlayout:2.0.0-beta1")
  implementation("androidx.appcompat:appcompat:1.0.2")
  implementation("androidx.recyclerview:recyclerview:1.0.0")
  implementation("androidx.lifecycle:lifecycle-runtime:2.0.0")
  implementation("androidx.lifecycle:lifecycle-extensions:2.0.0")
  implementation("com.github.pedrovgs:renderers:3.4.0")
  implementation("com.squareup.okhttp3:okhttp:3.11.0")
  implementation("com.squareup.picasso:picasso:2.71828")

  testImplementation("junit:junit:4.12")
  androidTestImplementation("org.mockito:mockito-android:2.28.2")
  androidTestImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0") {
    exclude(group = "androidx.annotation")
  }
  androidTestImplementation("androidx.test:runner:1.2.0")
  androidTestImplementation("androidx.test:rules:1.2.0")
  androidTestImplementation("androidx.test.ext:junit:1.1.1")
  androidTestImplementation("com.schibsted.spain:barista:3.1.0") {
    exclude(group = "androidx.annotation")
    exclude(group = "org.jetbrains.kotlin")
  }
}

ktlint {
  version.set("0.30.0")
  debug.set(true)
  verbose.set(true)
  android.set(false)
  outputToConsole.set(true)
  reporters.set(setOf(ReporterType.PLAIN, ReporterType.CHECKSTYLE))
  ignoreFailures.set(true)
  filter {
    exclude("**/generated/**")
    include("**/kotlin/**")
  }
}

apply(from = "../shared/gradleconfig/lint.gradle")