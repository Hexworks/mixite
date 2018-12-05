package org.hexworks.mixite.core.vendor

interface Supplier<out T> {

    fun get(): T
}
