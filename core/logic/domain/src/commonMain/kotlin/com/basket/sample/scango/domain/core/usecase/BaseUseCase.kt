package com.basket.sample.scango.domain.core.usecase

import kotlin.native.ObjCName

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
abstract class BaseUseCase<out T : Any, in Params> {

    /**
     * Executes appropriate implementation of [BaseUseCase],
     * @param params Set of input parameters
     * @return type [T] of parameter. In the most common way the [T] is wrapped to a special use-case implementation.
     */
    @ObjCName(name = "callAsFunction")
    suspend operator fun invoke(params: Params): T = doWork(params)

    /**
     * Inner business logic of [BaseUseCase]
     *
     * @param params Set of input parameters
     * @return type [T] of parameter. In the most common way the [T] is wrapped to a special use-case implementation.
     */
    protected abstract suspend fun doWork(params: Params): T
}
