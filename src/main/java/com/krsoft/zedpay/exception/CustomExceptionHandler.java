package com.krsoft.zedpay.exception;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import com.krsoft.zedpay.utils.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
@ControllerAdvice
public class CustomExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionHandler.class);

    private final MappingErrorTypeHttpStatus mappingErrorStatus = new MappingErrorTypeHttpStatus();

    private ResponseEntity<ErrorDetails> getResponseForException(Exception ex, String code, String customMessage, HttpStatus status, WebRequest request) {
        final String message = customMessage != null
                ? customMessage
                : ex.getMessage();
        LOGGER.error(message, ex);
        return new ResponseEntity<>(
                new ErrorDetails(code, message, request.getDescription(false)),
                status);
    }

    private ResponseEntity<ErrorDetails> getResponseForException(Exception ex, String code, HttpStatus status, WebRequest request) {
        return getResponseForException(ex, code, null, status, request);
    }

    private ResponseEntity<ErrorDetails> getResponseForMiscellaneaException(MiscellaneaException ex, WebRequest request) {
        return getResponseForException(ex,
                ex.getErrorTypeEnum().name(),
                getStatusFromErrorType(ex.getErrorTypeEnum()),
                request);
    }

    private HttpStatus getStatusFromErrorType(ErrorTypeEnum errorType) {
        final HttpStatus status = mappingErrorStatus.getHttpStatus(errorType);
        return status != null ? status : HttpStatus.BAD_REQUEST;
    }

    /**
     * Mapping exceptions
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({
        JsonMappingException.class,
        JsonParseException.class,
        MismatchedInputException.class,
        HttpMessageNotReadableException.class})
    @ResponseBody
    public final ResponseEntity<ErrorDetails> handleMappingExceptions(Exception ex, WebRequest request) {
        final ErrorTypeEnum errorType = ErrorTypeEnum.MAPPING_ERROR;
        return getResponseForException(ex,
                errorType.name(),
                getStatusFromErrorType(errorType),
                request);
    }



    @ExceptionHandler(MiscellaneaException.class)
    @ResponseBody
    public final ResponseEntity<ErrorDetails> handleZedpayException(MiscellaneaException ex, WebRequest request) {
        return getResponseForMiscellaneaException(ex, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public final ResponseEntity<ErrorDetails> handleForbiddenException(AccessDeniedException ex, WebRequest request) {
        final ErrorTypeEnum errorType = ErrorTypeEnum.FORBIDDEN_ACCESS;
        return getResponseForException(ex, errorType.name(), ex.getMessage(), getStatusFromErrorType(errorType), request);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {
        final ErrorTypeEnum errorType = ErrorTypeEnum.SERVER_ERROR;
        final String message = MessageUtils.getInstance().getMessage("internal.error");
        return getResponseForException(ex, errorType.name(), message, getStatusFromErrorType(errorType), request);
    }

}
