package com.hamurcuabi.compose.metric.reporter.tasks

import com.hamurcuabi.compose.metric.reporter.util.Constants
import org.gradle.api.Project
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property

interface HtmlGeneratorExtension {

    val toolbarTitle: Property<String>

    val projectName: Property<String>

    val outputPath: Property<String>

    val excludeSuffix: ListProperty<String>

    val hideComposableWithNoParams: Property<Boolean>

    val variantName: Property<String>

    companion object {

        fun create(project: Project) = project.extensions.create(
            Constants.PLUGIN_EXT_NAME, HtmlGeneratorExtension::class.java
        ).apply {
            variantName.set("debug")
            outputPath.set("${project.name}-compose-metric-reporter")
            toolbarTitle.set("")
            projectName.set(project.name)
            excludeSuffix.set(listOf())
            hideComposableWithNoParams.set(false)
        }

        fun get(target: Project) = target.extensions.getByType(HtmlGeneratorExtension::class.java)
    }
}
