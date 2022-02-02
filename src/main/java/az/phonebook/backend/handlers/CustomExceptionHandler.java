package az.phonebook.backend.handlers;

import az.phonebook.backend.dto.feign.response.ErrorResponseDto;
import az.phonebook.backend.exceptions.AuthorizationException;
import az.phonebook.backend.exceptions.FailedToGetSuccessfulResponseException;
import az.phonebook.backend.exceptions.RecordNotFoundException;
import az.phonebook.backend.exceptions.ValidationException;
import az.phonebook.backend.logger.MainLogger;
import feign.RetryableException;
import java.time.LocalDateTime;
import javax.validation.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    public static final String VALIDATION_FAILED_MSG = "Validation failed";
    public static final String SERVICE_TIMED_OUT_MSG = "Service timed out";
    public static final String SERVICE_FAILED_MSG = "Service failed";
    public static final String RECORD_NOT_FOUND_MSG = "Record not found";
    public static final String INTERNAL_ERROR_MSG = "Internal error";

    public static final String LOG_MSG_TMP = "%s: %s";

    private static final MainLogger CUSTOM_LOGGER = MainLogger.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleExceptions(Exception ex, WebRequest request) {

        return buildResponseEntity(ex, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        return getResponseEntity(ex, status, request, VALIDATION_FAILED_MSG);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            @Nullable Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        return getResponseEntity(ex, status, request, VALIDATION_FAILED_MSG);
    }

    private ResponseEntity<Object> buildResponseEntity(Exception ex, WebRequest request) {

        String message;
        HttpStatus status;

        if (ex instanceof ConstraintViolationException
                || ex instanceof ValidationException) {
            status = HttpStatus.BAD_REQUEST;
            message = VALIDATION_FAILED_MSG;
        } else if (ex instanceof RecordNotFoundException) {
            status = HttpStatus.NOT_FOUND;
            message = RECORD_NOT_FOUND_MSG;
        } else if (ex instanceof ResourceAccessException
                || ex instanceof RetryableException) {
            status = HttpStatus.GATEWAY_TIMEOUT;
            message = SERVICE_TIMED_OUT_MSG;
        } else if (ex instanceof FailedToGetSuccessfulResponseException) {
            status = HttpStatus.BAD_GATEWAY;
            message = SERVICE_FAILED_MSG;
        } else if (ex instanceof AuthorizationException) {
            status = HttpStatus.UNAUTHORIZED;
            message = SERVICE_FAILED_MSG;
        } else if (ex instanceof DataAccessException) {
            status = HttpStatus.BAD_REQUEST;
            message = INTERNAL_ERROR_MSG;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            message = INTERNAL_ERROR_MSG;
        }
        return getResponseEntity(ex, status, request, message);
    }

    private ResponseEntity<Object> getResponseEntity(
            Exception ex, HttpStatus status, WebRequest request, String message) {

        CUSTOM_LOGGER.error(String.format(LOG_MSG_TMP, ex.getClass().getSimpleName(), ex.getMessage()), ex);

        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .errorDetail(ex.getMessage())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorResponseDto, status);
    }
}
