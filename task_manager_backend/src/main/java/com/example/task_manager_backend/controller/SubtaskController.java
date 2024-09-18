package com.example.task_manager_backend.controller;

import com.example.task_manager_backend.model.Subtask;
import com.example.task_manager_backend.service.SubtaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subtasks")
public class SubtaskController {

    @Autowired
    private SubtaskService subtaskService;

    @GetMapping
    public List<Subtask> getAllSubtasks() {
        return subtaskService.getAllSubtasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subtask> getSubtaskById(@PathVariable Long id) {
        return ResponseEntity.ok(subtaskService.getSubtaskById(id));
    }

    @PostMapping
    public ResponseEntity<Subtask> createSubtask(@RequestBody Subtask subtask) {
        return ResponseEntity.ok(subtaskService.createSubtask(subtask));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subtask> updateSubtask(@PathVariable Long id, @RequestBody Subtask updatedSubtask) {
        return ResponseEntity.ok(subtaskService.updateSubtask(id, updatedSubtask));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubtask(@PathVariable Long id) {
        subtaskService.deleteSubtask(id);
        return ResponseEntity.noContent().build();
    }
}
