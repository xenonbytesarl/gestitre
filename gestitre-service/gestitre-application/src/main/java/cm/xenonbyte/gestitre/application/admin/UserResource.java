package cm.xenonbyte.gestitre.application.admin;

import cm.xenonbyte.gestitre.application.admin.dto.ActivateAccountRequest;
import cm.xenonbyte.gestitre.application.admin.dto.CreateUserViewRequest;
import cm.xenonbyte.gestitre.application.admin.dto.LoginRequest;
import cm.xenonbyte.gestitre.application.admin.dto.LoginResponse;
import cm.xenonbyte.gestitre.application.admin.dto.ResendVerificationCodeRequest;
import cm.xenonbyte.gestitre.application.admin.dto.VerifyCodeRequest;
import cm.xenonbyte.gestitre.application.common.dto.SuccessApiResponse;
import jakarta.annotation.Nonnull;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import java.time.ZonedDateTime;
import java.util.Objects;

import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.CONTENT;
import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.USER_API_PATH;
import static cm.xenonbyte.gestitre.application.common.in18.LocalizationUtil.getMessage;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.Response.Status.CREATED;
import static jakarta.ws.rs.core.Response.Status.OK;
import static java.util.Locale.forLanguageTag;
import static java.util.Map.of;

/**
 * @author bamk
 * @version 1.0
 * @since 24/11/2024
 */
@Path(USER_API_PATH)
public class  UserResource {

    private static final String USER_CREATED_SUCCESSFULLY = "UserResource.1";
    private static final String USER_LOGGED_IN_SUCCESSFULLY = "UserResource.2";
    private static final String USER_VERIFICATION_MFA_SEND = "UserResource.3";
    private static final String USER_ACTIVATED_IN_SUCCESSFULLY = "UserResource.4";

    private final UserApplicationAdapter userApplicationAdapter;

    public UserResource(@Nonnull UserApplicationAdapter userApplicationAdapter) {
        this.userApplicationAdapter = Objects.requireNonNull(userApplicationAdapter);
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @RolesAllowed({"create:user"})
    public Response createUser(
            @HeaderParam("Accept-Language") String acceptLanguage,
            @Valid CreateUserViewRequest createUserViewRequest
    ) {
        return Response.status(CREATED)
                .entity(
                        SuccessApiResponse.builder()
                                .success(true)
                                .status(CREATED.name())
                                .code(CREATED.getStatusCode())
                                .timestamp(ZonedDateTime.now())
                                .message(getMessage(USER_CREATED_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                                .data(of(CONTENT, userApplicationAdapter.createUser(createUserViewRequest)))
                                .build()
                )
                .build();
    }

    @POST
    @Path("/auth/token")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @PermitAll
    public Response login(
            @HeaderParam("Accept-Language") String acceptLanguage,
            @Valid LoginRequest loginRequest
    ) {
        LoginResponse loginResponse = userApplicationAdapter.login(loginRequest);
        return Response.status(OK)
                .entity(
                        SuccessApiResponse.builder()
                                .success(true)
                                .status(OK.name())
                                .code(OK.getStatusCode())
                                .timestamp(ZonedDateTime.now())
                                .message(getMessage(loginResponse.getIsMfa() != null && loginResponse.getIsMfa()? USER_VERIFICATION_MFA_SEND: USER_LOGGED_IN_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                                .data(of(CONTENT, loginResponse))
                                .build()
                )
                .build();
    }

    @POST
    @Path("/auth/verify-code")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @PermitAll
    public Response verifyCode(
            @HeaderParam("Accept-Language") String acceptLanguage,
            @Valid VerifyCodeRequest verifyCodeRequest
            ) {
        return Response.status(OK)
                .entity(
                        SuccessApiResponse.builder()
                                .success(true)
                                .status(OK.name())
                                .code(OK.getStatusCode())
                                .timestamp(ZonedDateTime.now())
                                .message(getMessage(USER_LOGGED_IN_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                                .data(of(CONTENT, userApplicationAdapter.verifyCode(verifyCodeRequest)))
                                .build()
                )
                .build();
    }

    @PUT
    @Path("/auth/activate-account")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @PermitAll
    public Response verifyCode(
            @HeaderParam("Accept-Language") String acceptLanguage,
            @Valid ActivateAccountRequest activateAccountRequest
    ) {
        return Response.status(OK)
                .entity(
                        SuccessApiResponse.builder()
                                .success(true)
                                .status(OK.name())
                                .code(OK.getStatusCode())
                                .timestamp(ZonedDateTime.now())
                                .message(getMessage(USER_ACTIVATED_IN_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                                .data(of(CONTENT, userApplicationAdapter.activateUser(activateAccountRequest)))
                                .build()
                )
                .build();
    }

    @POST
    @Path("/auth/verify-code/resend")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @PermitAll
    public Response resendVerifyCode(
            @HeaderParam("Accept-Language") String acceptLanguage,
            @Valid ResendVerificationCodeRequest resendVerificationCodeRequest
    ) {
        return Response.status(OK)
                .entity(
                        SuccessApiResponse.builder()
                                .success(true)
                                .status(OK.name())
                                .code(OK.getStatusCode())
                                .timestamp(ZonedDateTime.now())
                                .message(getMessage(USER_LOGGED_IN_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                                .data(of(CONTENT, userApplicationAdapter.resendMfaVerification(resendVerificationCodeRequest)))
                                .build()
                )
                .build();
    }
}
