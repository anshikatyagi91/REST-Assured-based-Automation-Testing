package com.railse.workforcemgmt.model;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class Task {
    private Long id;
    private String title;
    private TaskStatus status;
    private LocalDate startDate;
    private LocalDate dueDate;
    private Staff assignee;
    private TaskPriority priority = TaskPriority.MEDIUM;

    private List<String> comments = new ArrayList<>();
    private List<String> activityLog = new ArrayList<>();

}
