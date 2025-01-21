package org.app.books.book.data.database

import androidx.room.RoomDatabaseConstructor

/**
 * [BookDatabaseConstructor] is an expect object that provides a platform-specific way to construct the [FavoriteBookDatabase].
 *
 * This object is part of a multiplatform setup, where the actual implementation of database construction
 * may vary depending on the target platform (e.g., Android, iOS).
 *
 * @see FavoriteBookDatabase
 */
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object BookDatabaseConstructor : RoomDatabaseConstructor<FavoriteBookDatabase> {
    /**
     * Initializes and returns an instance of [FavoriteBookDatabase].
     *
     * This function is responsible for creating and configuring the database instance.
     * The actual implementation of this function will vary depending on the target platform.
     *
     * @return An instance of [FavoriteBookDatabase].
     */
    override fun initialize(): FavoriteBookDatabase
}