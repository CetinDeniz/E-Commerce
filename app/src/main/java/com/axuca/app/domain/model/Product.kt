package com.axuca.app.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Product(
    @field:Json(name = "id") val id: Int? = null,
    @field:Json(name = "title") val title: String? = null,
    @field:Json(name = "price") val price: Double? = null,
    @field:Json(name = "description") val description: String? = null,
    @field:Json(name = "category") val category: String? = null,
    @field:Json(name = "image") val image: String? = null,
    @field:Json(name = "rating") val rating: Rating? = null
)
