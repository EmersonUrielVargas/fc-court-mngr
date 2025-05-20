package com.foodcourt.court.domain.spi;

import com.foodcourt.court.domain.model.Category;
import com.foodcourt.court.domain.model.Plate;

import java.util.Optional;

public interface ICategoryPersistencePort {
    Optional<Category> getCategoryById(Long categoryId);
}
