package com.basket.core.common.result

import com.basket.core.common.result.Result.Success
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * ```
 * val result = Result.Success("Hello World!")
 * val error = Result.Failure(ErrorResult("Some error text"))
 * val error2 = Result.Failure("Some error text")
 * ```
 *
 * @author vsouhrada
 * @since 1.0.0
 */
sealed class Result<out T, out E> {
    data class Success<T>(val data: T) : Result<T, Nothing>() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Result<*, *>) return false
            return if (other is Success<*>) {
                this.data == other.data
            } else {
                false
            }
        }
    }

    data class Failure<out E>(val error: E) : Result<Nothing, E>() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Result<*, *>) return false
            return if (other is Failure<*>) {
                this.error == other.error
            } else {
                false
            }
        }
    }

    companion object {
        fun <T> success(data: T): Result<T, Nothing> = Success(data = data)

        fun <E> failure(error: E): Result<Nothing, E> = Failure(error)

        /* fun <E> failure(
             errorResult: ErrorResult<E>,
             message: String? = null,
             throwable: Throwable? = null
         ): Result<Nothing, E> =
             Failure(ErrorResult(message = message, throwable = throwable), data)*/
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Result<*, *>) return false
        return false
    }

    override fun hashCode(): Int {
        return this::class.hashCode()
    }

    /*override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is InRideOnlineFilter) return false

        if (filterType != other.filterType) return false

        return true
    }*/

    override fun toString(): String {
        return when (this) {
            is Success -> "Success[data=$data]"
            is Failure -> "Failure[$error]"
        }
    }

    fun isFinished() = this is Success || this is Failure

    fun isSuccess() = this is Success

    fun isFailure() = this is Failure

    /**
     * Returns the encapsulated value if this instance represents [Success] or null is returned
     * @since 0.2.0
     */
    fun getOrNull() = if (this is Success) data else null

    /**
     * Returns the encapsulated value if this instance represents [Failure] or null is returned
     * @since 0.7.0
     */
    fun getFailureOrNull() = if (this is Failure) error else null

    /**
     * Runs a [block] function only when current [Result] is successful. Returns this to allow chaining of additional
     * functions.
     *
     * @param block of code to run on success
     * @return [Result] with [T] which the same as call [Result]
     * @since 0.2.0
     */
    @OptIn(ExperimentalContracts::class)
    inline fun onSuccess(block: (T) -> Unit): Result<T, E> {
        contract {
            callsInPlace(block, InvocationKind.EXACTLY_ONCE)
        }

        if (this is Success) {
            block(data)
        }
        return this
    }

    /**
     * @since 0.2.0
     */
    inline fun <S, R> onSuccessWithReturn(block: (T) -> Result<S, R>): Result<S, R> {
        return when (this) {
            is Success<T> -> {
                block(this.data)
            }
            is Failure<E> -> {
                this as Result<S, R>
            }
        }
    }

    /**
     * Runs a [block] function only when current [Result] fails with an error. Returns this to allow chaining of additional
     * functions.
     *
     * @param block of code to run on error
     * @return [Result] which the same as call [Result]
     * @since 0.5.0
     */
    inline fun onFailure(block: (E) -> Unit): Result<T, E> {
        if (this is Failure<E>) {
            block(this.error)
        }
        return this
    }

    /**
     * Runs a [block] function after the result finishes. Returns this to allow chaining of additional functions.
     *
     * @param block of code to run
     * @return [Result] with [T] which the same as call [Result]
     * @since 0.2.0
     */
    inline fun onFinished(block: (Result<T, E>) -> Unit): Result<T, E> {
        block(this)
        return this
    }

    /**
     * Applies `onSuccess` if this is a [Result.Success] or `onFailure` if this is a [Result.Failure].
     *
     * Example:
     * ```kotlin
     * import com.basket.core.common.result.*
     *
     * fun main() {
     *   fun possiblyFailingOperation(): Result.Failure<Int> = Result.Failure(1)
     *   //sampleStart
     *   val result: Result<String, Int> = possiblyFailingOperation()
     *   result.fold(
     *        { println("operation succeeded with $it") },
     *        { println("operation failed with $it") }
     *   )
     *   //sampleEnd
     * }
     * ```
     * <!--- KNIT example-result-01.kt -->
     *
     * @param onSuccess the function to apply if this is a [Result.Success]
     * @param onFailure the function to apply if this is a [Result.Failure]
     * @return the results of applying the function
     * @since 1.0.0
     */
    inline fun <C> fold(onSuccess: (T) -> C, onFailure: (E) -> C,): C {
        return when (this) {
            is Success -> onSuccess(data)
            is Failure -> onFailure(error)
        }
    }

    /**
     * Converts a nullable of type [T] to a [Result]. Returns [Success] if the value is
     * non-null, otherwise [error] will be invoked.
     * @since 1.0.0
     */
    @OptIn(ExperimentalContracts::class)
    inline infix fun <T, E> T?.toResultOr(error: () -> E): Result<T, E> {
        contract {
            callsInPlace(error, InvocationKind.AT_MOST_ONCE)
        }

        return when (this) {
            null -> Failure(error())
            else -> Success(this)
        }
    }

    inline infix fun <S> chainResult(flatMapAction: (T) -> S): Result<S, E> {
        return map(flatMapAction)
    }

    /**
     * Maps success part of the Result using a [mapAction]. Keeps error as it is. This should be used only to handle / map
     * the result object to another. It should not be used to chain other functions. For that there is a [chain] function.
     *
     * @param mapAction map function for body of [Result.Success] branch
     * @return [Result] with [S]
     * @since 0.2.0
     */
    /*inline fun <S> map(mapAction: (T) -> S): Result<S, E> {
        return when (this) {
            is Success -> Success(mapAction(this.data))
            is Failure -> this
        }
    }*/

    /**
     * Maps success part of the Result using a [mapAction]. Keeps error as it is. This should be used only to handle / map
     * the result object to another. It should not be used to chain other functions. For that there is a [chain] function.
     *
     * @param mapAction map function for body of [Result.Success] branch
     * @return [Result] with [S]
     * @since 0.2.0
     */
    inline fun <C> map(mapAction: (T) -> C): Result<C, E> = flatMap { Success(mapAction(it)) }

    /**
     * @since 0.2.0
     */
    inline fun <S : Any, R> map(transformSuccess: (T) -> S, transformError: (E) -> R,): Result<S, R> {
        return when (this) {
            is Success -> Success(data = transformSuccess(data))
            is Failure -> Failure(error = transformError(error))
        }
    }

    /**
     * Maps success part of the Result using a [success]. Keeps error as it is. This should be used only to handle / map
     * the result object to another. It should not be used to chain other functions. For that there is a [chain] function.
     *
     * @param success map function for [Result.Success] branch
     * @return [Result] with [S]
     * @since 0.2.0
     */
    inline fun <S> mapSuccess(success: (T) -> Result<S, Nothing>): Result<S, E> {
        return flatMap(success)
    }

    /**
     * @since 0.2.0
     */
    inline infix fun <R> mapFailure(flatMapAction: (Failure<E>) -> Result<Nothing, R>): Result<T, R> {
        return when (this) {
            is Success -> this
            is Failure -> flatMapAction(this)
        }
    }

    /**
     * @since 0.2.0
     */
    inline fun <R> mapError(transform: (E) -> R): Result<T, R> {
        return when (this) {
            is Success -> this
            is Failure -> Failure(error = transform(error))
        }
    }

    /**
     * @since 0.2.0
     */
    inline fun <S : Any, R> mapFull(transform: (Result<T, E>) -> Result<S, R>): Result<S, R> {
        return transform(this)
    }

    /*    @Deprecated(
            message = "Use the retypeErrorCode function instead",
            replaceWith = ReplaceWith("retypeErrorCode(newErrorCode =)")
        )
        fun <S> Failure<T>.retypeError(newErrorCode: ErrorCode? = null): Result<S> =
            failure(
                ErrorResult(
                    code = newErrorCode ?: this.error.code,
                    message = this.error.message,
                    throwable = this.error.throwable
                ), this.data as S
            )

     */
}

/**
 * Binds the given function across [Success].
 *
 * @param f The function to bind across [Success].
 * @since 1.0.0
 */
inline fun <E, T, C> Result<T, E>.flatMap(f: (T) -> Result<C, E>): Result<C, E> = when (this) {
    is Success -> f(this.data)
    is Result.Failure -> this
}

/**
 * Chains an action on the success of the current Result. Used to call other functions on success of this [Result].
 * Does not call the [chainAction] on error.
 *
 * Example:
 * ```kotlin
 * import com.basket.core.common.result.*
 *
 * fun main() {
 *   //sampleStart
 *   val result = firstChain().chain { secondChain() }
 *   result.fold(
 *     { println("operation succeeded with $it") },
 *     { println("operation failed with $it") }
 *   )
 *   //sampleEnd
 *   //sampleStart - chain of failure in a path
 *   val result2 = firstChain().chain { secondChain() }.chain { thirdChain() }.chain { fourthChain() }
 *   result2.fold(
 *     { println("operation succeeded with $it") },
 *     { println("operation failed with $it") }
 *   )
 *   //sampleEnd
 * }
 *
 * fun firstChain() = Result.success("Chain1-Success")
 *
 * fun secondChain() = Result.success("Chain2-Success")
 *
 * fun thirdChain() = Result.failure(404)
 *
 * fun fourthChain() = Result.success("Chain3-Success")
 * ```
 * <!--- KNIT example-result-02.kt -->
 *
 * @param chainAction chain function for [Result.Success]
 * @return [Result] with [S]
 * @since 0.2.0
 */
inline infix fun <T, E, S> Result<T, E>.chain(chainAction: (T) -> Result<S, E>): Result<S, E> {
    return when (this) {
        is Success -> chainAction(this.data)
        is Result.Failure -> this
    }
}

/**
 * Chains an action on the success of the current Result. Used to call other functions on success of this [Result].
 * Which function is called ([chainAction] or [default]) on the success of this [Result] depends on the return value of the [predicate] function.
 * Does not call the [chainAction] on error.
 *
 * Example:
 * ```kotlin
 * import com.basket.core.common.result.*
 *
 * fun main() {
 *   //sampleStart
 *   val result = firstChain()
 *      .chainIfPredicate(
 *          predicate = { check() },
 *          chainAction = {
 *              secondChain()
 *          },
 *          default = {
 *              thirdChain()
 *          }
 *      )
 *   result.fold(
 *     { println("operation succeeded with $it") },
 *     { println("operation failed with $it") }
 *   )
 *   //sampleEnd
 * }
 *
 * fun firstChain() = Result.success("Chain1-Success")
 *
 * fun check() = true
 *
 * fun secondChain() = Result.success("Chain2-Success")
 *
 * fun thirdChain() = Result.failure("Chain3-Success")
 * ```
 * <!--- KNIT example-result-03.kt -->
 *
 * @param predicate condition to check before chaining the call of current result to [chainAction] or [default].
 * @param chainAction chain function for [Result.Success] and [predicate] returns true.
 * @param default chain function for [Result.Success] and [predicate] returns false.
 * @since 0.11.0
 */
inline fun <T, E, S> Result<T, E>.chainIfPredicate(
    predicate: (T) -> Boolean,
    chainAction: (T) -> Result<S, E>,
    default: (T) -> Result<S, E>,
): Result<S, E> {
    return when (this) {
        is Success -> {
            if (predicate(this.data)) {
                chainAction(this.data)
            } else {
                default(this.data)
            }
        }
        is Result.Failure -> this
    }
}

/**
 * Maps [Result.Failure] part of the Result using a [flatMapAction]. Keeps success as it is. This should be used only to handle / map
 * the result object to another. It should not be used to chain other functions. For that there is a [chain] function.
 *
 * @param flatMapAction map function for [Result.Failure] into [Result.Success]branch
 * @return [Result] with [S]
 * @since 0.8.0
 */
inline infix fun <S : Any, R> Result<S, R>.mapFailureToSuccess(
    flatMapAction: (Result.Failure<R>) -> Result<S, Nothing>,
): Result<S, Nothing> {
    return when (this) {
        is Success -> this
        is Result.Failure -> flatMapAction(this)
    }
}
