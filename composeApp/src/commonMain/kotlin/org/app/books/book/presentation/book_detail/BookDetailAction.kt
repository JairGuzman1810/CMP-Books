package org.app.books.book.presentation.book_detail

import org.app.books.book.domain.model.Book

/**
 * [BookDetailAction] is a sealed interface that defines the possible actions
 * that can be triggered from the BookDetail screen.
 *
 * This interface is used to represent user interactions or events that occur
 * within the BookDetail screen. Each data class within this interface represents
 * a specific action.
 */
sealed interface BookDetailAction {

    /**
     * [OnBackClick] represents the action of clicking the back button.
     *
     * This action typically triggers navigation back to the previous screen.
     */
    data object OnBackClick : BookDetailAction

    /**
     * [OnFavoriteClick] represents the action of clicking the favorite button.
     *
     * This action typically toggles the favorite status of the displayed book.
     */
    data object OnFavoriteClick : BookDetailAction

    /**
     * [OnSelectedBookChange] represents the action of changing the selected book.
     *
     * This action is triggered when a new book is selected to be displayed.
     *
     * @property book The new selected book.
     */
    data class OnSelectedBookChange(val book: Book) : BookDetailAction
}