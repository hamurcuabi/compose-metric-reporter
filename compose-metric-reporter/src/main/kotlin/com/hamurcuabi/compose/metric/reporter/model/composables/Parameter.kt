package com.hamurcuabi.compose.metric.reporter.model.composables

import com.hamurcuabi.compose.metric.reporter.model.common.CommonDetail
import com.hamurcuabi.compose.metric.reporter.model.common.Stability
import com.hamurcuabi.compose.metric.reporter.util.Constants

data class Parameter(
    val stability: Stability,
    val details: String
) {

    private val commonDetail by lazy {
        details.split(Constants.COLON).map { it.trim() }.let { (name, type) ->
            CommonDetail(
                name = name,
                type = type
            )
        }
    }

    val name: String
        get() = commonDetail.name

    val type: String
        get() = commonDetail.type
}