package cm.xenonbyte.gestitre.application.common.exception;

import cm.xenonbyte.gestitre.application.common.dto.ErrorApiResponse;
import cm.xenonbyte.gestitre.application.common.dto.ValidationError;
import cm.xenonbyte.gestitre.application.common.in18.LocalizationService;
import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainBadException;
import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainConflictException;
import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainNotFoundException;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;
import org.jboss.resteasy.reactive.server.UnwrapException;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;
import static jakarta.ws.rs.core.Response.Status.CONFLICT;
import static jakarta.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

/**
 * @author bamk
 * @version 1.0
 * @since 02/11/2024
 */

@UnwrapException({BaseDomainBadException.class, BaseDomainConflictException.class, BaseDomainNotFoundException.class, ConstraintViolationException.class, Exception.class})
public class ApplicationUnwrapExceptionMapping {

    @Inject
    LocalizationService localizationService;

    public static final String VALIDATION_ERROR_MESSAGE = "ApplicationUnwrapExceptionMapping.1";

    @ServerExceptionMapper
    public Response unwrapBaseDomainBadException(BaseDomainBadException exception) {
        return Response.status(BAD_REQUEST)
                .entity(
                    ErrorApiResponse.builder()
                        .success(false)
                        .status(BAD_REQUEST.name())
                        .timestamp(ZonedDateTime.now())
                        .reason(localizationService.getMessage(exception.getMessage(),exception.getArgs()))
                        .code(BAD_REQUEST.getStatusCode())
                )
                .build();

   }

    @ServerExceptionMapper
    public Response unwrapBaseDomainConflictException(BaseDomainConflictException exception) {
        return Response.status(CONFLICT)
                .entity(
                        ErrorApiResponse.builder()
                                .success(false)
                                .status(CONFLICT.name())
                                .timestamp(ZonedDateTime.now())
                                .reason(localizationService.getMessage(exception.getMessage(), exception.getArgs()))
                                .code(CONFLICT.getStatusCode())
                )
                .build();

    }

    @ServerExceptionMapper
    public Response unwrapBaseDomainNotFoundException(BaseDomainNotFoundException exception) {
        return Response.status(NOT_FOUND)
                .entity(
                        ErrorApiResponse.builder()
                                .success(false)
                                .status(NOT_FOUND.name())
                                .timestamp(ZonedDateTime.now())
                                .reason(localizationService.getMessage(exception.getMessage(), exception.getArgs()))
                                .code(NOT_FOUND.getStatusCode())
                )
                .build();

    }

    @ServerExceptionMapper
    public Response unwrapConstraintViolationException(ConstraintViolationException exception) {
        List<ValidationError> errors = exception.getConstraintViolations().stream()
                .map(constraintViolation -> ValidationError.builder()
                        .field(getField(constraintViolation))
                        .message(localizationService.getMessage(getMessage(constraintViolation))).build())
                .collect(Collectors.toUnmodifiableList());

        return Response.status(BAD_REQUEST)
                .entity(
                        ErrorApiResponse.builder()
                                .success(false)
                                .status(BAD_REQUEST.name())
                                .timestamp(ZonedDateTime.now())
                                .reason(localizationService.getMessage(VALIDATION_ERROR_MESSAGE))
                                .code(BAD_REQUEST.getStatusCode())
                                .errors(errors)
                )
                .build();

    }

    private static String getMessage(ConstraintViolation<?> constraintViolation) {
        return Arrays.stream(constraintViolation.getPropertyPath().toString()
                .split("\\."))
                .filter(message -> !message.startsWith("arg"))
                .collect(Collectors.joining("."));
    }

    private static String getField(ConstraintViolation<?> constraintViolation) {
        return Arrays.stream(constraintViolation.getPropertyPath().toString().split("\\.")).toList().getLast();
    }

    @ServerExceptionMapper
    public Response unwrapException(Exception exception) {
        return Response.status(INTERNAL_SERVER_ERROR)
                .entity(
                        ErrorApiResponse.builder()
                                .success(false)
                                .status(INTERNAL_SERVER_ERROR.name())
                                .timestamp(ZonedDateTime.now())
                                .reason(localizationService.getMessage(exception.getMessage()))
                                .code(INTERNAL_SERVER_ERROR.getStatusCode())
                )
                .build();

    }
}
