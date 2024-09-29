package com.hamurcuabi.compose.metric.reporter.util

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.variant.AndroidComponentsExtension
import com.hamurcuabi.compose.metric.reporter.tasks.ComposeHtmlGeneratorExtension
import com.hamurcuabi.compose.metric.reporter.tasks.GenerateComposeHtmlTask
import com.hamurcuabi.compose.metric.reporter.util.Constants.TASK_GROUP
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
        val taskName =
            getDirPath() + "_" + variant.name + "MetricReporter"
        val descSuffix = "'${variant.name}' variant in Android project"

        registerComposeCompilerWithVariant(taskName, descSuffix, variant.name)

        val build =
            ":${getAssembleDirPath()}:assemble${variant.name.uppercaseFirstChar()}"
        tasks.getByName(taskName) {
            dependsOn(build)
        }
    }
}

fun Project.registerComposeCompilerWithVariant(
    taskName: String,
    descSuffix: String,
    variant: String,
): TaskProvider<GenerateComposeHtmlTask> {
    val reportExtension = ComposeHtmlGeneratorExtension.get(project)
    return tasks.register(taskName, GenerateComposeHtmlTask::class.java) {
        variantName.set(variant)
        outputPath.set("${getDirPath()}-${reportExtension.outputPath.get()}")
        projectName.set(getProjectName())
        toolbarTitle.set(reportExtension.toolbarTitle)
        excludeSuffixForFunctions.set(reportExtension.excludeSuffixForFunctions.get())
        excludeSuffixForClasses.set(reportExtension.excludeSuffixForClasses.get())
        hideComposableWithNoParams.set(reportExtension.hideComposableWithNoParams.get())
        group = TASK_GROUP
        description = "Generate Compose Compiler Metrics and Report for $descSuffix"
    }
}

fun Project.getDirPath() =
    project.projectDir.relativeTo(rootProject.projectDir).path.replace("/", "_")

fun Project.getProjectName() =
    project.projectDir.relativeTo(rootProject.projectDir).path.replace("/", ":")

fun Project.getAssembleDirPath() =
    project.projectDir.relativeTo(rootProject.projectDir).path.replace("/", ":")
