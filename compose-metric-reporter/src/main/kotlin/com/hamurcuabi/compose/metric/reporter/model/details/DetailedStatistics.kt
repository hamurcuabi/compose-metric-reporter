package com.hamurcuabi.compose.metric.reporter.model.details

data class DetailedStatistics(val items: List<DetailRowItems>) {
    val headers: List<String> get() = items.first().detailItem.map { it.name }
}

