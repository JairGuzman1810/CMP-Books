package org.app.books.book.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * [FavoriteBookDatabase] is a Room database that manages the storage of favorite books.
 *
 * This class defines the database configuration, including the entities, version, and type converters.
 * It provides an abstract method to get the [FavoriteBookDao], which is used to interact with the database.
 *
 */
@Database(
    entities = [BookEntity::class], // Specifies the entities (tables) in the database.
    version = 1 // Specifies the database version.
)
@TypeConverters(
    StringListTypeConverter::class // Specifies the type converters to use.
)
abstract class FavoriteBookDatabase : RoomDatabase() {
    // Abstract method to get the FavoriteBookDao.
    abstract val favoriteBookDao: FavoriteBookDao

    companion object {
        const val DB_NAME = "book.db" // The name of the database file.
    }
}