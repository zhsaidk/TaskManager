package com.task_manager.zhsaidk.http.justConrtoller;

import com.task_manager.zhsaidk.database.entity.Status;
import com.task_manager.zhsaidk.dto.CreateUpdateTaskRequest;
import com.task_manager.zhsaidk.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public String getAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                         @RequestParam(value = "size", defaultValue = "10") Integer size,
                         @RequestParam(value = "status", required = false) Status status,
                         Model model) {
        model.addAttribute("tasks", taskService.getAll(page, size, status));
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("status", status);
        model.addAttribute("statuses", Status.values());
        return "task/tasks";
    }

    @GetMapping("/create")
    public String createTaskPage(Model model) {
        model.addAttribute("task", new CreateUpdateTaskRequest());
        model.addAttribute("statuses", Status.values()); // Для выпадающего списка
        return "task/create";
    }

    @PostMapping("/create")
    public String createTask(@Valid CreateUpdateTaskRequest createUpdateTaskRequest,
                             RedirectAttributes redirectAttributes) { //TODO Нужно доделать проверку
        try{
            taskService.create(createUpdateTaskRequest);
            redirectAttributes.addFlashAttribute("message", "Успешно создано");
        }catch (IllegalStateException exception){
            redirectAttributes.addFlashAttribute("message", "Не удалось создать");
        }
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable("id") Integer id,
                           Model model) {
        model.addAttribute("task", taskService.findById(id));
        model.addAttribute("statuses", Status.values());
        return "task/edit";
    }

    @PostMapping("/edit")
    public String editTask(@Valid CreateUpdateTaskRequest createUpdateTaskRequest,
                           @RequestParam("id") Integer id,
                           RedirectAttributes redirectAttributes) {
        taskService.updateTask(id, createUpdateTaskRequest);
        redirectAttributes.addFlashAttribute("message", "Успешно обновлено");
        return "redirect:/tasks";
    }

    @PostMapping("/delete")
    public String deleteTask(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        if (taskService.deleteById(id)) {
            redirectAttributes.addFlashAttribute("message", "Успешно удалено");
        } else {
            redirectAttributes.addFlashAttribute("message", "Не удалось удалить");
        }
        return "redirect:/tasks";
    }

}
