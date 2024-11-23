package ru.golovina.MySecondTestAppSpringBoot.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org. springframework.http.HttpStatus;
import org. springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.golovina.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.golovina.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.golovina.MySecondTestAppSpringBoot.model.*;
import ru.golovina.MySecondTestAppSpringBoot.service.ValidationService;
import ru.golovina.MySecondTestAppSpringBoot.util.DateTimeUtil;
import ru.golovina.MySecondTestAppSpringBoot.service.ModifyResponceService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
public class MyController {
    private final ValidationService validationService;

    private final ModifyResponceService modifyResponceService;

    @Autowired
    public MyController(ValidationService validationService,
                        @Qualifier("ModifySystemTimeResponceService") ModifyResponceService modifyResponceService) {
        this.modifyResponceService = modifyResponceService;
        this.validationService = validationService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request, BindingResult bindingResult) {

//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        log.info("Received request: {}", request);
        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();
        log.info("Initialized response: {}", response);


        try {
            // Проверка UID
            if ("123".equals(request.getUid())) {
                log.info("Unsupported UID detected: {}", request.getUid());
                throw new UnsupportedCodeException("Unsupported uid value: 123");
            }

            // Проверка на ошибки валидации
            if (bindingResult.hasErrors()) {
                log.error("Validation errors found in request: {}", request);
                List<FieldError> fieldErrors = bindingResult.getFieldErrors();
                StringBuilder errorMessage = new StringBuilder("Validation errors: ");
                for (FieldError error : fieldErrors) {
                    String fieldErrorMessage = String.format("%s - %s", error.getField(), error.getDefaultMessage());
                    log.error("Validation error: {}", fieldErrorMessage);
                    errorMessage.append(fieldErrorMessage).append(";\n ");
                }

                response.setCode(Codes.FAILED);
                response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
                response.setErrorMessage(ErrorMessages.UNKNOWN);
                log.info("Updated response with validation errors: {}", response);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            // Дополнительная проверка в сервисе валидации
            validationService.isValid(bindingResult);
            log.info("Validation passed successfully for request: {}", request);

        } catch (ValidationFailedException e) {
            log.error("Validation failed for request: {}, error: {}", request, e.getMessage(), e);
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessages.VALIDATION);
            log.info("Updated response with validation exception: {}", response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (UnsupportedCodeException e) {
            log.error("UnsupportedCodeException occurred for request: {}, error: {}", request, e.getMessage(), e);
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNSUPPORTED_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNKNOWN);
            log.info("Updated response with unsupported exception: {}", response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Unexpected exception occurred for request: {}, error: {}", request, e.getMessage(), e);
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNKNOWN_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNKNOWN);
            log.info("Updated response with unknown exception: {}", response);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Модификация ответа через сервис
        modifyResponceService.modify(response);
        log.info("Response modified by ModifyResponceService: {}", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}