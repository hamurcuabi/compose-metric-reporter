import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val POM_DESCRIPTION: String by project
val POM_URL: String by project
val PLUGIN_ID: String by project
val GROUP: String by project
val VERSION_NAME: String by project
val POM_NAME: String by project

plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    `maven-publish`
    alias(libs.plugins.gradle.plugin.publish)
    alias(libs.plugins.mavenPublish)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
        incremental = true
    }
}

dependencies {
    compileOnly(gradleApi())
    compileOnly(kotlin("stdlib"))
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    implementation(libs.kotlinx.serialization.json)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

group = GROUP
version = VERSION_NAME

gradlePlugin {
    website = POM_URL
    vcsUrl = POM_URL
    plugins {
        create("composeReporterPlugin") {
            id = PLUGIN_ID
            group = GROUP
            version = VERSION_NAME
            implementationClass = "com.hamurcuabi.compose.metric.reporter.ComposeReporterPlugin"
            displayName = POM_NAME
            description = POM_DESCRIPTION
            tags = listOf("kotlin", "htmlreport", "compose", "report", "jetpackcompose", "android")
        }
    }
}



