package com.example.domain.wrapper

import android.content.Context

class ContextWrapper(private val context: Context) : IContextWrapper {
    override fun getString(id: Int, vararg formatArgs: Any): String {
        return context.getString(id, formatArgs)
    }

    override fun getResources(): IContextWrapper.ResourcesWrapper {
        return object : IContextWrapper.ResourcesWrapper {
            override fun getIdentifier(name: String, defType: String, defPackage: String): Int {
                return context.resources.getIdentifier(name, defType, defPackage)
            }
        }
    }

    override fun getPackageName(): String {
        return context.packageName
    }
}
