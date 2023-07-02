package com.axuca.app.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Rating(
    @field:Json(name = "rate") val rate: Double? = null,
    @field:Json(name = "count") val count: Int? = null
)
