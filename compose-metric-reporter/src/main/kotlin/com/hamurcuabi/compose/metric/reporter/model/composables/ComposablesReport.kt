package com.hamurcuabi.compose.metric.reporter.model.composables

import com.hamurcuabi.compose.metric.reporter.util.ParsingException

data class ComposablesReport(
    val composables: List<ComposableDetail>,
    val errors: List<ParsingException>,
) {
    private val partitionedComposables by lazy {
        composables.partition { !it.isSkippable && it.isRestartable }
    }

    val restartableButNotSkippableComposables: List<ComposableDetail>
        get() = partitionedComposables.first

    val nonIssuesComposables: List<ComposableDetail>
        get() = partitionedComposables.second
}
