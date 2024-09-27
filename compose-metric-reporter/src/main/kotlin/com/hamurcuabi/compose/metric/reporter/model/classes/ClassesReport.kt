package com.hamurcuabi.compose.metric.reporter.model.classes

import com.hamurcuabi.compose.metric.reporter.util.ParsingException
import com.hamurcuabi.compose.metric.reporter.model.common.Stability

data class ClassesReport(
    val classes: List<ClassDetail>,
    val errors: List<ParsingException>
) {
    private val stableAndUnstableClasses by lazy {
        classes.partition { it.stability === Stability.STABLE }
    }

    val stableClasses = stableAndUnstableClasses.first

    val unstableClasses = stableAndUnstableClasses.second
}
