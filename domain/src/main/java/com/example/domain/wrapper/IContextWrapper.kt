package com.example.domain.wrapper

interface IContextWrapper {

    fun getString(id: Int, vararg formatArgs: Any): String

    fun getResources(): ResourcesWrapper

    fun getPackageName(): String

    interface ResourcesWrapper {
        fun getIdentifier(name: String, defType: String, defPackage: String): Int
    }
}
