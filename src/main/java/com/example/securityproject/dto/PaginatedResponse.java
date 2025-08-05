package com.example.securityproject.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PaginatedResponse<T> {
    private final List<T> data;
    private final Meta meta;

    public PaginatedResponse(List<T> data, Meta meta) {
        this.data = data;
        this.meta = meta;
    }

    @Getter
    public static class Meta {
        private final int page;
        private final int pageSize;
        private final int total;
        private final int totalPages;
        private final boolean hasPrev;
        private final boolean hasNext;

        public Meta(int page, int pageSize, int total) {
            this.page = page;
            this.pageSize = pageSize;
            this.total = total;
            this.totalPages = (int) Math.ceil((double) total / pageSize);
            this.hasPrev = page > 1;
            this.hasNext = page < totalPages;
        }
    }
}
