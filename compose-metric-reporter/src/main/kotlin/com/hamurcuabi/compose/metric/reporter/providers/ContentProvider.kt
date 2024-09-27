package com.hamurcuabi.compose.metric.reporter.providers

import com.hamurcuabi.compose.metric.reporter.util.Constants
import java.io.File

class ContentProvider(
    directory: File,
    private val variantName: String
) {

    val briefStatisticsContents: List<String>
        get() = findBriefStatisticsJsonFile()
            .map { it.readText() }

    val detailedStatisticsCsvRows: List<String>
        get() = findDetailsStatisticsCsvFile()
            .flatMap { it.readLines() }

    val composablesReportContents: String
        get() = findComposablesReportTxtFile()
            .joinToString(separator = Constants.NEW_LINE) { it.readText() }

    val classesReportContents: String
        get() = findClassesReportTxtFile()
            .joinToString(separator = Constants.NEW_LINE) { it.readText() }

    private val allFiles =
        directory
            .listFiles()
            ?.filterNotNull()
            ?: emptyList()

    private fun findBriefStatisticsJsonFile(): List<File> {
        return allFiles.filter { it.name.endsWith(variantName + Constants.FileSuffixes.MODULE_REPORT_JSON) }
    }

    private fun findDetailsStatisticsCsvFile(): List<File> {
        return allFiles.filter { it.name.endsWith(variantName + Constants.FileSuffixes.COMPOSABLES_STATS_METRICS_CSV) }
    }

    private fun findComposablesReportTxtFile(): List<File> {
        return allFiles.filter { it.name.endsWith(variantName + Constants.FileSuffixes.COMPOSABLES_REPORT_TXT) }
    }

    private fun findClassesReportTxtFile(): List<File> {
        return allFiles.filter { it.name.endsWith(variantName + Constants.FileSuffixes.CLASSES_REPORT_TXT) }
    }
}
