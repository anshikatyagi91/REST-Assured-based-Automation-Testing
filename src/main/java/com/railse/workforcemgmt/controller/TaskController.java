package com.railse.workforcemgmt.controller;

import com.railse.workforcemgmt.dto.CreateTaskRequest;
import com.railse.workforcemgmt.dto.TaskDto;
import com.railse.workforcemgmt.model.Staff;
import com.railse.workforcemgmt.model.Task;
import com.railse.workforcemgmt.model.TaskPriority;
import com.railse.workforcemgmt.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public TaskDto createTask(@RequestBody CreateTaskRequest request) {
        return taskService.createTask(request);
    }

    @GetMapping
    public List<TaskDto> getAllTasks() {
        return taskService.getAllTasks();
    }

    // Endpoint: Get active (non-cancelled) tasks
    @GetMapping("/active")
    public List<TaskDto> getActiveTasks() {
        return taskService.getTasksExcludingCancelled();
    }

    // Endpoint: Reassign task to a new staff
    @PostMapping("/reassign")
    public TaskDto reassignTask(@RequestParam Long taskId, @RequestBody Staff newAssignee) {
        return taskService.reassignTask(taskId, newAssignee);
    }

    // Endpoint: Smart daily view
    @GetMapping("/smart")
    public List<TaskDto> getSmartDailyView(@RequestParam String from, @RequestParam String to) {
        return taskService.getTasksInSmartRange(LocalDate.parse(from), LocalDate.parse(to));
    }

    // Endpoint: Get tasks by priority
    @GetMapping("/priority/{level}")
    public List<TaskDto> getByPriority(@PathVariable TaskPriority level) {
        return taskService.getTasksByPriority(level);
    }

    // Endpoint: Change task priority
    @PutMapping("/priority")
    public void changePriority(@RequestParam Long taskId, @RequestParam TaskPriority level) {
        taskService.updatePriority(taskId, level);
    }

    // Endpoint: Add a comment to a task
    @PostMapping("/{id}/comment")
    public void addComment(@PathVariable Long id, @RequestParam String comment) {
        taskService.addCommentToTask(id, comment);
    }

    // Endpoint: Get task details (with full history)
    @GetMapping("/{id}")
    public Task getTaskDetails(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }
}

