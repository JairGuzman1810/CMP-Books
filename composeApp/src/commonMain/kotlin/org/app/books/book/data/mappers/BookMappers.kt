package org.app.books.book.data.mappers

import org.app.books.book.data.database.BookEntity
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

/**
 * Converts a [Book] object to a [BookEntity] object.
 *
 * This function maps the properties of a [Book] (domain model) to a [BookEntity] (database entity).
 * It is used to save a book to the database.
 *
 * @receiver The [Book] to convert.
 * @return A [BookEntity] object with the data from the [Book].
 */
fun Book.toBookEntity(): BookEntity {
    // Create a BookEntity object with the mapped data.
    return BookEntity(
        id = id, // Set the ID.
        title = title, // Set the title.
        description = description, // Set the description.
        imageUrl = imageUrl, // Set the image URL.
        languages = languages, // Set the languages.
        authors = authors, // Set the authors.
        firstPublishYear = firstPublishYear, // Set the first publish year.
        ratingsAverage = averageRating, // Set the average rating.
        ratingsCount = ratingCount, // Set the rating count.
        numPagesMedian = numPages, // Set the number of pages.
        numEditions = numEditions // Set the number of editions.
    )
}

/**
 * Converts a [BookEntity] object to a [Book] object.
 *
 * This function maps the properties of a [BookEntity] (database entity) to a [Book] (domain model).
 * It is used to retrieve a book from the database.
 *
 * @receiver The [BookEntity] to convert.
 * @return A [Book] object with the data from the [BookEntity].
 */
fun BookEntity.toBook(): Book {
    // Create a Book object with the mapped data.
    return Book(
        id = id, // Set the ID.
        title = title, // Set the title.
        description = description, // Set the description.
        imageUrl = imageUrl, // Set the image URL.
        languages = languages, // Set the languages.
        authors = authors, // Set the authors.
        firstPublishYear = firstPublishYear, // Set the first publish year.
        averageRating = ratingsAverage, // Set the average rating.
        ratingCount = ratingsCount, // Set the rating count.
        numPages = numPagesMedian, // Set the number of pages.
        numEditions = numEditions // Set the number of editions.
    )
}