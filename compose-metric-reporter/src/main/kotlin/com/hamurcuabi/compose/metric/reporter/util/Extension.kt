package com.hamurcuabi.compose.metric.reporter.util

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.variant.AndroidComponentsExtension
import com.hamurcuabi.compose.metric.reporter.tasks.GenerateHtmlTask
import com.hamurcuabi.compose.metric.reporter.tasks.HtmlGeneratorExtension
import org.gradle.api.Project
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.support.uppercaseFirstChar

fun Project.configureKotlinAndroidComposeCompilerReports(android: AndroidComponentsExtension<*, *, *>) {
    val commonExtension =
        runCatching { extensions.getByType(CommonExtension::class.java) }.getOrNull()

    afterEvaluate {
        val isComposeEnabled = commonExtension?.buildFeatures?.compose

        if (isComposeEnabled != true) {
            error("Jetpack Compose is not found enabled in this module '$name'")
        }
    }
    android.onVariants { variant ->
        val taskName = project.name + "_" + variant.name + "MetricReporter"
        val descSuffix = "'${variant.name}' variant in Android project"

        registerComposeCompilerWithVariant(taskName, descSuffix,variant.name)

        val build = ":${project.name}:assemble${variant.name.uppercaseFirstChar()}"
        tasks.getByName(taskName) {
            dependsOn(build)
        }
    }
}


fun Project.registerComposeCompilerWithVariant(
    taskName: String,
    descSuffix: String,
    variant: String,
): TaskProvider<GenerateHtmlTask> {
    val reportExtension = HtmlGeneratorExtension.Companion.get(project)
    return tasks.register(taskName, GenerateHtmlTask::class.java) {
        variantName.set(variant)
        outputPath.set("${project.name}-${reportExtension.outputPath.get()}")
        projectName.set(project.name)
        toolbarTitle.set(reportExtension.toolbarTitle)
        excludeSuffix.set(reportExtension.excludeSuffix.get())
        hideComposableWithNoParams.set(reportExtension.hideComposableWithNoParams.get())
        group = Constants.TASK_GROUP
        description = "Generate Compose Compiler Metrics and Report for $descSuffix"
    }
}









