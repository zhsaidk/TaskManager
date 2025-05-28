package com.task_manager.zhsaidk.http.restController;

import com.task_manager.zhsaidk.database.entity.Status;
import com.task_manager.zhsaidk.database.entity.Task;
import com.task_manager.zhsaidk.dto.CreateUpdateTaskRequest;
import com.task_manager.zhsaidk.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskRestController {
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<Page<Task>> getAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                                             @RequestParam(value = "status", required = false) Status status){
        return ResponseEntity.ok(taskService.getAll(page, size, status));
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody CreateUpdateTaskRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.create(request));
    }

    @PutMapping
    public ResponseEntity<Task> updateTask(@Valid @RequestBody CreateUpdateTaskRequest request,
                                           @RequestParam(value = "id") Integer id){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(taskService.updateTask(id, request));
    }

    @DeleteMapping
    public ResponseEntity<Task> deleteTask(Integer id){
        return taskService.deleteById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
