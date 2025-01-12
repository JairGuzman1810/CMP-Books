package org.app.books.book.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * [SearchedBookDto] is a Data Transfer Object (DTO) that represents a book returned from a search operation.
 *
 * This class is annotated with [Serializable] to enable serialization and deserialization, typically for network communication.
 * It uses [SerialName] to map the Kotlin property names to the corresponding JSON keys received from the API.
 *
 * @property id The unique identifier of the book. Mapped from the JSON key "key".
 * @property title The title of the book. Mapped from the JSON key "title".
 * @property languages A list of languages the book is available in. Mapped from the JSON key "language". Can be null.
 * @property coverAlternativeKey An alternative key for the book's cover image. Mapped from the JSON key "cover_i". Can be null.
 * @property authorKeys A list of keys identifying the authors of the book. Mapped from the JSON key "author_key". Can be null.
 * @property authorNames A list of names of the authors of the book. Mapped from the JSON key "author_name". Can be null.
 * @property coverKey The key for the book's cover edition. Mapped from the JSON key "cover_edition_key". Can be null.
 * @property firstPublishYear The year the book was first published. Mapped from the JSON key "first_publish_year". Can be null.
 * @property ratingsAverage The average rating of the book. Mapped from the JSON key "ratings_average". Can be null.
 * @property ratingsCount The number of ratings the book has received. Mapped from the JSON key "ratings_count". Can be null.
 * @property numPagesMedian The median number of pages in the book's editions. Mapped from the JSON key "number_of_pages_median". Can be null.
 * @property numEditions The number of editions of the book. Mapped from the JSON key "edition_count". Can be null.
 */
@Serializable
data class SearchedBookDto(
    /**
     * The unique identifier of the book.
     *
     * Mapped from the JSON key "key".
     */
    @SerialName("key") val id: String,
    /**
     * The title of the book.
     *
     * Mapped from the JSON key "title".
     */
    @SerialName("title") val title: String,
    /**
     * A list of languages the book is available in.
     *
     * Mapped from the JSON key "language".
     * Can be null.
     */
    @SerialName("language") val languages: List<String>? = null,
    /**
     * An alternative key for the book's cover image.
     *
     * Mapped from the JSON key "cover_i".
     * Can be null.
     */
    @SerialName("cover_i") val coverAlternativeKey: Int? = null,
    /**
     * A list of keys identifying the authors of the book.
     *
     * Mapped from the JSON key "author_key".
     * Can be null.
     */
    @SerialName("author_key") val authorKeys: List<String>? = null,
    /**
     * A list of names of the authors of the book.
     *
     * Mapped from the JSON key "author_name".
     * Can be null.
     */
    @SerialName("author_name") val authorNames: List<String>? = null,
    /**
     * The key for the book's cover edition.
     *
     * Mapped from the JSON key "cover_edition_key".
     * Can be null.
     */
    @SerialName("cover_edition_key") val coverKey: String? = null,
    /**
     * The year the book was first published.
     *
     * Mapped from the JSON key "first_publish_year".
     * Can be null.
     */
    @SerialName("first_publish_year") val firstPublishYear: Int? = null,
    /**
     * The average rating of the book.
     *
     * Mapped from the JSON key "ratings_average".
     * Can be null.
     */
    @SerialName("ratings_average") val ratingsAverage: Double? = null,
    /**
     * The number of ratings the book has received.
     *
     * Mapped from the JSON key "ratings_count".
     * Can be null.
     */
    @SerialName("ratings_count") val ratingsCount: Int? = null,
    /**
     * The median number of pages in the book's editions.
     *
     * Mapped from the JSON key "number_of_pages_median".
     * Can be null.
     */
    @SerialName("number_of_pages_median") val numPagesMedian: Int? = null,
    /**
     * The number of editions of the book.
     *
     * Mapped from the JSON key "edition_count".
     * Can be null.
     */
    @SerialName("edition_count") val numEditions: Int? = null,
)