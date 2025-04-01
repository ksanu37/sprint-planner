package com.example.services.impl;

import com.example.entities.Task;
import com.example.enums.TaskStatus;
import com.example.repositories.TaskRepository;
import com.example.services.SprintService;
import com.example.services.TaskService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@AllArgsConstructor
@Log4j2
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    @Override
    public Task addTask(Task task) {
        return this.taskRepository.addTask(task);
    }

    public Task getTask(UUID taskId) {
        return this.taskRepository.getTask(taskId);
    }

    @Override
    public Task updateTask(UUID taskId, TaskStatus taskStatus) {
        Task task = getTask(taskId);
        validateTaskTransition(task, taskStatus);
        task.setStatus(taskStatus);
        task.setUpdatedOn(LocalDate.now());
        task = this.taskRepository.addTask(task);
        return task;
    }

    private void validateTaskTransition(final Task task, final TaskStatus updatedStatus) {
        TaskStatus validTaskStatus;
        switch (task.getStatus()) {
            case TODO -> validTaskStatus = TaskStatus.INRPROGRESS;
            case INRPROGRESS -> validTaskStatus = TaskStatus.DONE;
            default -> {
                log.error("Task status can't be updated from {} to {}", task.getStatus().name(), updatedStatus);
                throw new RuntimeException("Task can't be updated");
            }
        }

        if(validTaskStatus != updatedStatus) {
            log.error("Task status can't be updated from {} to {}", task.getStatus().name(), updatedStatus);
            throw new RuntimeException("Task can't be updated");
        }
    }
}
