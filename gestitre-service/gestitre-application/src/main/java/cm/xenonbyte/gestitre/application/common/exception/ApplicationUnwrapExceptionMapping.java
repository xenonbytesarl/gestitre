package cm.xenonbyte.gestitre.application.common.exception;

import cm.xenonbyte.gestitre.application.common.dto.ErrorApiResponse;
import cm.xenonbyte.gestitre.application.common.dto.ValidationError;
import cm.xenonbyte.gestitre.application.common.in18.LocalizationResolver;
import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainBadException;
import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainConflictException;
import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainNotFoundException;
import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainUnAuthorizedException;
import jakarta.annotation.Nonnull;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;
import org.jboss.resteasy.reactive.server.UnwrapException;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.MAX_SIZE;
import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.MIN_MAX_SIZE;
import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.MIN_SIZE;
import static cm.xenonbyte.gestitre.application.common.in18.LocalizationUtil.getMessage;
import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;
import static jakarta.ws.rs.core.Response.Status.CONFLICT;
import static jakarta.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;
import static jakarta.ws.rs.core.Response.Status.UNAUTHORIZED;

/**
 * @author bamk
 * @version 1.0
 * @since 02/11/2024
 */
@Slf4j
@UnwrapException({BaseDomainBadException.class, BaseDomainConflictException.class, BaseDomainNotFoundException.class, ConstraintViolationException.class, Exception.class})
public class ApplicationUnwrapExceptionMapping {


    private final LocalizationResolver localeResolver;

    public static final String VALIDATION_ERROR_MESSAGE = "ApplicationUnwrapExceptionMapping.1";

    public ApplicationUnwrapExceptionMapping(@Nonnull LocalizationResolver localeResolver) {
        this.localeResolver = Objects.requireNonNull(localeResolver);
    }

    @ServerExceptionMapper
    public Response unwrapBaseDomainBadException(BaseDomainBadException exception) {
        log.error(exception.getMessage(), exception);
        return Response.status(BAD_REQUEST)
                .entity(
                    ErrorApiResponse.builder()
                        .success(false)
                        .status(BAD_REQUEST.name())
                        .timestamp(ZonedDateTime.now())
                        .reason(getMessage(exception.getMessage(), localeResolver.getLocaleFromRequest(), exception.getArgs()))
                        .code(BAD_REQUEST.getStatusCode())
                )
                .build();

   }

    @ServerExceptionMapper
    public Response unwrapBaseDomainBadException(BaseDomainUnAuthorizedException exception) {
        log.error(exception.getMessage(), exception);
        return Response.status(UNAUTHORIZED)
                .entity(
                        ErrorApiResponse.builder()
                                .success(false)
                                .status(UNAUTHORIZED.name())
                                .timestamp(ZonedDateTime.now())
                                .reason(getMessage(exception.getMessage(), localeResolver.getLocaleFromRequest(), exception.getArgs()))
                                .code(UNAUTHORIZED.getStatusCode())
                )
                .build();

    }

    @ServerExceptionMapper
    public Response unwrapBaseDomainConflictException(BaseDomainConflictException exception) {
        log.error(exception.getMessage(), exception);
        return Response.status(CONFLICT)
                .entity(
                        ErrorApiResponse.builder()
                                .success(false)
                                .status(CONFLICT.name())
                                .timestamp(ZonedDateTime.now())
                                .reason(getMessage(exception.getMessage(), localeResolver.getLocaleFromRequest(), exception.getArgs()))
                                .code(CONFLICT.getStatusCode())
                )
                .build();

    }

    @ServerExceptionMapper
    public Response unwrapBaseDomainNotFoundException(BaseDomainNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return Response.status(NOT_FOUND)
                .entity(
                        ErrorApiResponse.builder()
                                .success(false)
                                .status(NOT_FOUND.name())
                                .timestamp(ZonedDateTime.now())
                                .reason(getMessage(exception.getMessage(), localeResolver.getLocaleFromRequest(), exception.getArgs()))
                                .code(NOT_FOUND.getStatusCode())
                )
                .build();

    }

    @ServerExceptionMapper
    public Response unwrapConstraintViolationException(ConstraintViolationException exception) {
        List<ValidationError> errors = exception.getConstraintViolations().stream()
                .map(constraintViolation -> ValidationError.builder()
                        .field(getErrorField(constraintViolation))
                        .message(getMessage(getErrorMessage(constraintViolation), localeResolver.getLocaleFromRequest(), getArgs(constraintViolation))).build())
                .collect(Collectors.toUnmodifiableList());
        log.error(exception.getMessage(), exception);
        return Response.status(BAD_REQUEST)
                .entity(
                        ErrorApiResponse.builder()
                                .success(false)
                                .status(BAD_REQUEST.name())
                                .timestamp(ZonedDateTime.now())
                                .reason(getMessage(VALIDATION_ERROR_MESSAGE, localeResolver.getLocaleFromRequest()))
                                .code(BAD_REQUEST.getStatusCode())
                                .errors(errors)
                )
                .build();

    }

    private Object[] getArgs(ConstraintViolation<?> constraintViolation) {
        List<String> args = new ArrayList<>();
        if (constraintViolation.getConstraintDescriptor().getAnnotation().annotationType().getName().contains("Size")) {
            Map<String, Object> attributes = constraintViolation.getConstraintDescriptor().getAttributes();
            for (String key : attributes.keySet().stream().sorted().toList()) {
                if(
                    key.equals("min") &&
                    (attributes.get("message").toString().equals(MIN_SIZE) || attributes.get("message").toString().equals(MIN_MAX_SIZE)) &&
                    Integer.parseInt(attributes.get(key).toString()) != 0) {
                    args.add(attributes.get(key).toString());
                }
                if(
                    key.equals("max") &&
                    (attributes.get("message").toString().equals(MAX_SIZE) || attributes.get("message").toString().equals(MIN_MAX_SIZE)) &&
                    Integer.parseInt(attributes.get(key).toString()) != 0) {
                    args.add(attributes.get(key).toString());
                }
            }
        }
        return args.stream().sorted().toArray();
    }

    private static String getErrorMessage(ConstraintViolation<?> constraintViolation) {
        return constraintViolation.getMessage();
    }

    private static String getErrorField(ConstraintViolation<?> constraintViolation) {
        return Arrays.stream(constraintViolation.getPropertyPath().toString().split("\\.")).toList().getLast();
    }

    @ServerExceptionMapper
    public Response unwrapException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return Response.status(INTERNAL_SERVER_ERROR)
                .entity(
                        ErrorApiResponse.builder()
                                .success(false)
                                .status(INTERNAL_SERVER_ERROR.name())
                                .timestamp(ZonedDateTime.now())
                                .reason(getMessage(exception.getMessage(), localeResolver.getLocaleFromRequest()))
                                .code(INTERNAL_SERVER_ERROR.getStatusCode())
                )
                .build();

    }
}
