package org.app.books

import android.app.Application
import org.app.books.di.initKoin
import org.koin.android.ext.koin.androidContext

/**
 * [BookApplication] is the main [Application] class for the Android app.
 *
 * This class is responsible for initializing the Koin dependency injection framework
 * when the application starts.
 */
class BookApplication : Application() {

    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     *
     * This is where we initialize Koin and provide the Android context.
     */
    override fun onCreate() {
        super.onCreate()
        // Initializes Koin for dependency injection.
        initKoin {
            // Provides the Android application context to Koin.
            androidContext(this@BookApplication)
        }
    }
}