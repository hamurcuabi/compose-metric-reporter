package com.hamurcuabi.compose.metric.reporter.tasks

import com.hamurcuabi.compose.metric.reporter.util.Constants.PLUGIN_EXT_NAME
import com.hamurcuabi.compose.metric.reporter.util.getDirPath
import org.gradle.api.Project
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property

interface ComposeHtmlGeneratorExtension {

    val toolbarTitle: Property<String>

    val projectName: Property<String>

    val outputPath: Property<String>

    val excludeSuffixForFunctions: ListProperty<String>

    val excludeSuffixForClasses: ListProperty<String>

    val hideComposableWithNoParams: Property<Boolean>

    val variantName: Property<String>

    companion object {

        fun create(project: Project) = project.extensions.create(
            PLUGIN_EXT_NAME, ComposeHtmlGeneratorExtension::class.java
        ).apply {
            variantName.set("debug")
            outputPath.set("${project.getDirPath()}-compose-metric-reporter")
            toolbarTitle.set("")
            projectName.set(project.getDirPath())
            excludeSuffixForFunctions.set(listOf())
            excludeSuffixForClasses.set(listOf())
            hideComposableWithNoParams.set(false)
        }

        fun get(target: Project) =
            target.extensions.getByType(ComposeHtmlGeneratorExtension::class.java)
    }
}
