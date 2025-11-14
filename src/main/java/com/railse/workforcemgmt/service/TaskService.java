package com.railse.workforcemgmt.service;

import com.railse.workforcemgmt.dto.CreateTaskRequest;
import com.railse.workforcemgmt.dto.TaskDto;
import com.railse.workforcemgmt.model.*;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final Map<Long, Task> taskStore = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    public TaskDto createTask(CreateTaskRequest request) {
        Task task = new Task();
        task.setId(idGenerator.incrementAndGet());
        task.setTitle(request.getTitle());
        task.setStatus(TaskStatus.ACTIVE);
        task.setStartDate(request.getStartDate());
        task.setDueDate(request.getDueDate());
        task.setAssignee(request.getAssignee());

        taskStore.put(task.getId(), task);
        return TaskDto.from(task);
    }

    public List<TaskDto> getAllTasks() {
        return taskStore.values().stream()
                .map(TaskDto::from)
                .collect(Collectors.toList());
    }

    // Reassign Task and cancel old one
    public TaskDto reassignTask(Long taskId, Staff newAssignee) {
        Task existingTask = taskStore.get(taskId);
        if (existingTask == null) throw new RuntimeException("Task not found");

        if (!existingTask.getAssignee().equals(newAssignee)) {
            existingTask.setStatus(TaskStatus.CANCELLED);
            existingTask.getActivityLog().add("Task reassigned. Previous assignee: " + existingTask.getAssignee().getName());

            Task newTask = new Task();
            newTask.setId(idGenerator.incrementAndGet());
            newTask.setTitle(existingTask.getTitle());
            newTask.setStartDate(existingTask.getStartDate());
            newTask.setDueDate(existingTask.getDueDate());
            newTask.setAssignee(newAssignee);
            newTask.setStatus(TaskStatus.ACTIVE);
            newTask.setPriority(existingTask.getPriority());
            newTask.getActivityLog().add("Task created by reassignment from task ID: " + taskId);

            taskStore.put(newTask.getId(), newTask);
            return TaskDto.from(newTask);
        }
        return TaskDto.from(existingTask);
    }

    // Get non-cancelled tasks only
    public List<TaskDto> getTasksExcludingCancelled() {
        return taskStore.values().stream()
                .filter(task -> task.getStatus() != TaskStatus.CANCELLED)
                .map(TaskDto::from)
                .collect(Collectors.toList());
    }

    // Smart Daily Task View
    public List<TaskDto> getTasksInSmartRange(LocalDate from, LocalDate to) {
        return taskStore.values().stream()
                .filter(task -> task.getStatus() != TaskStatus.CANCELLED)
                .filter(task ->
                        (task.getStartDate().compareTo(from) >= 0 && task.getStartDate().compareTo(to) <= 0)
                                || (task.getStartDate().isBefore(from) && task.getStatus() != TaskStatus.COMPLETED)
                )
                .map(TaskDto::from)
                .collect(Collectors.toList());
    }

    // Update Task Priority
    public void updatePriority(Long taskId, TaskPriority priority) {
        Task task = taskStore.get(taskId);
        if (task != null) {
            task.setPriority(priority);
            task.getActivityLog().add("Priority updated to: " + priority);
        }
    }

    // Get tasks by priority
    public List<TaskDto> getTasksByPriority(TaskPriority level) {
        return taskStore.values().stream()
                .filter(t -> t.getPriority() == level && t.getStatus() != TaskStatus.CANCELLED)
                .map(TaskDto::from)
                .collect(Collectors.toList());
    }

    // Add comment and log activity
    public void addCommentToTask(Long id, String comment) {
        Task task = taskStore.get(id);
        if (task != null) {
            task.getComments().add(comment);
            task.getActivityLog().add("Comment added: " + comment);
        }
    }

    // Get task details with full history
    public Task getTaskById(Long id) {
        Task task = taskStore.get(id);
        if (task == null) throw new RuntimeException("Not found");
        return task;
    }
}
