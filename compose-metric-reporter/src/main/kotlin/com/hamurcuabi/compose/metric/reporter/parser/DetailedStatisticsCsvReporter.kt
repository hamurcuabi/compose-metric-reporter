package com.hamurcuabi.compose.metric.reporter.parser

import com.hamurcuabi.compose.metric.reporter.model.details.DetailItem
import com.hamurcuabi.compose.metric.reporter.model.details.DetailRowItems
import com.hamurcuabi.compose.metric.reporter.model.details.DetailedStatistics
import com.hamurcuabi.compose.metric.reporter.util.Constants.COMMA

object DetailedStatisticsCsvReporter {

    fun parse(detailedStatisticsCsvRows: List<String>): DetailedStatistics {
        val metrics =
            if (detailedStatisticsCsvRows.size > 1) {
                val headers = splitWithCsvSeparator(detailedStatisticsCsvRows.first())

                detailedStatisticsCsvRows.subList(1, detailedStatisticsCsvRows.size)
                    .map { splitWithCsvSeparator(it) }
                    .map { items ->
                        DetailRowItems(
                            items.mapIndexed { index, value ->
                                DetailItem(
                                    headers[index],
                                    value
                                )
                            }
                        )
                    }
            } else {
                emptyList()
            }

        return DetailedStatistics(metrics)
    }

    private fun splitWithCsvSeparator(content: String) =
        content.split(COMMA).filter { it.isNotBlank() }
}
