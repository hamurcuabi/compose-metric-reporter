package com.hamurcuabi.compose.metric.reporter.util

class ParsingException(
    content: String,
    cause: Throwable,
    message: String = "Error occurred while parsing the content",
) : Exception("Message:$message,Content:$content", cause)
