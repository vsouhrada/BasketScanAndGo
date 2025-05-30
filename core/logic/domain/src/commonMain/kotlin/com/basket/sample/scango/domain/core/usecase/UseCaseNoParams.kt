package com.basket.sample.scango.domain.core.usecase

import com.basket.core.common.result.failure.Error
import kotlin.native.ObjCName

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This class represents an execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 *
 * Use cases are the entry points to the domain layer.
 * @author vsouhrada
 */
abstract class UseCaseNoParams<out T : Any, E : Error> : UseCase<T, E, Unit>() {
    @ObjCName(name = "callAsFunction")
    suspend operator fun invoke() = super.invoke(Unit)
}
