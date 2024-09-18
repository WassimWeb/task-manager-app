package com.example.task_manager_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.task_manager_backend.model.Subtask;


@Repository
public interface SubtaskRepository extends JpaRepository<Subtask, Long> {

}
