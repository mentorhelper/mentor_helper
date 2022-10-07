package com.ua.javarush.mentor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Data
@Schema(description = "Page DTO")
public class PageDTO<T> {
    @Schema(description = "Content of page")
    private List<T> content;
    @Schema(description = "Total pages")
    private int totalPages;
    @Schema(description = "Total elements")
    private long totalElements;
    @Schema(description = "Page size")
    private int pageSize;
    @Schema(description = "Page number")
    private int pageNumber;
    @Schema(description = "Sort")
    private SortDTO sort;

    public PageDTO(Page<T> page, Pageable pageable) {
        this.content = page.getContent();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.pageSize = page.getSize();
        this.pageNumber = page.getNumber();
        this.sort = new SortDTO(pageable.getSort());
    }
}
