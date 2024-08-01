package com.workFlow.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.internal.bytebuddy.dynamic.loading.InjectionClassLoader;

@Entity
@Getter
@Setter
@Table(name = "activitylog")
public class ActivityLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String sessionId;
    private String ipAddress;
    private String lastLogin;

}
