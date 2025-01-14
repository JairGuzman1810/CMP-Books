package org.app.books.app

import kotlinx.serialization.Serializable

/**
 * [Route] is a sealed interface that defines the navigation routes for the application.
 *
 * Each data class within this interface represents a specific screen or a group of screens
 * in the app's navigation graph.
 */
sealed interface Route {

    /**
     * [BookGraph] represents the navigation graph for all book-related screens.
     *
     * This is used to group the [BookList] and [BookDetail] routes together.
     */
    @Serializable
    data object BookGraph : Route

    /**
     * [BookList] represents the screen that displays the list of books.
     *
     * This is the main screen for browsing books.
     */
    @Serializable
    data object BookList : Route

    /**
     * [BookDetail] represents the screen that displays the details of a specific book.
     *
     * @property id The unique identifier of the book to display.
     */
    @Serializable
    data class BookDetail(val id: String) : Route
}