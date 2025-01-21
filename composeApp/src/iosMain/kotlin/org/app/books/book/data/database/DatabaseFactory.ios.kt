@file:OptIn(ExperimentalForeignApi::class)

package org.app.books.book.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSFileManager
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSUserDomainMask

/**
 * [DatabaseFactory] is an actual class that provides a platform-specific way to create a [RoomDatabase.Builder] for [FavoriteBookDatabase] on iOS.
 *
 * This class is the iOS implementation of the [DatabaseFactory] expect class.
 * It uses iOS APIs to determine the document directory and creates a [RoomDatabase.Builder] for [FavoriteBookDatabase].
 */
actual class DatabaseFactory {
    /**
     * Creates and returns a [RoomDatabase.Builder] for [FavoriteBookDatabase].
     *
     * This function determines the document directory on iOS and creates a [RoomDatabase.Builder] for [FavoriteBookDatabase] using the Room library.
     *
     * @return A [RoomDatabase.Builder] for [FavoriteBookDatabase].
     */
    actual fun create(): RoomDatabase.Builder<FavoriteBookDatabase> {
        // Get the path to the document directory and append the database name.
        val dbFile = documentDirectory() + "/${FavoriteBookDatabase.DB_NAME}"
        // Create and return a RoomDatabase.Builder for FavoriteBookDatabase.
        return Room.databaseBuilder<FavoriteBookDatabase>(
            name = dbFile,
        )
    }

    /**
     * Returns the path to the document directory on iOS.
     *
     * This function uses the `NSFileManager` to retrieve the URL for the document directory and extracts the path from it.
     *
     * @return The path to the document directory.
     */
    private fun documentDirectory(): String {
        // Get the URL for the document directory using NSFileManager.
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )
        // Extract and return the path from the URL.
        return requireNotNull(documentDirectory?.path.orEmpty())
    }
}