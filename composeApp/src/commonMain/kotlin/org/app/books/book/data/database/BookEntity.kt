package org.app.books.book.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * [BookEntity] represents a book entity stored in the local database.
 *
 * This class is annotated with `@Entity`, indicating that it corresponds to a table in the database.
 * It contains the necessary fields to represent a book, such as its ID, title, description, image URL,
 * languages, authors, and other relevant details.
 *
 * This is part of the **Data Layer** in the application architecture, specifically the **Local Data Source**.
 *
 * **Local Data Source Responsibilities:**
 * - Persisting data locally using Room or other database solutions.
 * - Caching data for offline access.
 * - Providing data to the repository layer.
 *
 * **Key Concepts:**
 * - **Persistence:** Storing data in a local database.
 * - **Caching:** Storing data locally for faster access.
 * - **Data Integrity:** Ensuring data consistency and validity.
 * - **Offline Access:** Providing data even when the device is offline.
 *
 * @property id The unique identifier of the book.
 * @property title The title of the book.
 * @property description The description of the book (can be null).
 * @property imageUrl The URL of the book's cover image.
 * @property languages The list of languages the book is available in.
 * @property authors The list of authors of the book.
 * @property firstPublishYear The year the book was first published (can be null).
 * @property ratingsAverage The average rating of the book (can be null).
 * @property ratingsCount The number of ratings for the book (can be null).
 * @property numPagesMedian The median number of pages for the book (can be null).
 * @property numEditions The number of editions of the book.
 */
@Entity
data class BookEntity(
    @PrimaryKey(autoGenerate = false) val id: String, // The unique identifier of the book.
    val title: String, // The title of the book.
    val description: String?, // The description of the book (can be null).
    val imageUrl: String, // The URL of the book's cover image.
    val languages: List<String>, // The list of languages the book is available in.
    val authors: List<String>, // The list of authors of the book.
    val firstPublishYear: String?, // The year the book was first published (can be null).
    val ratingsAverage: Double?, // The average rating of the book (can be null).
    val ratingsCount: Int?, // The number of ratings for the book (can be null).
    val numPagesMedian: Int?, // The median number of pages for the book (can be null).
    val numEditions: Int // The number of editions of the book.
)