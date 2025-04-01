package com.example.services;

import com.example.entities.Task;
import com.example.enums.TaskStatus;

import java.util.UUID;

public interface TaskService {
    Task addTask(Task task);
    Task getTask(UUID taskId);

    Task updateTask(UUID taskId, TaskStatus taskStatus);
}
