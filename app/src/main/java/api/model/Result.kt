package api.model

sealed class Result<T> {
    abstract fun getOrThrowException(): T
    abstract fun getOrNull(): T?
    abstract fun httpStatus(): Int

    data class Success<T>(val httpStatus: Int, val result: T) : Result<T>() {
        override fun getOrThrowException(): T = result
        override fun getOrNull(): T = result
        override fun httpStatus(): Int = httpStatus
    }

    data class Failure<T>(val httpStatus: Int, val error: String) : Result<T>() {
        override fun getOrThrowException(): T = throw UnsupportedOperationException()
        override fun getOrNull(): T? = null
        override fun httpStatus(): Int = httpStatus
    }
}