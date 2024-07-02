package com.workFlow.specification;

import com.workFlow.entity.Project;
import com.workFlow.entity.User;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class ProjectSpecifications {

   /* public static Specification<Project> findAllProjects(String createdBy) {
        return (root, query, criteriaBuilder) ->
                Join<User,Project>projects=root.join("projects");
    }*/

    public static Specification<Project> status(String status) {
        return (root, query, criteriaBuilder) ->
                status == null ? null : criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Project> createdBy(String createdBy) {
        return (root, query, criteriaBuilder) ->
                createdBy == null ? null : criteriaBuilder.equal(root.get("createdBy"), createdBy);
    }
}
