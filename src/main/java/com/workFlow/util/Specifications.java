package com.workFlow.util;

import com.workFlow.entity.Project;
import org.springframework.data.jpa.domain.Specification;

public class Specifications {

    public static Specification<Project> projectStatus(String status) {
        return (root, query, criteriaBuilder) ->
                status == null ? null : criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Project> projectCreatedBy(String createdBy) {
        return (root, query, criteriaBuilder) ->
                createdBy == null ? null : criteriaBuilder.equal(root.get("createdBy"), createdBy);
    }
}
