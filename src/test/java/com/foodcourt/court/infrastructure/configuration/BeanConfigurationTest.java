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
import com.foodcourt.court.infrastructure.security.JwtAuthenticationFilter;
import com.foodcourt.court.infrastructure.security.service.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BeanConfigurationTest {

    @Mock
    private IRestaurantRepository restaurantRepository;
    @Mock
    private IRestaurantEntityMapper restaurantEntityMapper;
    @Mock
    private IPlateRepository plateRepository;
    @Mock
    private IPlateEntityMapper plateEntityMapper;
    @Mock
    private ICategoryRepository categoryRepository;
    @Mock
    private ICategoryEntityMapper categoryEntityMapper;
    @Mock
    private IUserRestClient userRestClient;
    @Mock
    private JwtService jwtService;

    @InjectMocks
    private BeanConfiguration beanConfiguration;

    @Test
    void userPersistencePort() {
        IUserVerificationPort userVerificationPort = beanConfiguration.userPersistencePort();

        assertNotNull(userVerificationPort);
        assertInstanceOf(UserVerificationRestAdapter.class, userVerificationPort);
    }

    @Test
    void restaurantPersistencePort() {
        IRestaurantPersistencePort restaurantPersistencePort = beanConfiguration.restaurantPersistencePort();

        assertNotNull(restaurantPersistencePort);
        assertInstanceOf(RestaurantJpaAdapter.class,restaurantPersistencePort);
    }

    @Test
    void restaurantServicePort_shouldCreateRestaurantUseCase() {
        IRestaurantServicePort restaurantServicePort = beanConfiguration.restaurantServicePort();

        assertNotNull(restaurantServicePort);
        assertInstanceOf(RestaurantUseCase.class, restaurantServicePort);
    }

    @Test
    void platePersistencePort_shouldCreatePlateJpaAdapter() {
        IPlatePersistencePort platePersistencePort = beanConfiguration.platePersistencePort();

        assertNotNull(platePersistencePort);
        assertInstanceOf(PlateJpaAdapter.class, platePersistencePort);
    }

    @Test
    void categoryPersistencePort_shouldCreateCategoryJpaAdapter() {
        ICategoryPersistencePort categoryPersistencePort = beanConfiguration.categoryPersistencePort();

        assertNotNull(categoryPersistencePort);
        assertInstanceOf(CategoryJpaAdapter.class, categoryPersistencePort);
    }

    @Test
    void plateServicePort_shouldCreatePlateUseCases() {
        IPlateServicePort plateServicePort = beanConfiguration.plateServicePort();

        assertNotNull(plateServicePort);
        assertInstanceOf(PlateUseCases.class, plateServicePort);
    }

    @Test
    void jwtAuthenticationFilter_shouldCreateFilterInstance() {

        JwtAuthenticationFilter filter = beanConfiguration.jwtAuthenticationFilter();

        assertNotNull(filter);
    }
}