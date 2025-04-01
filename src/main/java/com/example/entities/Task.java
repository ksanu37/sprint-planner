package com.example.entities;

import com.example.enums.TaskStatus;
import com.example.enums.TaskType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Task {
    private UUID id;
    private String title;
    private String description;
    @Builder.Default
    private LocalDate createdOn = LocalDate.now();
    @Builder.Default
    private LocalDate updatedOn = LocalDate.now();
    private UUID userId;
    private UUID sprintId;
    private TaskType type;
    @Builder.Default
    private TaskStatus status = TaskStatus.TODO;

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                ", userId=" + userId +
                ", Added to sprint =" + sprintId +
                ", type=" + type +
                ", status=" + status +
                '}';
    }
}
