package org.app.books.book.data.dto

import kotlinx.serialization.Serializable

/**
 * [DescriptionDto] is a data transfer object for a description value.
 *
 * This DTO is used when the description is a JSON object with a 'value' field.
 *
 * @property value The value of the description.
 */
@Serializable
data class DescriptionDto(
    val value: String // The value of the description.
)