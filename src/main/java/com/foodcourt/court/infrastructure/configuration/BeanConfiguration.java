package com.foodcourt.court.infrastructure.configuration;

import com.foodcourt.court.domain.api.IOrderServicePort;
import com.foodcourt.court.domain.api.IPlateServicePort;
import com.foodcourt.court.domain.api.IRestaurantServicePort;
import com.foodcourt.court.domain.spi.*;
import com.foodcourt.court.domain.usecase.OrderUseCases;
import com.foodcourt.court.domain.usecase.PlateUseCases;
import com.foodcourt.court.domain.usecase.RestaurantUseCase;
import com.foodcourt.court.infrastructure.out.jpa.adapter.*;
import com.foodcourt.court.infrastructure.out.jpa.mapper.*;
import com.foodcourt.court.infrastructure.out.jpa.repository.*;
import com.foodcourt.court.infrastructure.out.rest.adapter.UserVerificationRestAdapter;
import com.foodcourt.court.infrastructure.out.rest.client.IUserRestClient;
import com.foodcourt.court.infrastructure.out.twilio.adapter.NotificationTwilioAdapter;
import com.foodcourt.court.infrastructure.out.twilio.service.NotificationService;
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
    private final IAssignmentEmployeeRepository assignmentEmployeeRepository;
    private final IAssignmentEmployeeEntityMapper assignmentEmployeeEntityMapper;
    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;
    private final IUserRestClient userRestClient;
    private final NotificationService notificationService;
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
        return new RestaurantUseCase(userPersistencePort(), restaurantPersistencePort(), authenticationPort(), assignmentEmployeePort());
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
    public IAssignmentEmployeePort assignmentEmployeePort() {
        return new AssignmentEmployeeJpaAdapter(assignmentEmployeeRepository, assignmentEmployeeEntityMapper);
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
        return new OrderUseCases(
                platePersistencePort(),
                orderPersistencePort(),
                authenticationPort(),
                restaurantPersistencePort(),
                assignmentEmployeePort(),
                notificationPort(),
                userPersistencePort());
    }

    @Bean
    public INotificationPort notificationPort() {
        return new NotificationTwilioAdapter(notificationService);
    }

}