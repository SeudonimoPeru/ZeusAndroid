package solis.jhon.pokezeus.domain.utils

fun String.defaultIfEmpty(defaultValue: String): String {
    return if (this.isNotEmpty()) {
        this
    } else {
        defaultValue
    }
}