package com.railse.workforcemgmt.dto;
import com.railse.workforcemgmt.model.Task;
import com.railse.workforcemgmt.model.TaskStatus;
import com.railse.workforcemgmt.model.TaskPriority;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskDto {
    private Long id;
    private String title;
    private TaskStatus status;
    private LocalDate startDate;
    private LocalDate dueDate;
    private String assigneeName;
    private TaskPriority priority;

    public static TaskDto from(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setStatus(task.getStatus());
        dto.setStartDate(task.getStartDate());
        dto.setDueDate(task.getDueDate());
        dto.setPriority(task.getPriority());
        dto.setAssigneeName(task.getAssignee() != null ? task.getAssignee().getName() : null);
        return dto;
    }

}
