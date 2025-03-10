package cm.xenonbyte.gestitre.application.admin;

import cm.xenonbyte.gestitre.application.admin.dto.ActivateAccountRequest;
import cm.xenonbyte.gestitre.application.admin.dto.CreateUserViewRequest;
import cm.xenonbyte.gestitre.application.admin.dto.LoginRequest;
import cm.xenonbyte.gestitre.application.admin.dto.LoginResponse;
import cm.xenonbyte.gestitre.application.admin.dto.ResendVerificationCodeRequest;
import cm.xenonbyte.gestitre.application.admin.dto.ResetPasswordRequest;
import cm.xenonbyte.gestitre.application.admin.dto.SendResetPasswordCodeRequest;
import cm.xenonbyte.gestitre.application.admin.dto.UpdateUserViewRequest;
import cm.xenonbyte.gestitre.application.admin.dto.VerifyCodeRequest;
import cm.xenonbyte.gestitre.application.common.dto.SuccessApiResponse;
import jakarta.annotation.Nonnull;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

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
    private static final String USER_SEND_RESET_PASSWORD_SUCCESSFULLY = "UserResource.5";
    private static final String USER_PASSWORD_RESET_SUCCESSFULLY = "UserResource.6";
    private static final String USER_UPDATED_SUCCESSFULLY = "UserResource.7";
    private static final String USER_FIND_SUCCESSFULLY = "UserResource.8";
    private static final String USER_FINDS_SUCCESSFULLY = "UserResource.9";

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

    @POST
    @Path("/auth/reset-password/send-code")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @PermitAll
    public Response sendResetPasswordCode(
            @HeaderParam("Accept-Language") String acceptLanguage,
            @Valid SendResetPasswordCodeRequest sendResetPasswordCodeRequest
    ) {
        userApplicationAdapter.sendResetPasswordCode(sendResetPasswordCodeRequest);
        return Response.status(OK)
                .entity(
                        SuccessApiResponse.builder()
                                .success(true)
                                .status(OK.name())
                                .code(OK.getStatusCode())
                                .timestamp(ZonedDateTime.now())
                                .message(getMessage(USER_SEND_RESET_PASSWORD_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                                .build()
                )
                .build();
    }

    @POST
    @Path("/auth/reset-password")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @PermitAll
    public Response resetPassword(
            @HeaderParam("Accept-Language") String acceptLanguage,
            @Valid ResetPasswordRequest resetPasswordRequest
    ) {
        userApplicationAdapter.resetPassword(resetPasswordRequest);
        return Response.status(OK)
                .entity(
                        SuccessApiResponse.builder()
                                .success(true)
                                .status(OK.name())
                                .code(OK.getStatusCode())
                                .timestamp(ZonedDateTime.now())
                                .message(getMessage(USER_PASSWORD_RESET_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                                .build()
                )
                .build();
    }

    @PUT
    @Path("/{userId}")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @RolesAllowed({"update:user"})
    public Response updateUser(
            @HeaderParam("Accept-Language") String acceptLanguage,
            @PathParam("userId") UUID userId,
            @Valid UpdateUserViewRequest updateUserViewRequest
    ) {
        return Response.status(OK)
                .entity(
                        SuccessApiResponse.builder()
                                .success(true)
                                .status(OK.name())
                                .code(OK.getStatusCode())
                                .timestamp(ZonedDateTime.now())
                                .message(getMessage(USER_UPDATED_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                                .data(of(CONTENT, userApplicationAdapter.updateUser(userId, updateUserViewRequest)))
                                .build()
                )
                .build();
    }

    @GET
    @Path("/{userId}")
    @Produces(APPLICATION_JSON)
    @RolesAllowed({"read:user"})
    public Response findUserById(
            @HeaderParam("Accept-Language") String acceptLanguage,
            @PathParam("userId") UUID userId
    ) {
        return Response.status(OK)
                .entity(
                        SuccessApiResponse.builder()
                                .success(true)
                                .status(OK.name())
                                .code(OK.getStatusCode())
                                .timestamp(ZonedDateTime.now())
                                .message(getMessage(USER_FIND_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                                .data(of(CONTENT, userApplicationAdapter.findUserById(userId)))
                                .build()
                )
                .build();
    }

    @GET
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @RolesAllowed({"read:user"})
    public Response searchUsers(
            @HeaderParam("Accept-Language") String acceptLanguage,
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size,
            @QueryParam("field") String field,
            @QueryParam("direction") String direction,
            @QueryParam("keyword") String keyword
    ) {
        return Response.status(OK)
                .entity(
                        SuccessApiResponse.builder()
                                .success(true)
                                .status(OK.name())
                                .code(OK.getStatusCode())
                                .timestamp(ZonedDateTime.now())
                                .message(getMessage(USER_FINDS_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                                .data(of(CONTENT, userApplicationAdapter.searchUsers(page, size, field, direction, keyword)))
                )
                .build();
    }
}
