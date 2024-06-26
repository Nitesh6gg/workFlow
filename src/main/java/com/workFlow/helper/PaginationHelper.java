package com.workFlow.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PaginationHelper {

    public static Pageable createPageable(int page, int size, String sortBy, String sortOrder) {
        return PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
    }

    public static <T> Map<String, Object> createResponse(Page<T> page, List<T> content) {
        Map<String, Object> response = new HashMap<>();
        response.put("items", content);
        response.put("currentPage", page.getNumber());
        response.put("totalItems", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());
        return response;
    }


}
