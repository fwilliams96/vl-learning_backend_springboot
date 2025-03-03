package com.williamsdreams.vl_learning.app.handlers;

import com.williamsdreams.vl_learning.app.api.dto.ErrorDto;
import com.williamsdreams.vl_learning.app.api.dto.ErrorMessageDto;
import com.williamsdreams.vl_learning.users.domain.UserAlreadyExistsError;
import com.williamsdreams.vl_learning.users.domain.UserInvalidCredentialsError;
import com.williamsdreams.vl_learning.users.domain.UserNotFoundError;
import com.williamsdreams.vl_learning.users.shared.domain.BaseException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class UsersExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsError.class)
    @ResponseStatus(value = BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorDto> userAlreadyExists(
            HttpServletRequest request,
            UserAlreadyExistsError exception
    ) {
        log.error("User already exists", exception);
        return buildErrorResponse(exception, BAD_REQUEST, request);
    }

    @ExceptionHandler(UserNotFoundError.class)
    @ResponseStatus(value = NOT_FOUND)
    @ResponseBody
    public ResponseEntity<ErrorDto> userNotFound(
            HttpServletRequest request,
            UserNotFoundError exception
    ) {
        log.error("User not found exists", exception);
        return buildErrorResponse(exception, NOT_FOUND, request);
    }

    @ExceptionHandler(UserInvalidCredentialsError.class)
    @ResponseStatus(value = BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorDto> userCredentialsInvalid(
            HttpServletRequest request,
            UserInvalidCredentialsError exception
    ) {
        log.error("User invalid credentials", exception);
        return buildErrorResponse(exception, BAD_REQUEST, request);
    }

    private ResponseEntity<ErrorDto> buildErrorResponse(
            BaseException ex,
            HttpStatus status,
            HttpServletRequest request
    ) {
        ErrorDto errorResponse = new ErrorDto();
        errorResponse.setTimestamp(new Date().getTime());
        errorResponse.setStatus(status.value());
        errorResponse.setError(ex.getMessage());
        errorResponse.setPath(request.getServletPath());

        if (ex.hasErrors()) {
            ex.getErrors().forEach(error -> errorResponse.addErrorsItem(
                    new ErrorMessageDto().code(error.getCode()).message(error.getMessage())
            ));
        }

        return ResponseEntity.status(status).body(errorResponse);
    }

}