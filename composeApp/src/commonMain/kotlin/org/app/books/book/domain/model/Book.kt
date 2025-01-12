package org.app.books.book.domain.model

/**
 * Represents a book entity within the application's domain.
 *
 * This data class encapsulates the core information about a book,
 * independent of any specific data source or presentation layer.
 *
 * @property id The unique identifier of the book.
 * @property title The title of the book.
 * @property imageUrl The URL of the book's cover image.
 * @property authors A list of the book's authors.
 * @property description An optional description of the book's content.
 * @property languages A list of languages in which the book is available.
 * @property firstPublishYear An optional string representing the year the book was first published.
 * @property averageRating An optional average rating of the book, typically out of 5 stars.
 * @property ratingCount An optional number of ratings the book has received.
 * @property numPages An optional number of pages in the book.
 * @property numEditions The total number of editions this book has.
 */
data class Book(
    val id: String,
    val title: String,
    val imageUrl: String,
    val authors: List<String>,
    val description: String?,
    val languages: List<String>,
    val firstPublishYear: String?,
    val averageRating: Double?,
    val ratingCount: Int?,
    val numPages: Int?,
    val numEditions: Int
)