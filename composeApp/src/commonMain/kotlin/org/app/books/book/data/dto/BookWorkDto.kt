package org.app.books.book.data.dto

import kotlinx.serialization.Serializable

/**
 * [BookWorkDto] represents the data transfer object for book work information.
 *
 * It contains a description field that can be either a simple string or a JSON object
 * with a value, depending on the response from the external API.
 *
 * @property description The description of the book work, which can be a simple string or a JSON object.
 */
@Serializable(with = BookWorkDtoSerializer::class)
data class BookWorkDto(
    val description: String? = null, // The description of the book work.
)
