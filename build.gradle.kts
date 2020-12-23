plugins {
    id("io.gitlab.arturbosch.detekt") version "1.15.0"
}

buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.21")
        classpath("com.android.tools.build:gradle:7.0.0-alpha03")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}

val detektAll by tasks.registering(io.gitlab.arturbosch.detekt.Detekt::class) {
    parallel = true
    failFast = false
    autoCorrect = true
    buildUponDefaultConfig = true
    setSource(files(projectDir))
    config.setFrom(project.file(".static/detekt-config.yml"))
    include("**/*.kt")
    exclude("*/build/")
    reports {
        html {
            enabled = true
            destination = project.file("build/reports/detekt.html")
        }
        xml.enabled = false
    }
}

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.15.0")
}