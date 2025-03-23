package com.basket.core.common.result

import com.basket.core.common.result.failure.Error
import com.basket.core.common.result.failure.FailureResult

/**
 * Converts object [T] into [Result.Success] containing [T] only when calling object is not null. Else it builds
 * [ErrorResult] and fills [Result.Companion.failure] with it.
 *
 * @param error used to build [ErrorResult] when predicate is false
 * @return [Result] with [T] calling object
 * @see toSuccessIfPredicate
 * @since 0.1.0
 */
inline fun <T : Any, E> T?.toSuccessIfNotNull(error: () -> E): Result<T, E> {
    return toSuccessIfPredicate(
        error = error,
        predicate = { this != null },
    )
}

/**
 * Converts object [T] into [Result.Success] containing [T] only when [predicate] returns true. Else it builds
 * [ErrorResult] and fills [Result.Companion.failure] with it.
 *
 * @param predicate condition to check before wrapping the object in [Result.Success]
 * @param error used to build [ErrorResult] when predicate is false
 * @return [Result] with [T] calling object
 * @since 0.1.0
 */
inline fun <T : Any, E> T?.toSuccessIfPredicate(predicate: (T?) -> Boolean, error: () -> E,): Result<T, E> {
    return if (this != null && predicate(this)) {
        this.toSuccess()
    } else {
        Result.Companion.failure(error())
    }
}

/**
 * Maps any object to [Result.Success] containing this object.
 *
 * @return [Result.Success] with [T] calling object
 * @since 0.1.0
 */
fun <T : Any> T.toSuccess(): Result<T, Nothing> {
    return Result.Companion.success(this)
}
/*
*
 * Combines two successful results ([T1], [T2]) into one single result [R]. If any of the two result is a [Result.Failure]
 * then it returns it to handle the error state.
 *
 * @param other second [Result] to combine with the first one
 * @param transform function combining two results
 * @return [Result] with [R] data
 * @see 0.2.0
inline fun <T1 : Any, T2 : Any, R : Any> Result<T1>.combine(
    other: Result<T2>,
    transform: (T1, T2) -> R
): Result<R> {
    return when {
        this is Result.Failure<*> -> this.retypeErrorCode()
        other is Result.Failure<*> -> other.retypeErrorCode()
        else -> Result.Success(
            transform(
                (this as Result.Success).data,
                (other as Result.Success).data
            )
        )
    }
}

*
 * Combines three successful results ([T1], [T2], [T3]) into one single result [R]. If any of the three result is a
 * [Result.Failure] then it returns it to handle the error state.
 *
 * @param two second [Result] to combine with the other ones
 * @param three third [Result] to combine with the other ones
 * @param transform function combining three results
 * @return [Result] with [R] data
 * @see 0.2.0

inline fun <T1 : Any, T2 : Any, T3 : Any, R : Any> Result<T1>.combine(
    two: Result<T2>,
    three: Result<T3>,
    transform: (T1, T2, T3) -> R
): Result<R> {
    return when {
        this is Result.Failure<*> -> this.retypeErrorCode()
        two is Result.Failure<*> -> two.retypeErrorCode()
        three is Result.Failure<*> -> three.retypeErrorCode()
        else -> Result.Success(
            transform(
                (this as Result.Success).data,
                (two as Result.Success).data,
                (three as Result.Success).data
            )
        )
    }
}

*
 * Combines four successful results ([T1], [T2], [T3], [T4]) into one single result [R]. If any of the four result is a
 * [Result.Failure] then it returns it to handle the error state.
 *
 * @param two second [Result] to combine with the other ones
 * @param three third [Result] to combine with the other ones
 * @param four fourth [Result] to combine with the other ones
 * @param transform function combining four results
 * @return [Result] with [R] data
 * @see 0.2.0

inline fun <T1 : Any?, T2 : Any, T3 : Any, T4 : Any, R : Any> Result<T1, *>.combine(
    two: Result<T2, *>,
    three: Result<T3, *>,
    four: Result<T4, *>,
    transform: (T1, T2, T3, T4) -> R
): Result<R, *> {
    return when {
        this is Result.Failure<*> -> this.retypeErrorCode()
        two is Result.Failure<*> -> two.retypeErrorCode()
        three is Result.Failure<*> -> three.retypeErrorCode()
        four is Result.Failure<*> -> four.retypeErrorCode()
        else -> Result.Success(
            transform(
                (this as Result.Success).data,
                (two as Result.Success).data,
                (three as Result.Success).data,
                (four as Result.Success).data
            )
        )
    }
}*/

/**
 * Same as [chain] function in the [Result] class, but adds [failureResult] for error mapping.
 *
 * @since 1.2.0
 */
inline fun <T, E : Error, S, F : Error> Result<T, FailureResult<E>>.chain(
    noinline failureResult: (error: E) -> F,
    chainAction: (T) -> Result<S, FailureResult<F>>,
): Result<S, FailureResult<F>> {
    return when (this) {
        is Result.Success -> chainAction(this.data)
        is Result.Failure ->
            Result.Failure(
                this.error.mapError {
                    failureResult(this.error.error)
                },
            )
    }
}

/**
 * Same as [chainIfPredicate] function in the [Result] class, but adds [failureResult] for error mapping.
 *
 * @since 1.2.0
 */
inline fun <T, E : Error, S, F : Error> Result<T, FailureResult<E>>.chainIfPredicate(
    noinline failureResult: (error: E) -> F,
    predicate: (T) -> Boolean,
    chainAction: (T) -> Result<S, FailureResult<F>>,
    default: (T) -> Result<S, FailureResult<F>>,
): Result<S, FailureResult<F>> {
    return when (this) {
        is Result.Success -> {
            if (predicate(this.data)) {
                chainAction(this.data)
            } else {
                default(this.data)
            }
        }

        is Result.Failure ->
            Result.Failure(
                this.error.mapError {
                    failureResult(this.error.error)
                },
            )
    }
}

/**
 * Same as [flatMap] function in the [Result] class, but adds [failureResult] for error mapping.
 *
 * @since 1.2.0
 */
inline fun <E : Error, T, C, F : Error> Result<T, FailureResult<E>>.flatMap(
    noinline failureResult: (error: E) -> F,
    f: (T) -> Result<C, FailureResult<F>>,
): Result<C, FailureResult<F>> = when (this) {
    is Result.Success -> f(this.data)
    is Result.Failure ->
        Result.Failure(
            this.error.mapError {
                failureResult(this.error.error)
            },
        )
}

/**
 * Same as [map] function in the [Result] class, but adds [failureResult] for error mapping.
 *
 * @since 1.2.0
 */
inline fun <C, T, E : Error, F : Error> Result<T, FailureResult<E>>.map(
    noinline failureResult: (error: E) -> F,
    mapAction: (T) -> C,
): Result<C, FailureResult<F>> = flatMap(failureResult) { Result.Success(mapAction(it)) }

/**
 * Same as [chainResult] function in the [Result] class, but adds [failureResult] for error mapping.
 *
 * @since 1.2.0
 */
inline fun <S, T, E : Error, F : Error> Result<T, FailureResult<E>>.chainResult(
    noinline failureResult: (error: E) -> F,
    flatMapAction: (T) -> S,
): Result<S, FailureResult<F>> {
    return map(failureResult, flatMapAction)
}

/**
 * It maps [FailureResult.error], but keeps the other [FailureResult] content.
 *
 * @param mapErrors error mapping
 * @return [Result.Failure] with [FailureResult] with an error of type [F].
 * @since 1.2.0
 */
inline fun <T, E : Error, F : Error> Result<T, FailureResult<E>>.mapFailureResultError(
    noinline mapErrors: (error: E) -> F,
): Result<T, FailureResult<F>> {
    return when (this) {
        is Result.Success -> this
        is Result.Failure ->
            Result.Failure(
                this.error.mapError {
                    mapErrors(it)
                },
            )
    }
}
