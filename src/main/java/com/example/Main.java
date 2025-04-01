package com.example;

import com.example.controllers.SprintController;
import com.example.controllers.TaskController;
import com.example.controllers.UserController;
import com.example.entities.Sprint;
import com.example.entities.Task;
import com.example.entities.User;
import com.example.enums.TaskStatus;
import com.example.enums.TaskType;
import com.example.services.SprintService;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.util.List;

@Log4j2
@SpringBootApplication
public class Main {
    public static final String DIVIDER = "____________\n";


    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Main.class, args);
        UserController userController = ctx.getBean(UserController.class);


        User user = userController.addUser("Sanu", "abcd@gmail.com");
        log.info("Added {}", user);

        SprintController sprintController = ctx.getBean(SprintController.class);
        log.info(DIVIDER);
        Sprint sprint = sprintController.createSprint("Sprint 1", LocalDate.now(), LocalDate.now().plusDays(14));
        log.info("Added {}", sprint);

        TaskController taskController = ctx.getBean(TaskController.class);
        Task task =  taskController.createTask("Task 1", "Test Desc", user.getId(), sprint.getId(), TaskType.FEATURE);
        log.info("Added {}", task);

        Task task1 =  taskController.createTask("Task 2", "Test Desc", user.getId(), sprint.getId(), TaskType.FEATURE);
        log.info("Added {}", task1);

        Task task2 =  taskController.createTask("Task 3", "Test Desc", user.getId(), sprint.getId(), TaskType.FEATURE);
        log.info("Added {}", task2);
//        log.info("Removing task {}", task.getTitle());
//        sprintController.removeTaskFromSprint(task.getId(), sprint.getId());
//
        log.info(DIVIDER);
        log.info("Getting all tasks in sprint {}", sprint.getId());
        SprintService sprintService = ctx.getBean(SprintService.class);
        List<Task> taskList = sprintService.getAllTasksInSprint(sprint.getId());
        taskList.forEach(x -> log.info(x.getTitle()));

        Task updateTask =  sprintService.updateTask(task.getId(), TaskStatus.INRPROGRESS);

        log.info("Updated {}", updateTask);
        updateTask =  sprintService.updateTask(task1.getId(), TaskStatus.INRPROGRESS);
        log.info("Updated {}", updateTask);
        updateTask =  sprintService.updateTask(task1.getId(), TaskStatus.DONE);
        log.info("Updated {}", updateTask);
        updateTask =  sprintService.updateTask(task2.getId(), TaskStatus.INRPROGRESS);
        log.info("Updated {}", updateTask);

        log.info(DIVIDER);
        log.info("Getting All tasks assigned to user: {}, in sprint: {}", user.getName(), sprint.getName());
        List<Task> userTaskList = sprintService.getAllTasksAssignedToUserInSprint(sprint.getId(), user.getId());
        userTaskList.forEach(log::info);

        log.info(DIVIDER);
        log.info("GETTING DELAYED TASKS");
        sprintService.updateTask(task2.getId(), LocalDate.now().plusDays(24));
        List<Task> delayedTasks = sprintService.getDelayedTasks(sprint.getId());
        delayedTasks.forEach(log::info);
    }


}