package com.hamurcuabi.compose.metric.reporter.util

object Constants {

    object FileSuffixes {
        const val CLASSES_REPORT_TXT = "-classes.txt"
        const val COMPOSABLES_REPORT_TXT = "-composables.txt"
        const val COMPOSABLES_STATS_METRICS_CSV = "-composables.csv"
        const val MODULE_REPORT_JSON = "-module.json"
    }

    const val NEW_LINE = "\n"
    const val COLON = ":"
    const val COMMA = ","
    const val SLASH = "/"

    const val INDEX_HTML = "index.html"

    const val COMPOSE_METRICS_PATH = "compose_metrics"

    const val ANDROID_APPLICATION = "com.android.application"
    const val ANDROID_LIBRARY = "com.android.library"

    const val PLUGIN_EXT_NAME = "composeReporter"

    const val TASK_GROUP = "Compose Metric Reporter"
}