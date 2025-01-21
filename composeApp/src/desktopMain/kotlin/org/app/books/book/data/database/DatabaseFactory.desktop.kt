package org.app.books.book.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

/**
 * [DatabaseFactory] is an actual class that provides a platform-specific way to create a [RoomDatabase.Builder] for [FavoriteBookDatabase] on desktop platforms.
 *
 * This class is the desktop implementation of the [DatabaseFactory] expect class.
 * It determines the appropriate database file location based on the operating system and creates a [RoomDatabase.Builder] for [FavoriteBookDatabase].
 */
actual class DatabaseFactory {
    /**
     * Creates and returns a [RoomDatabase.Builder] for [FavoriteBookDatabase].
     *
     * This function determines the appropriate database file location based on the operating system
     * and creates a [RoomDatabase.Builder] for [FavoriteBookDatabase] using the Room library.
     *
     * @return A [RoomDatabase.Builder] for [FavoriteBookDatabase].
     */
    actual fun create(): RoomDatabase.Builder<FavoriteBookDatabase> {
        // Get the operating system name.
        val os = System.getProperty("os.name").lowercase()
        // Get the user's home directory.
        val userHome = System.getProperty("user.home")
        // Determine the application data directory based on the operating system.
        val appDataDir = when {
            os.contains("win") -> File(System.getenv("APPDATA"), "Bookpedia")
            os.contains("mac") -> File(userHome, "Library/Application Support/Bookpedia")
            else -> File(userHome, ".local/share/Bookpedia")
        }

        // Create the application data directory if it doesn't exist.
        if (!appDataDir.exists()) {
            appDataDir.mkdirs()
        }

        // Create the database file in the application data directory.
        val dbFile = File(appDataDir, FavoriteBookDatabase.DB_NAME)
        // Create and return a RoomDatabase.Builder for FavoriteBookDatabase.
        return Room.databaseBuilder(dbFile.absolutePath)
    }
}