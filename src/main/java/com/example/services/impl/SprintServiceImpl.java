package com.example.services.impl;

import com.example.entities.Sprint;
import com.example.entities.Task;
import com.example.enums.TaskStatus;
import com.example.repositories.SprintRepository;
import com.example.repositories.SprintTaskMappingRepository;
import com.example.services.SprintService;
import com.example.services.TaskService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
public class SprintServiceImpl implements SprintService {
    private final SprintRepository sprintRepository;
    private final SprintTaskMappingRepository sprintTaskMappingRepository;
    private final TaskService taskService;
    @Override
    public Sprint createSprint(Sprint sprint) {
        return this.sprintRepository.addSprint(sprint);
    }

    @Override
    public void addTaskToSprint(UUID sprintId, UUID taskId) {
        this.sprintTaskMappingRepository.addSprintTaskMapping(sprintId, taskId);
    }

    @Override
    public void removeTaskFromSprint(UUID sprintId, UUID taskId) {
        Set<UUID> taks = this.sprintTaskMappingRepository.getAllTasksBySprintId(sprintId);
        if(!taks.contains(taskId)) {
            throw new IllegalArgumentException("Task is not present in the sprint");
        }
        taks.remove(taskId);
    }

    @Override
    public List<Task> getAllTasksInSprint(UUID sprintId) {
        List<Task> taskList = new ArrayList<>();
        Set<UUID> tasks = this.sprintTaskMappingRepository.getAllTasksBySprintId(sprintId);
        tasks.forEach(taskId -> taskList.add(this.taskService.getTask(taskId)));
        return taskList;
    }

    @Override
    public List<Task> getAllTasksAssignedToUserInSprint(UUID sprintId, UUID userId) {
        List<Task> task = getAllTasksInSprint(sprintId);
        return task.stream().filter(x -> x.getUserId().equals(userId)).collect(Collectors.toList());
    }

    private List<Task> getAllInProgressTasksAssignedToUserInSprint(UUID sprintId, UUID userId) {
        List<Task> task = getAllTasksInSprint(sprintId);
        return task.stream().filter(x -> x.getUserId().equals(userId) && x.getStatus().equals(TaskStatus.INRPROGRESS)).collect(Collectors.toList());
    }

    @Override
    public Task updateTask(UUID taskId, TaskStatus taskStatus) {
        Task task = taskService.getTask(taskId);
        log.info("Task is assigned to {} in sprint {}", task.getUserId(), task.getSprintId());
        if(taskStatus.equals(TaskStatus.INRPROGRESS) && getAllInProgressTasksAssignedToUserInSprint(task.getSprintId(), task.getUserId()).size() > 1){
            throw new RuntimeException("User can't have more than 2 task in progress in the same sprint");
        }
        return taskService.updateTask(taskId, taskStatus);
    }

    @Override
    public Task updateTask(UUID taskId, LocalDate updatedOn) {
        Task task = taskService.getTask(taskId);
        task.setUpdatedOn(updatedOn);
        return taskService.addTask(task);
    }

    @Override
    public List<Task> getDelayedTasks(UUID sprintId) {
        Sprint sprint = this.sprintRepository.getSprint(sprintId);
        List<Task> task = getAllTasksInSprint(sprintId);
        return task.stream().filter(x -> x.getUpdatedOn().isAfter(sprint.getEndDate())) .collect(Collectors.toList());
    }
}
