package com.hamurcuabi.compose.metric.reporter.parser

import kotlinx.serialization.json.Json

object OverallStatisticsReporter {
    private val REGEX_CAMEL_CASE = "(\\A[a-z]|[A-Z])".toRegex()

    fun parse(briefStatisticsContents: List<String>): Map<String, Long> {
        val statistics = mutableMapOf<String, Long>()
        briefStatisticsContents.forEach { statContent ->
            val stats = Json.Default.decodeFromString<Map<String, Long>>(statContent)
            if (statistics.isEmpty()) {
                statistics.putAll(stats)
            } else {
                stats.forEach { (key, value) ->
                    statistics[key] = statistics[key]?.plus(value) ?: value
                }
            }
        }
        return statistics.mapKeys { camelCaseToWord(it.key) }
    }

    fun camelCaseToWord(content: String): String =
        content.replace(REGEX_CAMEL_CASE) { " ${it.value[0].uppercase()}" }.trim()
}
