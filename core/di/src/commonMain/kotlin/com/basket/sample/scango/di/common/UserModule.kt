package com.basket.sample.scango.di.common

import com.basket.sample.scango.data.feature.authorization.token.repository.TokenRepositoryImpl
import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.TokenLocalDataSource
import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.TokenLocalDataSourceImpl
import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.TokenRemoteDataSource
import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.TokenRemoteDataSourceImpl
import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1.UserApi
import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1.UserApiImpl
import com.basket.sample.scango.data.feature.user.common.repository.UserRepositoryImpl
import com.basket.sample.scango.data.feature.user.common.repository.datasource.UserLocalDataSource
import com.basket.sample.scango.data.feature.user.common.repository.datasource.UserLocalDataSourceImpl
import com.basket.sample.scango.data.feature.user.common.repository.datasource.UserRemoteDataSource
import com.basket.sample.scango.data.feature.user.common.repository.datasource.UserRemoteDataSourceImpl
import com.basket.sample.scango.data.feature.user.common.repository.datasource.mapper.UserDtoToDoMapper
import com.basket.sample.scango.data.feature.user.common.repository.datasource.mapper.UserDtoToDoMapperImpl
import com.basket.sample.scango.domain.feature.authorization.repository.TokenRepository
import com.basket.sample.scango.domain.feature.authorization.usecase.FetchTokenInfoUseCase
import com.basket.sample.scango.domain.feature.authorization.usecase.FetchTokenUserInfoUseCaseImpl
import com.basket.sample.scango.domain.feature.user.common.repository.UserRepository
import com.basket.sample.scango.domain.feature.user.getActiveUser.usecase.GetActiveUserUseCase
import com.basket.sample.scango.domain.feature.user.getActiveUser.usecase.GetActiveUserUseCaseImpl
import com.basket.sample.scango.domain.feature.user.getUser.usecase.GetUserUseCase
import com.basket.sample.scango.domain.feature.user.getUser.usecase.GetUserUseCaseImpl
import com.basket.sample.scango.domain.feature.user.saveActiveUser.usecase.SaveActiveUserUseCase
import com.basket.sample.scango.domain.feature.user.saveActiveUser.usecase.SaveActiveUserUseCaseImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val userModule =
    module {

        factoryOf(::GetUserUseCaseImpl) bind GetUserUseCase::class

        factoryOf(::GetActiveUserUseCaseImpl) bind GetActiveUserUseCase::class

        factoryOf(::SaveActiveUserUseCaseImpl) bind SaveActiveUserUseCase::class

        factoryOf(::FetchTokenUserInfoUseCaseImpl) bind FetchTokenInfoUseCase::class

        singleOf(::UserRepositoryImpl) bind UserRepository::class
        singleOf(::TokenRepositoryImpl) bind TokenRepository::class

        singleOf(::TokenRemoteDataSourceImpl) bind TokenRemoteDataSource::class
        singleOf(::TokenLocalDataSourceImpl) bind TokenLocalDataSource::class

        singleOf(::UserLocalDataSourceImpl) bind UserLocalDataSource::class
        singleOf(::UserRemoteDataSourceImpl) bind UserRemoteDataSource::class

        singleOf(::UserApiImpl) bind UserApi::class

        factoryOf(::UserDtoToDoMapperImpl) bind UserDtoToDoMapper::class
    }
