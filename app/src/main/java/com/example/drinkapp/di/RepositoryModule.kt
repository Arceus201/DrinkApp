package com.example.drinkapp.di

import com.example.drinkapp.data.repository.*
import com.example.drinkapp.data.resource.retrofit.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCategoryRepository(
        apiService: CategoryApiService
    ): CategoryRepository {
        return CategoryRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideSizeRepository(
        apiService: SizeApiService
    ): SizeRepository {
        return SizeRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun providePriceSizeRepository(
        apiService: PriceSizeApiService
    ): PriceSizeRepository {
        return PriceSizeRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        apiService: ProductApiService
    ): ProductRepository {
        return ProductRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        apiService: UserApiService
    ): UserRepository {
        return UserRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideOrderRepository(
        apiService: OrderApiService
    ): OrderRepository {
        return OrderRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideCartItemRepository(
        apiService: CartItemApiService
    ): CartItemRepository {
        return CartItemRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideAddressRepository(
        apiService: AddressApiService
    ): AddressRepository {
        return AddressRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideRevenueRepository(
        apiService: RevenueApiService
    ): RevenueRepository {
        return RevenueRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideAddressVNRepository(
        apiService: AddressVNpiService
    ): AddressVNRepository {
        return AddressVNRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideExchangeRateRepository(
        apiService: ExChangeRateApiService
    ): ExchangeRateRepository {
        return ExchangeRateRepositoryImpl(apiService)
    }
}
