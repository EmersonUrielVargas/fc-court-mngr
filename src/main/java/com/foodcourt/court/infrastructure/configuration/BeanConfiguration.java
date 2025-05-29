package com.foodcourt.court.infrastructure.configuration;

import com.foodcourt.court.domain.api.IOrderServicePort;
import com.foodcourt.court.domain.api.IPlateServicePort;
import com.foodcourt.court.domain.api.IRestaurantServicePort;
import com.foodcourt.court.domain.spi.*;
import com.foodcourt.court.domain.usecase.OrderUseCases;
import com.foodcourt.court.domain.usecase.PlateUseCases;
import com.foodcourt.court.domain.usecase.RestaurantUseCase;
import com.foodcourt.court.infrastructure.out.jpa.adapter.CategoryJpaAdapter;
import com.foodcourt.court.infrastructure.out.jpa.adapter.OrderJpaAdapter;
import com.foodcourt.court.infrastructure.out.jpa.adapter.PlateJpaAdapter;
import com.foodcourt.court.infrastructure.out.jpa.adapter.RestaurantJpaAdapter;
import com.foodcourt.court.infrastructure.out.jpa.mapper.ICategoryEntityMapper;
import com.foodcourt.court.infrastructure.out.jpa.mapper.IOrderEntityMapper;
import com.foodcourt.court.infrastructure.out.jpa.mapper.IPlateEntityMapper;
import com.foodcourt.court.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.foodcourt.court.infrastructure.out.jpa.repository.ICategoryRepository;
import com.foodcourt.court.infrastructure.out.jpa.repository.IOrderRepository;
import com.foodcourt.court.infrastructure.out.jpa.repository.IPlateRepository;
import com.foodcourt.court.infrastructure.out.jpa.repository.IRestaurantRepository;
import com.foodcourt.court.infrastructure.out.rest.adapter.UserVerificationRestAdapter;
import com.foodcourt.court.infrastructure.out.rest.client.IUserRestClient;
import com.foodcourt.court.infrastructure.security.JwtAuthenticationFilter;
import com.foodcourt.court.infrastructure.security.adapter.AuthenticationAdapter;
import com.foodcourt.court.infrastructure.security.service.AutheticationService;
import com.foodcourt.court.infrastructure.security.service.JwtService;
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
    private final AutheticationService autheticationService;
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;
    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;
    private final IUserRestClient userRestClient;
    private final JwtService jwtService;


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
    public IOrderPersistencePort orderPersistencePort() {
        return new OrderJpaAdapter(orderRepository, orderEntityMapper);
    }

    @Bean
    public IAuthenticationPort authenticationPort() {
        return new AuthenticationAdapter(autheticationService);
    }

    @Bean
    public IPlateServicePort plateServicePort() {
        return new PlateUseCases(platePersistencePort(), restaurantPersistencePort(), categoryPersistencePort(), authenticationPort());
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter(jwtService);
    }

    @Bean
    public IOrderServicePort orderServicePort() {
        return new OrderUseCases(platePersistencePort(), orderPersistencePort(), authenticationPort(),restaurantPersistencePort());
    }

}