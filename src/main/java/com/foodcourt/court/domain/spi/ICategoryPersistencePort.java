package com.foodcourt.court.domain.spi;

import com.foodcourt.court.domain.model.Category;

import java.util.Optional;

public interface ICategoryPersistencePort {
    Optional<Category> getCategoryById(Long categoryId);
}
