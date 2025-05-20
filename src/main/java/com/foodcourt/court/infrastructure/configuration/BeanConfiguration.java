package com.foodcourt.court.infrastructure.configuration;

import com.foodcourt.court.domain.api.IPlateServicePort;
import com.foodcourt.court.domain.api.IRestaurantServicePort;
import com.foodcourt.court.domain.spi.ICategoryPersistencePort;
import com.foodcourt.court.domain.spi.IPlatePersistencePort;
import com.foodcourt.court.domain.spi.IRestaurantPersistencePort;
import com.foodcourt.court.domain.spi.IUserVerificationPort;
import com.foodcourt.court.domain.usecase.PlateUseCases;
import com.foodcourt.court.domain.usecase.RestaurantUseCase;
import com.foodcourt.court.infrastructure.out.jpa.adapter.CategoryJpaAdapter;
import com.foodcourt.court.infrastructure.out.jpa.adapter.PlateJpaAdapter;
import com.foodcourt.court.infrastructure.out.jpa.adapter.RestaurantJpaAdapter;
import com.foodcourt.court.infrastructure.out.jpa.mapper.ICategoryEntityMapper;
import com.foodcourt.court.infrastructure.out.jpa.mapper.IPlateEntityMapper;
import com.foodcourt.court.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.foodcourt.court.infrastructure.out.jpa.repository.ICategoryRepository;
import com.foodcourt.court.infrastructure.out.jpa.repository.IPlateRepository;
import com.foodcourt.court.infrastructure.out.jpa.repository.IRestaurantRepository;
import com.foodcourt.court.infrastructure.out.rest.adapter.UserVerificationRestAdapter;
import com.foodcourt.court.infrastructure.out.rest.client.IUserRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final IPlateRepository plateRepository;
    private final IPlateEntityMapper plateEntityMapper;
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;
    private final IUserRestClient userRestClient;

    @Bean
    public IUserVerificationPort userPersistencePort() {
        return new UserVerificationRestAdapter(userRestClient);
    }

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort() {
        return new RestaurantJpaAdapter(restaurantRepository, restaurantEntityMapper);
    }

    @Bean
    public IRestaurantServicePort restaurantServicePort() {
        return new RestaurantUseCase(userPersistencePort(), restaurantPersistencePort());
    }

    @Bean
    public IPlatePersistencePort platePersistencePort() {
        return new PlateJpaAdapter(plateRepository, plateEntityMapper);
    }

    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public IPlateServicePort plateServicePort() {
        return new PlateUseCases(platePersistencePort(), restaurantPersistencePort(), categoryPersistencePort());
    }
}