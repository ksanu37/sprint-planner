package com.example.repositories;

import com.example.entities.Task;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class TaskRepository {

    private Map<UUID, Task> taskMap;
    private static TaskRepository instance;

    public static TaskRepository getInstance() {
        if(instance == null) {
            instance = new TaskRepository();
        }
        return instance;
    }
    private TaskRepository() {
        this.taskMap = new HashMap<>();
    }

    public Task addTask(Task task) {
        this.taskMap.put(task.getId(), task);
        return task;
    }

    public Task getTask(UUID taskId) {
        return this.taskMap.get(taskId);
    }
}
