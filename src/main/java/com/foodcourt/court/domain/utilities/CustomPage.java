package com.foodcourt.court.domain.utilities;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
public class CustomPage<T> {
    private List<T> data;
    private Long totalItems;
    private Integer totalPages;
    private Boolean isLastPage;
    private Integer currentPage;
    private Integer pageSize;

    public <S> CustomPage(List<T> newData, CustomPage<S> sourcePage) {
        this.data = newData;
        if (Objects.nonNull(sourcePage)){
            this.totalItems = sourcePage.getTotalItems();
            this.totalPages = sourcePage.getTotalPages();
            this.isLastPage = sourcePage.getIsLastPage();
            this.currentPage = sourcePage.getCurrentPage();
            this.pageSize = sourcePage.getPageSize();
        }
    }
}
