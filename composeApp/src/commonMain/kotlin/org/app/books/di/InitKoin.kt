package org.app.books.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

/**
 * Initializes Koin for dependency injection.
 *
 * This function starts the Koin framework and configures it with the provided modules.
 * It allows for an optional configuration block to be passed, which can be used to
 * customize the Koin setup.
 *
 * @param config An optional [KoinAppDeclaration] lambda for configuring Koin.
 */
fun initKoin(config: KoinAppDeclaration? = null) {
    // Starts the Koin framework.
    startKoin {
        // Invokes the optional configuration block if provided.
        config?.invoke(this)
        // Loads the platform-specific and shared modules.
        modules(platformModule, sharedModule)
    }
}