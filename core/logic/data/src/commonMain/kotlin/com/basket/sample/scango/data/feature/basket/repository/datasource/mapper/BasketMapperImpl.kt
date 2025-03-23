package com.basket.sample.scango.data.feature.basket.repository.datasource.mapper

import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1.dto.BasketDto
import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1.dto.BasketItemDto
import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1.dto.CreateBasketRequestDto
import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1.dto.ProductDto
import com.basket.sample.scango.domain.common.model.Basket
import com.basket.sample.scango.domain.common.model.BasketItem
import com.basket.sample.scango.domain.common.model.Product
import com.basket.sample.scango.domain.feature.basket.create.usecase.CreateBasketRequest

class BasketMapperImpl : BasketMapper {
    override fun mapCreateBasketRequestDoToDto(basket: CreateBasketRequest) = CreateBasketRequestDto(
        customerId = basket.customerId,
        sharedBasket = basket.sharedBasket,
        createdTimestampUTC = basket.createdTimestampUTC
    )

    override fun mapBasketDtoToDomain(dto: BasketDto) = Basket(
        customerId = dto.customerId,
        sharedBasket = dto.sharedBasket,
        id = dto.id,
        items = emptyList(),
        totalItems = 0,
        totalPrice = 0.0,
    )

    override fun mapBasketItemDtoToDomain(dto: BasketItemDto) = BasketItem(
        id = dto.id,
        product = mapProductDtoToDomain(dto.product),
        quantity = dto.quantity,
        totalPrice = dto.totalPrice,
    )

    override fun mapProductDtoToDomain(dto: ProductDto) = Product(
        id = dto.id,
        name = dto.name,
        description = dto.description,
        price = dto.price,
        imageUrl = dto.imageUrl,
        category = dto.category,
        isSpecialOffer = dto.isSpecialOffer,
        discountPercentage = dto.discountPercentage,
    )
}
