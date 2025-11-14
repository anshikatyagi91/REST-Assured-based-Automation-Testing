package com.railse.workforcemgmt.dto;
import com.railse.workforcemgmt.model.Staff;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateTaskRequest {
    private String title;
    private LocalDate startDate;
    private LocalDate dueDate;
    private Staff assignee;
}
