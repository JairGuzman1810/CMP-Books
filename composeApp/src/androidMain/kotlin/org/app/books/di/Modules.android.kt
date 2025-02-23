package org.app.books.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.app.books.book.data.database.DatabaseFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * [platformModule] is a Koin [Module] that provides platform-specific dependencies for Android.
 *
 * This module defines the dependencies that are specific to the Android platform.
 * It includes the setup for the [HttpClientEngine] using OkHttp.
 */
actual val platformModule: Module
    get() = module {
        /**
         * Provides a singleton instance of [HttpClientEngine] using OkHttp.
         *
         * This instance is used by the Ktor HTTP client for making network requests on Android.
         * OkHttp is a popular and efficient HTTP client for Android.
         */
        single<HttpClientEngine> {
            OkHttp.create()
        }

        /**
         * Provides a singleton instance of [DatabaseFactory] for Android.
         *
         * This function creates a [DatabaseFactory] instance using the Android application context.
         * The application context is retrieved using `androidApplication()`.
         */
        single { DatabaseFactory(androidApplication()) } // Creates a DatabaseFactory instance with the Android application context.
    }