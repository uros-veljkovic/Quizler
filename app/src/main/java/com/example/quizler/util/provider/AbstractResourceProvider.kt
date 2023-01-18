package com.example.quizler.util.provider

import android.content.Context
import com.example.quizler.R
import com.example.quizler.util.extensions.capitalizeAndJoinWith
import timber.log.Timber

abstract class AbstractResourceProvider<T>
constructor(
    protected val context: Context,
) {

    protected abstract val resourceTypeLowercase: String
    protected open val prefix: String = ""
    protected open val postfix: String = ""
    protected open val isShouldCapitalizeResourceName: Boolean = false
    protected open val isShouldReplaceSpace: Boolean = false
    protected open val useInsteadOfBlank: String = "_"
    protected abstract val fallbackResourceId: Int
    private val fallbackResourceName: String = "brain"

    protected abstract fun getResource(resId: Int): T?

    fun getResourceByName(resourceName: String) = try {
        val resId = getResId(resourceName)
        getResource(resId)
    } catch (e: Exception) {
        Timber.d(context.getString(R.string.error_log_string, resourceTypeLowercase, resourceName))
        getResource(fallbackResourceId)
    }

    fun getResId(resourceName: String): Int {
        val fullResourceName =
            prefix + capitalizedOrNot(resourceName).replace(" ", useInsteadOfBlank) + postfix
        return try {
            context.resources.getIdentifier(
                fullResourceName,
                resourceTypeLowercase,
                context.packageName
            )
        } catch (e: Exception) {
            Timber.d(
                context.getString(
                    R.string.error_log_string,
                    resourceTypeLowercase,
                    fullResourceName
                )
            )

            context.resources.getIdentifier(
                fallbackResourceName,
                resourceTypeLowercase,
                context.packageName
            )
        }
    }

    private fun capitalizedOrNot(resourceName: String) = when {
        isShouldCapitalizeResourceName -> {
            resourceName.capitalizeAndJoinWith(useInsteadOfBlank)
        }
        else -> {
            resourceName.lowercase()
        }
    }
}
