package org.app.books.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.app.books.book.data.database.DatabaseFactory
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * [platformModule] is a Koin [Module] that provides platform-specific dependencies for iOS.
 *
 * This module defines the dependencies that are specific to the iOS platform.
 * It includes the setup for the [HttpClientEngine] using the Darwin engine.
 */
actual val platformModule: Module
    get() = module {
        /**
         * Provides a singleton instance of [HttpClientEngine] using the Darwin engine.
         *
         * This instance is used by the Ktor HTTP client for making network requests on iOS.
         * The Darwin engine is the recommended engine for Ktor on iOS.
         */
        single<HttpClientEngine> {
            Darwin.create()
        }

        /**
         * Provides a singleton instance of [DatabaseFactory] for iOS.
         *
         * This function creates a [DatabaseFactory] instance.
         */
        single { DatabaseFactory() } // Creates a DatabaseFactory instance.
    }