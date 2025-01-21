package org.app.books.book.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * [DatabaseFactory] is an actual class that provides a platform-specific way to create a [RoomDatabase.Builder] for [FavoriteBookDatabase] on Android.
 *
 * This class is the Android implementation of the [DatabaseFactory] expect class.
 * It uses the Android [Context] to create a [RoomDatabase.Builder] for [FavoriteBookDatabase].
 *
 * @property context The Android [Context] used to create the database builder.
 */
actual class DatabaseFactory(
    private val context: Context
) {
    /**
     * Creates and returns a [RoomDatabase.Builder] for [FavoriteBookDatabase].
     *
     * This function uses the Android [Context] to create a [RoomDatabase.Builder] for [FavoriteBookDatabase].
     * It configures the database builder with the necessary parameters.
     *
     * @return A [RoomDatabase.Builder] for [FavoriteBookDatabase].
     */
    actual fun create(): RoomDatabase.Builder<FavoriteBookDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(FavoriteBookDatabase.DB_NAME)

        return Room.databaseBuilder(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}