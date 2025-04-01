package com.example.services;

import com.example.entities.Sprint;
import com.example.entities.Task;
import com.example.enums.TaskStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface SprintService {
    Sprint createSprint(Sprint sprint);

    void addTaskToSprint(UUID sprintId, UUID taskId);
    void removeTaskFromSprint(UUID sprintId, UUID taskId);

    List<Task> getAllTasksInSprint(UUID sprintId);
    List<Task> getAllTasksAssignedToUserInSprint(UUID sprintId, UUID userId);
    List<Task> getDelayedTasks(UUID sprintId);
    Task updateTask(UUID taskId, TaskStatus taskStatus);
    Task updateTask(UUID taskId, LocalDate updatedOn);
}
