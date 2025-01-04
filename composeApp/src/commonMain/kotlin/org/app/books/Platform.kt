package org.app.books

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform