package com.example.repositories;

import com.example.entities.Sprint;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class SprintRepository {

    private Map<UUID, Sprint> sprintMap;
    private static SprintRepository instance;

    public static SprintRepository getInstance() {
        if(instance == null) {
            instance = new SprintRepository();
        }
        return instance;
    }
    private SprintRepository() {
        this.sprintMap = new HashMap<>();
    }

    public Sprint addSprint(Sprint sprint) {
        this.sprintMap.put(sprint.getId(), sprint);
        return sprint;
    }

    public Sprint getSprint(UUID sprintId) {
        return this.sprintMap.get(sprintId);
    }


}
