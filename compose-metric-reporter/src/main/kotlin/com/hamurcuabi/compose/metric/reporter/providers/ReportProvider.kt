package com.hamurcuabi.compose.metric.reporter.providers

import com.hamurcuabi.compose.metric.reporter.model.classes.ClassDetail
import com.hamurcuabi.compose.metric.reporter.model.composables.ComposableDetail
import com.hamurcuabi.compose.metric.reporter.model.details.DetailedStatistics
import com.hamurcuabi.compose.metric.reporter.parser.ClassReportParser
import com.hamurcuabi.compose.metric.reporter.parser.ComposableReportParser
import com.hamurcuabi.compose.metric.reporter.parser.DetailedStatisticsCsvReporter
import com.hamurcuabi.compose.metric.reporter.parser.OverallStatisticsReporter
import kotlin.collections.any
import kotlin.collections.filterNot


class ReportProvider(
    private val contentProvider: ContentProvider,
) {
    fun getOverallStatistics(): Map<String, Long> {
        return OverallStatisticsReporter.parse(contentProvider.briefStatisticsContents)
    }

    fun getDetailedStatistics(): DetailedStatistics {
        return DetailedStatisticsCsvReporter.parse(contentProvider.detailedStatisticsCsvRows)
    }

    fun getStableComposablesReport(
        hideComposableWithNoParams: Boolean,
        excludeSuffix: List<String>
    ): List<ComposableDetail> {
        val list = ComposableReportParser.parse(contentProvider.composablesReportContents)
            .nonIssuesComposables
            .sortedBy { it.isSkippable && it.isRestartable }
            .filterNot { item ->
                excludeSuffix.any { item.functionName.contains(it) }
            }
        return if (hideComposableWithNoParams) {
            list.filter { it.params.isNotEmpty() }
        } else list
    }

    fun getUnstableComposablesReport(
        hideComposableWithNoParams: Boolean,
        excludeSuffix: List<String>
    ): List<ComposableDetail> {
        val list = ComposableReportParser.parse(contentProvider.composablesReportContents)
            .restartableButNotSkippableComposables
            .sortedBy { it.isSkippable && it.isRestartable }
            .filterNot { item ->
                excludeSuffix.any { item.functionName.contains(it) }
            }
        return if (hideComposableWithNoParams) {
            list.filter { it.params.isNotEmpty() }
        } else list
    }

    fun getStableClassesReport(
        excludeSuffix: List<String>
    ): List<ClassDetail> {
        return ClassReportParser.parse(contentProvider.classesReportContents)
            .stableClasses
            .filterNot { item ->
                excludeSuffix.any { item.className.contains(it) }
            }

    }

    fun getUnStableClassesReport(
        excludeSuffix: List<String>
    ): List<ClassDetail> {
        return ClassReportParser.parse(contentProvider.classesReportContents)
            .unstableClasses
            .filterNot { item ->
                excludeSuffix.any { item.className.contains(it) }
            }
    }
}

