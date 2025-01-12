package org.app.books.core.domain

/**
 * [DataError] is a sealed interface that represents errors that can occur when accessing data.
 *
 * This interface is designed to provide a structured way to handle different types of data access errors.
 * It is a sealed interface, meaning that all its subclasses are known at compile time,
 * which allows for exhaustive `when` expressions when handling errors.
 *
 * [DataError] is a subtype of [Error], indicating that it represents an error condition.
 */
sealed interface DataError : Error {
    /**
     * [Remote] is an enum class that represents errors that can occur when accessing remote data sources.
     *
     * This enum defines specific types of errors that can happen during network requests or communication with a remote server.
     */
    enum class Remote : DataError {
        /**
         * [REQUEST_TIMEOUT] indicates that a request to a remote server timed out.
         */
        REQUEST_TIMEOUT,

        /**
         * [TOO_MANY_REQUESTS] indicates that too many requests were made to a remote server in a given time frame.
         */
        TOO_MANY_REQUESTS,

        /**
         * [NO_INTERNET] indicates that there is no internet connection available.
         */
        NO_INTERNET,

        /**
         * [SERVER] indicates that there was a general server error.
         */
        SERVER,

        /**
         * [SERIALIZATION] indicates that there was an error during serialization or deserialization of data.
         */
        SERIALIZATION,

        /**
         * [UNKNOWN] indicates that an unknown error occurred when accessing remote data.
         */
        UNKNOWN
    }

    /**
     * [Local] is an enum class that represents errors that can occur when accessing local data sources.
     *
     * This enum defines specific types of errors that can happen when interacting with local storage, such as a database or file system.
     */
    enum class Local : DataError {
        /**
         * [DISK_FULL] indicates that the local storage disk is full.
         */
        DISK_FULL,

        /**
         * [UNKNOWN] indicates that an unknown error occurred when accessing local data.
         */
        UNKNOWN
    }
}