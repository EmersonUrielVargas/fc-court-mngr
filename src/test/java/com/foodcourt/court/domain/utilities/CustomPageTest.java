package com.foodcourt.court.domain.utilities;

import com.foodcourt.court.shared.DataConstants;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomPageTest {

    @Test
    void mappingConstructor_withNonNullSourcePage_shouldCopyMetadataAndSetNewData() {
        List<String> sourceData = List.of("item1", "item2");
        CustomPage<String> sourcePage = new CustomPage<>();
        sourcePage.setData(sourceData);
        sourcePage.setTotalItems(DataConstants.DEFAULT_CUSTOM_PAGE_TOTAL_ITEMS);
        sourcePage.setTotalPages(DataConstants.DEFAULT_CUSTOM_PAGE_TOTAL_PAGES);
        sourcePage.setIsLastPage(DataConstants.DEFAULT_CUSTOM_PAGE_IS_LAST_PAGE);
        sourcePage.setCurrentPage(DataConstants.DEFAULT_CUSTOM_PAGE_CURRENT_PAGE);
        sourcePage.setPageSize(DataConstants.DEFAULT_PAGE_SIZE);

        List<Integer> newData = List.of(1, 2, 3);

        CustomPage<Integer> newPage = new CustomPage<>(newData, sourcePage);

        assertEquals(newData, newPage.getData());
        assertEquals(sourcePage.getTotalItems(), newPage.getTotalItems());
        assertEquals(sourcePage.getTotalPages(), newPage.getTotalPages());
        assertEquals(sourcePage.getIsLastPage(), newPage.getIsLastPage());
        assertEquals(sourcePage.getCurrentPage(), newPage.getCurrentPage());
        assertEquals(sourcePage.getPageSize(), newPage.getPageSize());
    }

    @Test
    void mappingConstructor_withNullSourcePage_shouldHandleNullSourceGracefully() {
        List<Double> newData = List.of(10.5, 20.5);

        CustomPage<Double> newPage = new CustomPage<>(newData, null); // sourcePage es null

        assertEquals(newData, newPage.getData());
        assertNull(newPage.getTotalItems());
        assertNull(newPage.getTotalPages());
        assertNull(newPage.getIsLastPage());
        assertNull(newPage.getCurrentPage());
        assertNull(newPage.getPageSize());
    }

    @Test
    void mappingConstructor_withSourcePageNullMetadata_shouldResultNullMetadata() {
        CustomPage<String> sourcePage = new CustomPage<>();
        sourcePage.setData(List.of("a"));

        List<Integer> newData = List.of(1, 2);

        CustomPage<Integer> newPage = new CustomPage<>(newData, sourcePage);

        assertEquals(newData, newPage.getData());
        assertNull(newPage.getTotalItems());
        assertNull(newPage.getTotalPages());
        assertNull(newPage.getIsLastPage());
        assertNull(newPage.getCurrentPage());
        assertNull(newPage.getPageSize());
    }
}