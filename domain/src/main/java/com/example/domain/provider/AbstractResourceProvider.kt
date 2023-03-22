package com.example.domain.provider

import com.example.domain.wrapper.IContextWrapper
import com.example.util.extension.capitalizeAndJoinWith
import timber.log.Timber

abstract class AbstractResourceProvider<T> constructor(
    protected val context: IContextWrapper,
) {

    protected abstract val resourceTypeLowercase: String
    protected open val prefix: String = ""
    protected open val postfix: String = ""
    protected open val isShouldCapitalizeResourceName: Boolean = false
    protected open val isShouldReplaceSpace: Boolean = false
    protected open val useInsteadOfBlank: String = "_"
    private val fallbackResourceName: String = "brain"

    protected abstract fun getResource(resId: Int): T

    fun getResourceByName(resourceName: String): T = try {
        val resId = getResId(resourceName)
        getResource(resId)
    } catch (e: Exception) {
        Timber.e(e)
        getResource(getResId(fallbackResourceName))
    }

    fun getResId(resourceName: String): Int {
        val fullResourceName = prefix + capitalizedOrNot(resourceName).replace(" ", useInsteadOfBlank) + postfix
        return try {
            context.getResources().getIdentifier(
                fullResourceName, resourceTypeLowercase, context.getPackageName()
            )
        } catch (e: Exception) {
            Timber.e(e)

            context.getResources().getIdentifier(
                fallbackResourceName, resourceTypeLowercase, context.getPackageName()
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
