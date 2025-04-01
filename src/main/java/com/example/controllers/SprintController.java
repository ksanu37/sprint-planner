package com.example.controllers;

import com.example.entities.Sprint;
import com.example.entities.User;
import com.example.services.SprintService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SprintController {
    private final SprintService sprintService;

    public Sprint createSprint(final String name, final LocalDate startDate, LocalDate endDate) {
        validateSprintAddition(name, startDate, endDate);
        Sprint sprint = new Sprint(UUID.randomUUID(), name, startDate, endDate);
        return this.sprintService.createSprint(sprint);
    }

    public void validateSprintAddition(final String name, final LocalDate startDate, LocalDate endDate) {
        if(Objects.isNull(name) || Objects.isNull(startDate) || Objects.isNull(endDate)) {
            throw new IllegalArgumentException("Spring Addition failed: one of the inputs is empty");
        }
        if(endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Spring Addition failed: start date is after end Date");

        }
    }

    public void removeTaskFromSprint(final UUID taskId, final  UUID sprintId) {
        this.sprintService.removeTaskFromSprint(sprintId, taskId);
    }
}
