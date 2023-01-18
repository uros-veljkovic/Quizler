package com.example.quizler.util.mapper

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface DataMapper<I, O> {
    fun map(input: I): O
    fun map(input: List<I>): List<O> = input.map { map(it) }
    fun map(input: Flow<List<I>>): Flow<List<O>> = input.map { map(it) }
}
