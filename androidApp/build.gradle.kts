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
  buildToolsVersion = "28.0.2"
  compileSdkVersion(28)
  defaultConfig {
    minSdkVersion(21)
    targetSdkVersion(28)
    multiDexEnabled = true
    versionCode = 1
    testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
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

  sourceSets {
    getByName("main").java.srcDirs("src/main/kotlin")
  }
  packagingOptions {
    exclude("META-INF/*")
  }
}

dependencies {
  implementation("com.android.support.constraint:constraint-layout:1.1.3")
  implementation("com.android.support.constraint:constraint-layout:1.1.3")
  implementation("com.android.support:appcompat-v7:28.0.0")
  implementation("com.android.support:recyclerview-v7:28.0.0")
  implementation("com.android.support:recyclerview-v7:28.0.0")
  implementation("com.github.pedrovgs:renderers:3.4.0")
  implementation("com.squareup.okhttp3:okhttp:3.11.0")
  implementation("com.squareup.picasso:picasso:2.71828")
  implementation(kotlin("stdlib-jdk7", KotlinCompilerVersion.VERSION))
  implementation(project(":shared"))
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