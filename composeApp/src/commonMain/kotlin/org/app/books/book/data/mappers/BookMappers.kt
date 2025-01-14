package org.app.books.book.data.mappers

import org.app.books.book.data.dto.SearchedBookDto
import org.app.books.book.domain.model.Book

/**
 * Extension function to convert a [SearchedBookDto] to a [Book].
 *
 * This function maps the properties of a [SearchedBookDto] (Data Transfer Object) to a [Book] (domain model).
 * It handles the conversion of data types and the construction of the image URL based on available cover keys.
 *
 * @receiver The [SearchedBookDto] to convert.
 * @return A [Book] object with the data from the [SearchedBookDto].
 */
fun SearchedBookDto.toBook(): Book {
    // Constructs the image URL based on the available cover keys.
    val imageUrl = if (coverKey != null) {
        "https://covers.openlibrary.org/b/olid/${coverKey}-L.jpg"
    } else {
        "https://covers.openlibrary.org/b/id/${coverAlternativeKey}-L.jpg"
    }

    return Book(
        id = id.substringAfterLast("/"),
        title = title,
        imageUrl = imageUrl,
        authors = authorNames ?: emptyList(),
        description = null,
        languages = languages ?: emptyList(),
        firstPublishYear = firstPublishYear.toString(),
        averageRating = ratingsAverage,
        ratingCount = ratingsCount,
        numPages = numPagesMedian,
        numEditions = numEditions ?: 0
    )
}