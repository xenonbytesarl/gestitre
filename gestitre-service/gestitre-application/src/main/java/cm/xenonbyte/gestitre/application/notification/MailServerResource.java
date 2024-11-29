package cm.xenonbyte.gestitre.application.notification;

import cm.xenonbyte.gestitre.application.common.dto.SuccessApiResponse;
import cm.xenonbyte.gestitre.application.notification.dto.CreateMailServerViewRequest;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import java.time.ZonedDateTime;
import java.util.Objects;

import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.CONTENT;
import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.MAIL_SERVER_API_PATH;
import static cm.xenonbyte.gestitre.application.common.in18.LocalizationUtil.getMessage;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.Response.Status.CREATED;
import static java.util.Locale.forLanguageTag;
import static java.util.Map.of;

/**
 * @author bamk
 * @version 1.0
 * @since 02/12/2024
 */
@Path(MAIL_SERVER_API_PATH)
public final class MailServerResource {

    private static final String MAIL_SERVER_CREATED_SUCCESSFULLY = "MailServerResource.1";
    private final MailServerApplicationAdapter mailServerApplicationAdapter;

    public MailServerResource(MailServerApplicationAdapter mailServerApplicationAdapter) {
        this.mailServerApplicationAdapter = Objects.requireNonNull(mailServerApplicationAdapter);
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @RolesAllowed({"create:mail:server"})
    public Response createMailServer(
            @HeaderParam("Accept-Language") String acceptLanguage,
            @Valid CreateMailServerViewRequest createMailServerViewRequest
    ) {
        return Response.status(CREATED)
                .entity(
                        SuccessApiResponse.builder()
                                .success(true)
                                .status(CREATED.name())
                                .code(CREATED.getStatusCode())
                                .timestamp(ZonedDateTime.now())
                                .message(getMessage(MAIL_SERVER_CREATED_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                                .data(of(CONTENT, mailServerApplicationAdapter.createMailServer(createMailServerViewRequest)))
                                .build()
                )
                .build();
    }
}
