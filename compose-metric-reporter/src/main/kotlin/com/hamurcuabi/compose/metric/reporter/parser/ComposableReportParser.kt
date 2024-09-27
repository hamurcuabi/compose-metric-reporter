package com.hamurcuabi.compose.metric.reporter.parser

import com.hamurcuabi.compose.metric.reporter.model.common.RawContent
import com.hamurcuabi.compose.metric.reporter.model.common.Stability
import com.hamurcuabi.compose.metric.reporter.model.composables.ComposableDetail
import com.hamurcuabi.compose.metric.reporter.model.composables.ComposablesReport
import com.hamurcuabi.compose.metric.reporter.model.composables.Parameter
import com.hamurcuabi.compose.metric.reporter.util.ParsingException


object ComposableReportParser {
    private const val INLINE = "inline"
    private const val RESTARTABLE = "restartable"
    private const val SKIPPABLE = "skippable"

    private val REGEX_COMPOSABLE_FUNCTION = "(.*)fun (\\w*)".toRegex()
    private val REGEX_COMPOSABLE_PARAMETERS = "(stable|unstable|\\w+) (\\w*:\\s.*)".toRegex()

    fun parse(content: String): ComposablesReport {
        val errors = mutableListOf<ParsingException>()

        val composables =
            getComposableFunctions(content)
                .mapNotNull { function ->
                    runCatching {
                        parseComposableDetail(function)
                    }.onFailure { cause ->
                        errors.add(ParsingException(function, cause))
                    }.getOrNull()
                }
                .toList()

        return ComposablesReport(composables, errors.toList())
    }

    internal fun getComposableFunctions(content: String): List<String> {
        val lines = content.split("\n").filter { it.isNotBlank() }

        val composableFunIndexes =
            lines.mapIndexedNotNull { index, s ->
                if (REGEX_COMPOSABLE_FUNCTION.containsMatchIn(s)) {
                    index
                } else {
                    null
                }
            }

        return composableFunIndexes.mapIndexed { index: Int, item: Int ->
            lines.subList(item, composableFunIndexes.getOrElse(index + 1) { lines.size })
                .joinToString(separator = "\n")
        }
    }

    private fun parseComposableDetail(function: String): ComposableDetail {
        val composableDetails = REGEX_COMPOSABLE_FUNCTION.find(function)?.groupValues!!

        val functionDetail = composableDetails[0]
        val functionName = composableDetails[2]

        val isInline = functionDetail.contains(INLINE)
        val isRestartable = functionDetail.contains(RESTARTABLE)
        val isSkippable = functionDetail.contains(SKIPPABLE)

        val params =
            REGEX_COMPOSABLE_PARAMETERS.findAll(function).map { it.groupValues }
                .filter { it.isNotEmpty() }
                .map {
                    Parameter(
                        stability = Stability.fromValue(it[1]),
                        details = it[2]
                    )
                }.toList()

        return ComposableDetail(
            functionName = functionName,
            isRestartable = isRestartable,
            isSkippable = isSkippable,
            isInline = isInline,
            params = params,
            rawContent = RawContent(function),
        )
    }
}
