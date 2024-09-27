package com.hamurcuabi.compose.metric.reporter.model.classes

import com.hamurcuabi.compose.metric.reporter.model.common.CommonDetail
import com.hamurcuabi.compose.metric.reporter.util.Constants

data class ClassDetailField(
    val status: String,
    val details: String
) {

    private val commonDetail by lazy {
        details.split(Constants.COLON)
            .map { it.trim() }
            .let { (name, type) ->
                CommonDetail(name, type)
            }
    }

    val name: String
        get() = commonDetail.name

    val type: String
        get() = commonDetail.type
}