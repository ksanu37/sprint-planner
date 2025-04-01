package com.example.repositories;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class SprintTaskMappingRepository {
    private Map<UUID, Set<UUID>> sprintTaskMapping = new HashMap<>(); // Sprint -> Task Mapping
    private static SprintTaskMappingRepository instance;
    private static final int MAX_SIZE = 20;
    public static SprintTaskMappingRepository getInstance() {
        if(instance == null) {
            instance = new SprintTaskMappingRepository();
        }
        return instance;
    }
    private SprintTaskMappingRepository() {
        this.sprintTaskMapping = new HashMap<>();
    }

    public void addSprintTaskMapping(UUID sprintId, UUID taskId) {
        Set<UUID> taskList = getAllTasksBySprintId(sprintId);
        if(taskList.size() == MAX_SIZE) {
            throw new RuntimeException("There can not be more than 20 tasks in a sprint");
        }
        if(taskList.contains(taskId)) {
            throw new IllegalArgumentException("Task Already present is sprint");
        }
        taskList.add(taskId);
        this.sprintTaskMapping.put(sprintId, taskList);
    }

    public Set<UUID> getAllTasksBySprintId(UUID sprintId) {
        return this.sprintTaskMapping.getOrDefault(sprintId, new HashSet<>());
    }

}
