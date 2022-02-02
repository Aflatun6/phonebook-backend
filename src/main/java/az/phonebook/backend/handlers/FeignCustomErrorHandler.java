package az.phonebook.backend.handlers;

import az.phonebook.backend.dto.feign.response.ErrorResponseDto;
import az.phonebook.backend.exceptions.AuthorizationException;
import az.phonebook.backend.exceptions.FailedToGetSuccessfulResponseException;
import az.phonebook.backend.exceptions.RecordNotFoundException;
import az.phonebook.backend.logger.MainLogger;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import javax.validation.ConstraintViolationException;


public class FeignCustomErrorHandler implements ErrorDecoder {
    public static final String ERR_MSG_TEMP = "Got status %d while reading %s with message: %s";
    private static final MainLogger LOGGER = MainLogger.getLogger(FeignCustomErrorHandler.class);
    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 400) {
            return new ConstraintViolationException(getErrorDetail(response), null);
        }
        if (response.status() == 404) {
            return new RecordNotFoundException(getErrorDetail(response));
        }
        if (response.status() == 401) {
            return new AuthorizationException("Authentication to dfg service failed");
        }
        if (response.status() >= 400 && response.status() <= 599) {
            return new FailedToGetSuccessfulResponseException(
                    String.format(ERR_MSG_TEMP, response.status(), methodKey, getErrorDetail(response)));
        }
        return defaultErrorDecoder.decode(methodKey, response);
    }

    private String getErrorDetail(Response response) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        try {
            if (response.body() != null) {
                ErrorResponseDto errorDto;
                errorDto = objectMapper.readValue(response.body().asReader(), ErrorResponseDto.class);
                if (errorDto != null) {
                        return errorDto.getErrorDetail();
                    }
                }
            }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }
}
