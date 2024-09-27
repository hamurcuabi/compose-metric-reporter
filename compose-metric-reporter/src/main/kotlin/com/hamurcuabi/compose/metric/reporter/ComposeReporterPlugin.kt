package com.hamurcuabi.compose.metric.reporter

import com.android.build.api.variant.AndroidComponentsExtension
import com.hamurcuabi.compose.metric.reporter.tasks.HtmlGeneratorExtension
import com.hamurcuabi.compose.metric.reporter.util.Constants
import com.hamurcuabi.compose.metric.reporter.util.configureKotlinAndroidComposeCompilerReports
import org.gradle.api.Plugin
import org.gradle.api.Project

internal class ComposeReporterPlugin : Plugin<Project> {

    override fun apply(project: Project) {

        val extension = HtmlGeneratorExtension.Companion.create(project)

        with(project) {
            val android = extensions.getByType(AndroidComponentsExtension::class.java)

            pluginManager.withPlugin(Constants.ANDROID_APPLICATION) {
                configureKotlinAndroidComposeCompilerReports(android)
            }
            pluginManager.withPlugin(Constants.ANDROID_LIBRARY) {
                configureKotlinAndroidComposeCompilerReports(android)
            }
        }
    }
}

