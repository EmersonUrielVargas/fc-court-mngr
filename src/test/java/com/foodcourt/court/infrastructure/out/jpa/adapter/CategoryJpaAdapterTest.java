package com.foodcourt.court.infrastructure.out.jpa.adapter;

import com.foodcourt.court.domain.model.Category;
import com.foodcourt.court.infrastructure.out.jpa.entity.CategoryEntity;
import com.foodcourt.court.infrastructure.out.jpa.mapper.ICategoryEntityMapper;
import com.foodcourt.court.infrastructure.out.jpa.repository.ICategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryJpaAdapterTest {

    @Mock
    private ICategoryRepository categoryRepository;

    @Mock
    private ICategoryEntityMapper categoryEntityMapper;

    @InjectMocks
    private CategoryJpaAdapter categoryJpaAdapter;

    @Test
    void getCategorySuccessful() {
        Long categoryId = 19L;
        Category category = Category.builder().build();
        CategoryEntity categoryEntity = new CategoryEntity();

        when(categoryEntityMapper.toCategory(categoryEntity)).thenReturn(category);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryEntity));
        Optional<Category> categoryFound = categoryJpaAdapter.getCategoryById(categoryId);

        verify(categoryEntityMapper).toCategory(categoryEntity);
        verify(categoryRepository).findById(categoryId);
        assertEquals(Optional.of(category), categoryFound);
    }

}