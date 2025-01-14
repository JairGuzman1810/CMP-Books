package org.app.books.di

import org.app.books.book.data.network.KtorRemoteBookDataSource
import org.app.books.book.data.network.RemoteBookDataSource
import org.app.books.book.data.repository.BookRepositoryImpl
import org.app.books.book.domain.repository.BookRepository
import org.app.books.book.presentation.SelectedBookViewModel
import org.app.books.book.presentation.book_list.BookListViewModel
import org.app.books.core.data.HttpClientFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * [platformModule] is an expected property that provides platform-specific dependencies.
 *
 * This is expected to be implemented in each platform-specific module (e.g., Android, iOS).
 */
expect val platformModule: Module

/**
 * [sharedModule] is a Koin [Module] that provides shared dependencies for the application.
 *
 * This module defines the dependencies that are common across all platforms.
 * It includes the setup for the HTTP client, data sources, repositories, and ViewModels.
 */
val sharedModule = module {

    /**
     * Provides a singleton instance of HttpClient created by [HttpClientFactory].
     *
     * This instance is used for making network requests.
     */
    single {
        HttpClientFactory.create(get())
    }

    /**
     * Provides a singleton instance of [KtorRemoteBookDataSource] as a [RemoteBookDataSource].
     *
     * This binds the concrete implementation to the interface, allowing for dependency injection.
     */
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()

    /**
     * Provides a singleton instance of [BookRepositoryImpl] as a [BookRepository].
     *
     * This binds the concrete implementation to the interface, allowing for dependency injection.
     */
    singleOf(::BookRepositoryImpl).bind<BookRepository>()

    /**
     * Provides a [BookListViewModel] instance for each request.
     *
     * This uses the `viewModelOf` function to create a ViewModel instance.
     */
    viewModelOf(::BookListViewModel)

    viewModelOf(::SelectedBookViewModel)
}