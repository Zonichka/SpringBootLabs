package ru.golovina.MySecondTestAppSpringBoot.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org. springframework.http.HttpStatus;
import org. springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.golovina.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.golovina.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.golovina.MySecondTestAppSpringBoot.model.Request;
import ru.golovina.MySecondTestAppSpringBoot.model.Response;
import ru.golovina.MySecondTestAppSpringBoot.service.ValidationService;

import java.text.SimpleDateFormat;
import java.util.List;

@RestController
public class MyController {
    private final ValidationService validationService;

    @Autowired
    public MyController(ValidationService validationService) {
        this.validationService = validationService;
    }
    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request, BindingResult bindingResult) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(request.getSystemTime())
                .code("success")
                .errorCode("")
                .errorMessage("")
                .build();

        try {
            if ("123".equals(request.getUid())) {
                throw new UnsupportedCodeException("Unsupported uid value: 123");
            }
            if (bindingResult.hasErrors()) {
                List<FieldError> fieldErrors = bindingResult.getFieldErrors();
                StringBuilder errorMessage = new StringBuilder("Validation errors: ");
                for (FieldError error : fieldErrors) {
                    errorMessage.append(String.format("%s - %s;%n ", error.getField(), error.getDefaultMessage()));
                }
                response.setCode("failed");
                response.setErrorCode("ValidationException");
                response.setErrorMessage(errorMessage.toString());
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            validationService.isValid(bindingResult);
        } catch (ValidationFailedException e) {
            response.setCode("failed");
            response.setErrorCode("ValidationException");
            response.setErrorMessage("Ошибка валидации");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (UnsupportedCodeException e) {
            response.setCode("failed");
            response.setErrorCode("UnsupportedCodeException");
            response.setErrorMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.setCode("failed");
            response.setErrorCode("Unknown Exception");
            response.setErrorMessage("Произошла непредвиденная ошибка");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

