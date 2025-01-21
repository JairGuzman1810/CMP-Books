package org.app.books.book.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

/**
 * [FavoriteBookDao] is a Data Access Object (DAO) interface for managing favorite books in the local database.
 *
 * This interface defines the methods for interacting with the `BookEntity` table, allowing for
 * inserting, querying, and deleting favorite books.
 *
 * This class handles the database communication with the Room API and uses [Upsert], [Query] to safely execute
 * database requests and handle potential exceptions.
 *
 * @see BookEntity
 */
@Dao
interface FavoriteBookDao {

    /**
     * Inserts or updates a [BookEntity] in the database.
     *
     * This function uses the `@Upsert` annotation, which means it will insert a new book if it doesn't exist,
     * or update an existing book if it does.
     *
     * @param book The [BookEntity] to insert or update.
     */
    @Upsert
    suspend fun upsert(book: BookEntity) // Inserts or updates a BookEntity in the database.

    /**
     * Retrieves all favorite books from the database.
     *
     * This function uses a `@Query` annotation to specify the SQL query to execute.
     * It returns a `Flow` of a list of [BookEntity] objects, which allows for reactive updates
     * to the list of favorite books.
     *
     * @return A `Flow` of a list of [BookEntity] objects.
     */
    @Query("SELECT * FROM BookEntity")
    fun getFavoriteBooks(): Flow<List<BookEntity>> // Retrieves all favorite books from the database.

    /**
     * Retrieves a specific favorite book from the database by its ID.
     *
     * This function uses a `@Query` annotation to specify the SQL query to execute.
     * It returns a [BookEntity] object if a book with the given ID exists, or null if no such book exists.
     *
     * @param id The ID of the book to retrieve.
     * @return A [BookEntity] object or null if no such book exists.
     */
    @Query("SELECT * FROM BookEntity WHERE id = :id")
    suspend fun getFavoriteBook(id: String): BookEntity? // Retrieves a specific favorite book from the database by its ID.

    /**
     * Deletes a favorite book from the database by its ID.
     *
     * This function uses a `@Query` annotation to specify the SQL query to execute.
     * It deletes the book with the given ID from the database.
     *
     * @param id The ID of the book to delete.
     */
    @Query("DELETE FROM BookEntity WHERE id = :id")
    suspend fun deleteFavoriteBook(id: String) // Deletes a favorite book from the database by its ID.
}