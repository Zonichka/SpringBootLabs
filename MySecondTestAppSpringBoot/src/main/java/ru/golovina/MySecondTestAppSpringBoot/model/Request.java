package ru.golovina.MySecondTestAppSpringBoot.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    @NotNull
    @Size(min=1, max = 32, message = "UID должен содержать не более 32 символов")
    @NotBlank(message = "UID is mandatory")
    private String uid;

    @NotBlank(message = "operationUid is mandatory")
    @Size(min=1, max = 32, message = "operationUid должен содержать не более 32 символов")
    private String operationUid;

    private String systemName;

    @NotBlank(message = "systemTime is mandatory")
    private String systemTime;

    private String source;

    @Min(value = 1, message = "communicationId должен содержать как минимум 1 символов")
    @Max(value = 100000, message = "communicationId должен содержать не более 100000 символов")
    private int communicationId;

    private int templateId;
    private int productCode;
    private int snsCode;
}
