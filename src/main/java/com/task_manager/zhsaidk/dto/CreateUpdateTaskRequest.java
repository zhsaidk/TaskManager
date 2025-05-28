package com.task_manager.zhsaidk.dto;

import com.task_manager.zhsaidk.database.entity.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUpdateTaskRequest {
    @NotBlank(message = "title не должен быть null or blank")
    String title;
    @NotBlank(message = "description не должен быть null or blank")
    String description;
    @NotNull(message = "message не должен быть null")
    Status status;
}
