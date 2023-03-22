package com.example.di

import com.example.util.logger.ILogger
import com.example.util.logger.SimpleLogger
import org.koin.dsl.module

val utilModule = module {
    single<ILogger> { SimpleLogger() }
}
