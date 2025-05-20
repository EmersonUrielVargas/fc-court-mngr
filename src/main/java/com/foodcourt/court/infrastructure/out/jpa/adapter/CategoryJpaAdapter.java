package com.foodcourt.court.infrastructure.out.jpa.adapter;

import com.foodcourt.court.domain.model.Category;
import com.foodcourt.court.domain.model.Restaurant;
import com.foodcourt.court.domain.spi.ICategoryPersistencePort;
import com.foodcourt.court.domain.spi.IRestaurantPersistencePort;
import com.foodcourt.court.infrastructure.out.jpa.mapper.ICategoryEntityMapper;
import com.foodcourt.court.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.foodcourt.court.infrastructure.out.jpa.repository.ICategoryRepository;
import com.foodcourt.court.infrastructure.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CategoryJpaAdapter implements ICategoryPersistencePort {

    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;


    @Override
    public Optional<Category> getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).map(categoryEntityMapper::toCategory);
    }
}
