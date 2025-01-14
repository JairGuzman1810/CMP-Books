package org.app.books.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * [platformModule] is a Koin [Module] that provides platform-specific dependencies for Desktop.
 *
 * This module defines the dependencies that are specific to the Desktop platform.
 * It includes the setup for the [HttpClientEngine] using OkHttp.
 */
actual val platformModule: Module
    get() = module {
        /**
         * Provides a singleton instance of [HttpClientEngine] using OkHttp.
         *
         * This instance is used by the Ktor HTTP client for making network requests on Desktop.
         * OkHttp is a popular and efficient HTTP client for JVM-based applications.
         */
        single<HttpClientEngine> {
            OkHttp.create()
        }
    }