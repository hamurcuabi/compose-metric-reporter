package com.hamurcuabi.compose.metric.reporter.parser

import com.hamurcuabi.compose.metric.reporter.model.classes.ClassDetail
import com.hamurcuabi.compose.metric.reporter.model.classes.ClassDetailField
import com.hamurcuabi.compose.metric.reporter.model.classes.ClassesReport
import com.hamurcuabi.compose.metric.reporter.model.common.RawContent
import com.hamurcuabi.compose.metric.reporter.model.common.Stability
import com.hamurcuabi.compose.metric.reporter.util.Constants.NEW_LINE
import com.hamurcuabi.compose.metric.reporter.util.ParsingException

object ClassReportParser {
    private val REGEX_RUNTIME_STABILITY = "<runtime stability> = (\\w+)".toRegex()
    private val REGEX_CLASS_NAME = "(stable|unstable|runtime) class (\\w*)".toRegex()
    private val REGEX_CLASS_FIELDS = "((\\w*) ((?:val|var) .*))".toRegex()

    fun parse(content: String): ClassesReport {
        val errors = mutableListOf<ParsingException>()

        val classes =
            getClasses(content)
                .mapNotNull { classBody ->
                    runCatching {
                        parseClassDetail(classBody)
                    }.onFailure { cause ->
                        errors.add(ParsingException(classBody, cause))
                    }.getOrNull()
                }.toList()

        return ClassesReport(classes = classes, errors = errors.toList())
    }

    internal fun getClasses(content: String): List<String> {
        val lines = content
            .split(NEW_LINE)
            .filter { it.isNotBlank() }

        val classIndexes =
            lines.mapIndexedNotNull { index, s ->
                if (REGEX_CLASS_NAME.containsMatchIn(s)) {
                    index
                } else {
                    null
                }
            }

        return classIndexes.mapIndexed { index: Int, item: Int ->
            lines.subList(
                fromIndex = item,
                toIndex = classIndexes
                    .getOrElse(index + 1) { lines.size }
            ).joinToString(separator = NEW_LINE)
        }
    }

    private fun parseClassDetail(classBody: String): ClassDetail {
        val classDetail = REGEX_CLASS_NAME
            .find(classBody)
            ?.groupValues

        val className = classDetail
            ?.getOrNull(2)
            ?: error("Undefined name for the class body: $classBody")

        val stability = classDetail
            .getOrNull(1)
            ?.let { Stability.fromValue(it) }
            ?: error("Undefined stability status for the class body: $classBody")

        val runtimeStability = REGEX_RUNTIME_STABILITY
            .find(classBody)
            ?.groupValues
            ?.getOrNull(1)
            ?.let { Stability.fromValue(it) }

        val fields = REGEX_CLASS_FIELDS
            .findAll(classBody)
            .map { it.groupValues }
            .filter { it.isNotEmpty() }
            .map { ClassDetailField(status = it[2], details = it[3]) }
            .toList()

        return ClassDetail(
            className = className,
            stability = stability,
            runtimeStability = runtimeStability,
            classDetailFields = fields,
            rawContent = RawContent(classBody),
        )
    }
}
