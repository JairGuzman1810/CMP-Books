package org.app.books.book.data.database

import androidx.room.RoomDatabase

/**
 * [DatabaseFactory] is an expect class that provides a platform-specific way to create a [RoomDatabase.Builder] for [FavoriteBookDatabase].
 *
 * This class is part of a multiplatform setup, where the actual implementation of database creation
 * may vary depending on the target platform (e.g., Android, iOS).
 *
 * This is the `expect` declaration, meaning that the actual implementation will be provided in the platform-specific code.
 */
expect class DatabaseFactory {
    /**
     * Creates and returns a [RoomDatabase.Builder] for [FavoriteBookDatabase].
     *
     * This function is responsible for creating a builder that can be used to construct the database instance.
     * The actual implementation of this function will vary depending on the target platform.
     *
     * @return A [RoomDatabase.Builder] for [FavoriteBookDatabase].
     */
    fun create(): RoomDatabase.Builder<FavoriteBookDatabase>
}