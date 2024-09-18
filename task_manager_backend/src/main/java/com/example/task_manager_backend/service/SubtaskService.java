package com.example.task_manager_backend.service;

import com.example.task_manager_backend.model.Subtask;
import com.example.task_manager_backend.repository.SubtaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubtaskService {

    @Autowired
    private SubtaskRepository subtaskRepository;

    public List<Subtask> getAllSubtasks() {
        return subtaskRepository.findAll();
    }

    public Subtask getSubtaskById(Long id) {
        return subtaskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subtask not found"));
    }

    public Subtask createSubtask(Subtask subtask) {
        return subtaskRepository.save(subtask);
    }

    public Subtask updateSubtask(Long id, Subtask updatedSubtask) {
        Subtask subtask = getSubtaskById(id);
        subtask.setTitle(updatedSubtask.getTitle());
        subtask.setDescription(updatedSubtask.getDescription());
        return subtaskRepository.save(subtask);
    }

    public void deleteSubtask(Long id) {
        subtaskRepository.deleteById(id);
    }
}
