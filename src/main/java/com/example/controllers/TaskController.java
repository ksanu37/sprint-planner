package com.example.controllers;

import com.example.entities.Sprint;
import com.example.entities.Task;
import com.example.enums.TaskStatus;
import com.example.enums.TaskType;
import com.example.services.SprintService;
import com.example.services.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final SprintService sprintService;

    public Task createTask(final String title, final String description,
                           final UUID userId, final UUID sprintId, final TaskType type) {
        validateTaskCreation(title, description, userId, sprintId, type);
        Task task = Task.builder()
                .id(UUID.randomUUID())
                .title(title)
                .description(description)
                .userId(userId)
                .sprintId(sprintId)
                .type(type)
                .build();
        Task task1 = this.taskService.addTask(task);
        this.sprintService.addTaskToSprint(sprintId, task1.getId());

        return task1;
    }

    public Task updateTaskStatus(final UUID taskId, final TaskStatus updatedStatus) {
        return this.taskService.updateTask(taskId, updatedStatus);
    }

    private void validateTaskCreation(final String title, final String description,
                                     final UUID userId, final UUID sprintId, final TaskType type) {
        if(Objects.isNull(title) || Objects.isNull(description) || Objects.isNull(userId) || Objects.isNull(sprintId) || Objects.isNull(type)) {
            throw new IllegalArgumentException("Task creation failed: One of the input fields is missing");
        }
    }
}
