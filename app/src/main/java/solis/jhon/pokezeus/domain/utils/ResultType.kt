package solis.jhon.pokezeus.domain.utils

sealed class  ResultType<out T> {
    object Loading : ResultType<Nothing>()
    data class Success<out T>(val data: T) : ResultType<T>()
    data class Error(val exception: Exception) : ResultType<Nothing>()
}