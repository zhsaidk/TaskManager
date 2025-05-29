package com.task_manager.zhsaidk.service;

import com.task_manager.zhsaidk.database.entity.Status;
import com.task_manager.zhsaidk.database.entity.Task;
import com.task_manager.zhsaidk.database.repo.TaskRepository;
import com.task_manager.zhsaidk.database.repo.TaskSpecification;
import com.task_manager.zhsaidk.dto.CreateUpdateTaskRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public Page<Task> getAll(Integer page, Integer size, Status status){
        return taskRepository.findAll(TaskSpecification.filterByStatus(status), PageRequest.of(page, size, Sort.by("id")));
    }

    public Task create(CreateUpdateTaskRequest createUpdateTaskRequest){
        try{
            Task currentTask = Task.builder()
                    .title(createUpdateTaskRequest.getTitle())
                    .description(createUpdateTaskRequest.getDescription())
                    .status(createUpdateTaskRequest.getStatus())
                    .build();
            return taskRepository.save(currentTask);
        }catch (Exception exception){
            log.error("Произошла ошибка во время создание task");
            throw new IllegalStateException("Произошла ошибка во время создание task");
        }
    }

    public Task findById(Integer id){
        return taskRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "NotFound"));
    }

    public Task updateTask(Integer id, CreateUpdateTaskRequest request){
        return taskRepository.findById(id)
                .map(task->{
                    task.setTitle(request.getTitle());
                    task.setDescription(request.getDescription());
                    task.setStatus(request.getStatus());
                    return taskRepository.save(task);
                })
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_MODIFIED, "Id не существует, не удалось удалить"));
    }

    public boolean deleteById(Integer id){
        if(taskRepository.existsById(id)){
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
