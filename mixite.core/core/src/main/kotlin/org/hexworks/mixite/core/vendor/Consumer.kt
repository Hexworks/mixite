package org.hexworks.mixite.core.vendor

interface Consumer<in T> {

    fun accept(value: T)
}
