package com.basket.sample.scango.domain.core.usecase

import com.basket.core.common.result.Result
import com.basket.core.common.result.failure.Error
import com.basket.core.common.result.failure.FailureResult


/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This class represents an execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 *
 * Use cases are the entry points to the domain layer.
 *
 * @author vsouhrada
 * @since 1.0.0
 */
abstract class UseCase<out T : Any, E : Error, in Params> : BaseUseCase<Result<T, FailureResult<E>>, Params>()
