package com.example.util.logger

import timber.log.Timber

interface ILogger {
    fun verbose(message: String)
    fun info(message: String)
    fun debug(message: String)
    fun warning(message: String)
    fun warning(throwable: Throwable)
    fun error(message: String)
    fun error(throwable: Throwable)
}

class SimpleLogger : ILogger {

    override fun verbose(message: String) {
        Timber.v(message)
    }

    override fun info(message: String) {
        Timber.i(message)
    }

    override fun debug(message: String) {
        Timber.d(message)
    }

    override fun warning(message: String) {
        Timber.w(message)
    }

    override fun warning(throwable: Throwable) {
        Timber.w(throwable)
    }

    override fun error(message: String) {
        Timber.e(message)
    }

    override fun error(throwable: Throwable) {
        Timber.e(throwable)
    }
}