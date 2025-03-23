package com.basket.sample.scango.di.basket

import com.basket.sample.scango.data.feature.basket.repository.BasketRepositoryImpl
import com.basket.sample.scango.data.feature.basket.repository.datasource.BasketLocalDataSource
import com.basket.sample.scango.data.feature.basket.repository.datasource.BasketLocalDataSourceImpl
import com.basket.sample.scango.data.feature.basket.repository.datasource.BasketRemoteDataSource
import com.basket.sample.scango.data.feature.basket.repository.datasource.BasketRemoteDataSourceImpl
import com.basket.sample.scango.domain.feature.basket.active.GetActiveBasketUseCase
import com.basket.sample.scango.domain.feature.basket.active.GetActiveBasketUseCaseImpl
import com.basket.sample.scango.domain.feature.basket.active.ObserveActiveBasketUseCase
import com.basket.sample.scango.domain.feature.basket.active.ObserveActiveBasketUseCaseImpl
import com.basket.sample.scango.domain.feature.basket.active.SetActiveBasketUseCase
import com.basket.sample.scango.domain.feature.basket.active.SetActiveBasketUseCaseImpl
import com.basket.sample.scango.domain.feature.basket.common.repository.BasketRepository
import com.basket.sample.scango.domain.feature.basket.create.usecase.CreateBasketUseCase
import com.basket.sample.scango.domain.feature.basket.create.usecase.CreateBasketUseCaseImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val basketModule = module {

    singleOf(::BasketRepositoryImpl) bind BasketRepository::class
    singleOf(::BasketLocalDataSourceImpl) bind BasketLocalDataSource::class
    singleOf(::BasketRemoteDataSourceImpl) bind BasketRemoteDataSource::class

    factoryOf(::SetActiveBasketUseCaseImpl) bind SetActiveBasketUseCase::class
    factoryOf(::GetActiveBasketUseCaseImpl) bind GetActiveBasketUseCase::class
    factoryOf(::ObserveActiveBasketUseCaseImpl) bind ObserveActiveBasketUseCase::class
    factoryOf(::CreateBasketUseCaseImpl) bind CreateBasketUseCase::class
}
