package com.example.quizler.util.mapper

interface DataMapper<I, O> {
    fun map(input: I): O
    fun map(input: List<I>): List<O> = input.map { map(it) }
}
