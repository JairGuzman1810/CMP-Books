package org.app.books.book.data.database

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

/**
 * [StringListTypeConverter] is a type converter for lists of strings.
 *
 * This object provides methods to convert between a list of strings and a single string,
 * using JSON serialization and deserialization.
 *
 */
object StringListTypeConverter {

    /**
     * Converts a string to a list of strings.
     *
     * This function uses JSON deserialization to convert a string to a list of strings.
     *
     * @param value The string to convert.
     * @return A list of strings.
     */
    @TypeConverter
    fun fromString(value: String): List<String> {
        // Use Json to decode the string to a list of strings.
        return Json.decodeFromString(value)
    }

    /**
     * Converts a list of strings to a string.
     *
     * This function uses JSON serialization to convert a list of strings to a string.
     *
     * @param list The list of strings to convert.
     * @return A string.
     */
    @TypeConverter
    fun fromList(list: List<String>): String {
        // Use Json to encode the list of strings to a string.
        return Json.encodeToString(list)
    }
}