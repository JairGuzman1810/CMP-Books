package org.app.books.book.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * [SearchResponseDto] is a Data Transfer Object (DTO) that represents the response received from a book search API.
 *
 * This class is annotated with [Serializable] to enable serialization and deserialization, typically for network communication.
 * It uses [SerialName] to map the Kotlin property names to the corresponding JSON keys received from the API.
 *
 * @property result A list of [SearchedBookDto] objects, representing the books found in the search.
 *                   Mapped from the JSON key "docs".
 */
@Serializable
data class SearchResponseDto(
    /**
     * A list of [SearchedBookDto] objects, representing the books found in the search.
     *
     * Mapped from the JSON key "docs".
     */
    @SerialName("docs") val result: List<SearchedBookDto>,
)
