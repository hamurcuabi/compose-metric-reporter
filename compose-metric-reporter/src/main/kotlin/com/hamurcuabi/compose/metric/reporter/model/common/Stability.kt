package com.hamurcuabi.compose.metric.reporter.model.common

enum class Stability(val text: String) {
    STABLE("stable"),
    UNSTABLE("unstable"),
    MISSING("missing");

    companion object {

        fun fromValue(value: String) = values()
            .firstOrNull { value == it.text }
            ?: MISSING
    }
}
