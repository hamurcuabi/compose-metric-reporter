package com.hamurcuabi.compose.metric.reporter.model.classes

import com.hamurcuabi.compose.metric.reporter.model.common.RawContent
import com.hamurcuabi.compose.metric.reporter.model.common.Stability

data class ClassDetail(
    val className: String,
    val stability: Stability,
    val runtimeStability: Stability?,
    val classDetailFields: List<ClassDetailField>,
    val rawContent: RawContent,
)
