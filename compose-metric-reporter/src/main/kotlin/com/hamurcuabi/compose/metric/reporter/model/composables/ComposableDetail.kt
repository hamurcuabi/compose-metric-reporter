package com.hamurcuabi.compose.metric.reporter.model.composables

import com.hamurcuabi.compose.metric.reporter.model.common.RawContent

data class ComposableDetail(
    val functionName: String,
    val isRestartable: Boolean,
    val isSkippable: Boolean,
    val isInline: Boolean,
    val params: List<Parameter>,
    val rawContent: RawContent,
)

