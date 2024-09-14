package com.example.task_manager_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "createdBy")
    private Set<Task> createdTasks = new HashSet<>();

    @OneToMany(mappedBy = "assignedTo")
    private Set<Task> assignedTasks = new HashSet<>();

    @OneToMany(mappedBy = "createdBy")
    private Set<Project> createdProjects = new HashSet<>();
}