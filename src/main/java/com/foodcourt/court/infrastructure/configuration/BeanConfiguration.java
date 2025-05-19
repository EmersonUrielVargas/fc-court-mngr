package com.foodcourt.court.infrastructure.configuration;

import com.foodcourt.court.domain.api.IRestaurantServicePort;
import com.foodcourt.court.domain.spi.IRestaurantPersistencePort;
import com.foodcourt.court.domain.spi.IUserVerificationPort;
import com.foodcourt.court.domain.usecase.RestaurantUseCase;
import com.foodcourt.court.infrastructure.out.jpa.adapter.RestaurantJpaAdapter;
import com.foodcourt.court.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
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
}