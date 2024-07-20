package com.workFlow.dto.response;

public interface TaskProjection {
    Long getTaskId();
    String getDescription();
    String getStatus();
    String getPriority();
    String getStartDate();
    String getDueDate();

    // Nested Projection for Project
    ProjectInfo getProjectId();

    interface ProjectInfo {
        Long getProjectId();
        String getProjectName();
    }

    // Nested Projection for User
    UserInfo getAssignUserId();

    interface UserInfo {
        Long getUserId();
        String getUsername();
    }
}