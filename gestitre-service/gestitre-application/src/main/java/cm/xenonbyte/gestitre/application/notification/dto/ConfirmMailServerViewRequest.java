package cm.xenonbyte.gestitre.application.notification.dto;

import jakarta.validation.constraints.NotBlank;

import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.NOT_BLANK;

/**
 * @author bamk
 * @version 1.0
 * @since 15/03/2025
 */
public record ConfirmMailServerViewRequest(@NotBlank(message = NOT_BLANK) String code) {}
