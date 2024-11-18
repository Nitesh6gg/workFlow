package com.workflow.specification;

import com.workflow.entity.Task;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecifications {

    public static Specification<Task> getPriority(String priority) {
        return (root, query, cb) -> cb.equal(root.get("priority"), priority);
    }



    public static Specification<Task> assignTo(int userId) {
        return (Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.equal(root.get("assignUserId").get("userId"), userId);
    }



}
