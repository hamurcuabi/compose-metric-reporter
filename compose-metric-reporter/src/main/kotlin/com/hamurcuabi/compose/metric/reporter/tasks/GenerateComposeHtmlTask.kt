package com.hamurcuabi.compose.metric.reporter.tasks

import com.hamurcuabi.compose.metric.reporter.model.classes.ClassDetail
import com.hamurcuabi.compose.metric.reporter.model.common.Stability
import com.hamurcuabi.compose.metric.reporter.model.composables.ComposableDetail
import com.hamurcuabi.compose.metric.reporter.model.details.DetailedStatistics
import com.hamurcuabi.compose.metric.reporter.providers.ContentProvider
import com.hamurcuabi.compose.metric.reporter.providers.ReportProvider
import com.hamurcuabi.compose.metric.reporter.util.CSS
import com.hamurcuabi.compose.metric.reporter.util.CSS_FILE_NAME
import com.hamurcuabi.compose.metric.reporter.util.Constants.COMPOSE_METRICS_PATH
import com.hamurcuabi.compose.metric.reporter.util.Constants.INDEX_HTML
import com.hamurcuabi.compose.metric.reporter.util.Constants.SLASH
import com.hamurcuabi.compose.metric.reporter.util.SCRIPTS
import com.hamurcuabi.compose.metric.reporter.util.SCRIPTS_FILE_NAME
import org.gradle.api.DefaultTask
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.iterator

@CacheableTask
abstract class GenerateComposeHtmlTask : DefaultTask() {

    @get:Input
    abstract val toolbarTitle: Property<String>

    @get:Input
    abstract val projectName: Property<String>

    @get:Input
    abstract val outputPath: Property<String>

    @get:Input
    abstract val hideComposableWithNoParams: Property<Boolean>

    @get:Input
    abstract val excludeSuffixForFunctions: ListProperty<String>

    @get:Input
    abstract val excludeSuffixForClasses: ListProperty<String>

    @get:Input
    abstract val variantName: Property<String>

    @TaskAction
    fun generate() {
        val dir = project.layout.buildDirectory.asFile.get().absolutePath

        val outputDirectory = File(dir + SLASH + outputPath.get())

        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs()
        }
        val file = File(outputDirectory, INDEX_HTML)
        if (!file.exists()) {
            file.createNewFile()
        }
        val directory = File(dir + SLASH + COMPOSE_METRICS_PATH)

        val provider = ContentProvider(directory = directory, variantName = variantName.get())
        val report = ReportProvider(provider)
        val stableComposables = report.getStableComposablesReport(
            hideComposableWithNoParams = hideComposableWithNoParams.get(),
            excludeSuffix = excludeSuffixForClasses.get()
        )

        val unstableComposables = report.getUnstableComposablesReport(
            hideComposableWithNoParams = hideComposableWithNoParams.get(),
            excludeSuffix = excludeSuffixForFunctions.get()
        )

        val stableClasses = report.getStableClassesReport(
            excludeSuffix = excludeSuffixForClasses.get()
        )

        val unStableClasses = report.getUnStableClassesReport(
            excludeSuffix = excludeSuffixForClasses.get()
        )

        val overallStatistics = createOverallHtml(
            report.getOverallStatistics()
        )

        val detailedStatistics = createDetailedStatisticHtml(
            report.getDetailedStatistics()
        )

        val fullContent = generateIndexHtml(
            stableComposables = createComposableFunctionsHtml(stableComposables, "s"),
            stableComposablesCount = stableComposables.count().toString(),
            unstableComposables = createComposableFunctionsHtml(unstableComposables, "us"),
            unstableComposablesCount = unstableComposables.count().toString(),
            stableClasses = createClassesHtml(stableClasses, "sc"),
            stableClassesCount = stableClasses.count().toString(),
            unStableClasses = createClassesHtml(unStableClasses, "usc"),
            unStableClassesCount = unStableClasses.count().toString(),
            overallStatistics = overallStatistics,
            detailedStatistics = detailedStatistics,
        )
        file.writeText(fullContent)

        copyFileToOutput(CSS, CSS_FILE_NAME)
        copyFileToOutput(SCRIPTS, SCRIPTS_FILE_NAME)
    }

    private fun createDetailedStatisticHtml(statistics: DetailedStatistics): String {
        return buildString {
            val id = "statistics"
            val cardColor = "cardGreen"
            val sectionId = "section-class-${id}"

            append("<div class='$cardColor'>\n")
            append("<div class='card-header' onclick=\"toggleSection('$sectionId')\">\n")
            append("<span>Detailed Statistic</span>\n")
            append("<i id='$sectionId-icon' class='collapse-icon fas fa-chevron-down'></i>\n")
            append("</div>\n")
            append("<div id='$sectionId' class='collapsed-content'>\n")
            append("<div class='table-statistic-container'>\n")
            append("<table>\n")
            append("<tr>\n")

            statistics.headers.forEach { item ->
                append("<th>${item}</th>\n")

            }
            append("</tr>\n")


            statistics.items.forEach { item ->
                append("<tr>\n")
                item.detailItem.map { it.value }.forEach { value ->
                    append("<td>$value</td>\n")
                }
                append("</tr>\n")
            }

            append("</table>\n")
            append("</div>\n")
            append("</div>\n")
            append("</div>\n")
        }
    }

    private fun escapeHtml(text: String): String {
        return text
            .replace("&", "&amp;")
            .replace("\"", "&quot;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
    }

    private fun generateIndexHtml(
        stableComposables: String,
        stableComposablesCount: String,
        unstableComposables: String,
        unstableComposablesCount: String,
        stableClasses: String,
        stableClassesCount: String,
        unStableClasses: String,
        unStableClassesCount: String,
        overallStatistics: String,
        detailedStatistics: String,
    ): String {
        val header = """
             <html>
            <head><meta charset="utf-8">
                <meta name="viewport" content="width=device-width, initial-scale=1">
                <title>${toolbarTitle.get()}</title>
                <link rel="stylesheet" href="$CSS_FILE_NAME">
                <script src="$SCRIPTS_FILE_NAME" defer></script>
                <link rel="stylesheet" href="https://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css">
               <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
                 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
            </head>
             <body class="light-mode">
            <div class="toolbar">
            <div class='toolbar-status-container'>
                <span class='status toolbar-status-project'>${projectName.get()}</span>
                <span class='status toolbar-status-variant'>${variantName.get()}</span>
                 </div>
                <div class="toolbar-buttons">
                    <button onclick="toggleDarkMode()">
                        <i class="fas fa-moon"></i>
                    </button>
                </div>
            </div>

            <div class="content">
        """.trimIndent()

        val footer = """
            </div>
           </body>
            </html>
        """.trimIndent()

        val htmlContent = buildString {
            append(header)
            append(
                """
                 <details>
                 <summary class="cardHeader">Non Issue Compose Functions ($stableComposablesCount)</summary>
            """.trimIndent()
            )
            append(stableComposables)
            append("</details>")
            append(
                """
                 <details>
                 <summary class="cardHeader">With Issue Compose Functions ($unstableComposablesCount)</summary>
            """.trimIndent()
            )
            append(unstableComposables)

            append("</details>")
            append(
                """
                  <details>
                    <summary class="cardHeader">Stable Classes ($stableClassesCount)</summary>
            """.trimIndent()
            )
            append(stableClasses)
            append("</details>")

            append("</details>")
            append(
                """
                  <details>
                    <summary class="cardHeader">Unstable Classes ($unStableClassesCount)</summary>
            """.trimIndent()
            )
            append(unStableClasses)
            append("</details>")

            append(
                """
                  <details>
                    <summary class="cardHeader">Statistics</summary>
            """.trimIndent()
            )
            append(overallStatistics)
            append(detailedStatistics)
            append("</details>")
            append(footer)
        }
        return htmlContent
    }

    private fun createOverallHtml(data: Map<String, Long>): String {
        return buildString {
            val id = "overAll"
            val cardColor = "cardGreen"
            val sectionId = "section-class-${id}"

            append("<div class='$cardColor'>\n")
            append("<div class='card-header' onclick=\"toggleSection('$sectionId')\">\n")
            append("<span>Overall</span>\n")
            append("<i id='$sectionId-icon' class='collapse-icon fas fa-chevron-down'></i>\n")
            append("</div>\n")
            append("<div id='$sectionId' class='collapsed-content'>\n")
            append("<div class='table-statistic-container'>\n")
            append("<table>\n")
            append("<tr><th>No.</th><th>Property</th><th>Value</th></tr>\n")

            var index = 1
            for ((key, value) in data) {
                append("<tr><td>${index}</td><td>${key}</td><td>${value}</td></tr>\n")
                index++
            }

            append("</table>\n")
            append("</div>\n")
            append("</div>\n")
            append("</div>\n")
        }
    }

    private fun createComposableFunctionsHtml(
        composables: List<ComposableDetail>,
        id: String
    ): String {

        val htmlContent = buildString {
            composables.forEachIndexed { index, composable ->
                val cardColor =
                    if (composable.isSkippable && composable.isRestartable) "cardGreen" else "cardRed"
                val sectionId = "section-compose-${index + 1}-$id"

                append("<div class='$cardColor'>\n")
                append("<div class='card-header' onclick=\"toggleSection('$sectionId')\">\n")
                append("<span>${index + 1}. ${composable.functionName}</span>\n")
                append("<i id='$sectionId-icon' class='collapse-icon fas fa-chevron-down'></i>\n")
                append("</div>\n")
                append("<div id='$sectionId' class='collapsed-content'>\n")

                val isSkippableColor =
                    if (composable.isSkippable) "restartable-skippable" else "non-restartable-skippable"
                val isRestartableColor =
                    if (composable.isRestartable) "restartable-skippable" else "non-restartable-skippable"

                append("<div class='status-container'>\n")
                append("<span class='status $isSkippableColor'>${if (composable.isSkippable) "Skippable" else "Non Skippable"}</span>\n")
                append("<span class='status $isRestartableColor'>${if (composable.isRestartable) "Restartable" else "Non Restartable"}</span>\n")
                append("</div>\n")

                if (composable.params.isNotEmpty()) {
                    append("<div class='table-container'>\n")
                    append("<table>\n")
                    append("<tr><th>No.</th><th>Stability</th><th>Parameter</th><th>Type</th></tr>\n")
                    composable.params.forEachIndexed { paramIndex, param ->
                        append("<tr>\n")
                        append("<td>${paramIndex + 1}</td>\n")
                        when (param.stability) {
                            Stability.STABLE -> append("<td class='condition-stable'>STABLE</td>\n")
                            Stability.UNSTABLE -> append("<td class='condition-unstable'>UNSTABLE</td>\n")
                            Stability.MISSING -> append("<td class='condition-missing'>MISSING</td>\n")
                        }
                        append("<td>${escapeHtml(param.name)}</td>\n")
                        append("<td>${escapeHtml(param.type)}</td>\n")
                        append("</tr>\n")
                    }
                    append("</table>\n")
                    append("</div>\n")
                }

                append("</div>\n")
                append("</div>\n")
            }
        }

        return htmlContent
    }

    private fun createClassesHtml(classes: List<ClassDetail>, id: String): String {
        val htmlContent = buildString {
            classes.forEachIndexed { index, item ->
                val cardColor =
                    if (item.stability == Stability.STABLE) "cardGreen" else "cardRed"
                val sectionId = "section-class-${index + 1}" + id

                append("<div class='$cardColor'>\n")
                append("<div class='card-header' onclick=\"toggleSection('$sectionId')\">\n")
                append("<span>${index + 1}. ${item.className}</span>\n")
                append("<i id='$sectionId-icon' class='collapse-icon fas fa-chevron-down'></i>\n")
                append("</div>\n")
                append("<div id='$sectionId' class='collapsed-content'>\n")

                val isStable =
                    if (item.stability == Stability.STABLE) "restartable-skippable" else "non-restartable-skippable"
                val isRuntimeStable =
                    if (item.runtimeStability == Stability.STABLE) "restartable-skippable" else "non-restartable-skippable"

                val runtimeStability = when (item.runtimeStability) {
                    Stability.STABLE -> "Runtime Stable"
                    Stability.UNSTABLE -> "Runtime Unstable"
                    Stability.MISSING,
                    null -> "Runtime stability is missing"
                }

                append("<div class='status-container'>\n")
                append("<span class='status $isStable'>${if (item.stability == Stability.STABLE) "Stable" else "UNSTABLE"}</span>\n")
                append("<span class='status $isRuntimeStable'>$runtimeStability</span>\n")
                append("</div>\n")
                if (item.classDetailFields.isNotEmpty()) {
                    append("<div class='table-container'>\n")
                    append("<table>\n")
                    append("<tr><th>No.</th><th>Status</th><th>Field</th><th>Type</th></tr>\n")
                    item.classDetailFields.forEachIndexed { paramIndex, param ->
                        append("<tr>\n")
                        append("<td>${paramIndex + 1}</td>\n")
                        when (Stability.fromValue(param.status)) {
                            Stability.STABLE -> append("<td class='condition-stable'>STABLE</td>\n")
                            Stability.UNSTABLE -> append("<td class='condition-unstable'>UNSTABLE</td>\n")
                            Stability.MISSING -> append("<td class='condition-missing'>MISSING</td>\n")
                        }
                        append("<td>${escapeHtml(param.name)}</td>\n")
                        append("<td>${escapeHtml(param.type)}</td>\n")
                        append("</tr>\n")
                    }
                    append("</table>\n")
                    append("</div>\n")
                }
                append("</div>\n")
                append("</div>\n")
            }
        }

        return htmlContent
    }

    private fun copyFileToOutput(code: String, fileName: String) {
        val dir = project.layout.buildDirectory.asFile.get().absolutePath

        val outputDirectory = File(dir + SLASH + outputPath.get())

        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs()
        }
        val file = File(outputDirectory, fileName)
        if (!file.exists()) {
            file.createNewFile()
        }
        file.writeText(code)
    }
}

