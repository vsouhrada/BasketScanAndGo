package com.basket.sample.scango.data.feature.basket.repository.datasource.mapper

import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1.dto.BasketDto
import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1.dto.BasketItemDto
import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1.dto.CreateBasketRequestDto
import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1.dto.ProductDto
import com.basket.sample.scango.domain.common.model.Basket
import com.basket.sample.scango.domain.common.model.BasketItem
import com.basket.sample.scango.domain.common.model.Product
import com.basket.sample.scango.domain.feature.basket.create.usecase.CreateBasketRequest

interface BasketMapper {

    fun mapCreateBasketRequestDoToDto(basket: CreateBasketRequest): CreateBasketRequestDto

    fun mapBasketDtoToDomain(dto: BasketDto): Basket

    fun mapBasketItemDtoToDomain(dto: BasketItemDto): BasketItem

    fun mapProductDtoToDomain(dto: ProductDto): Product
}
