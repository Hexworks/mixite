package org.hexworks.mixite.core.vendor

interface Function<in T, out R> {

    fun apply(param: T): R
}
