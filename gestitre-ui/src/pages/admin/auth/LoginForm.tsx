import {useTranslation} from "react-i18next";
import {useToast} from "@/hooks/use-toast.ts";
import {z} from "zod";
import {useNavigate, useSearchParams} from "react-router-dom";
import {LoginRequestModel} from "@/pages/admin/auth/LoginRequestModel.ts";
import {useForm} from "react-hook-form";
import {zodResolver} from "@hookform/resolvers/zod";
import {useDispatch, useSelector} from "react-redux";
import {RootDispatch} from "@/core/Store.ts";
import {getLoading, getProfile, login, persistAuthentication, persistMfa} from "@/pages/admin/auth/AuthSlice.ts";
import {unwrapResult} from "@reduxjs/toolkit";
import {REDIRECT, ToastType} from "@/shared/constant/globalConstant.ts";
import {cn} from "@/lib/utils.ts";
import {Toaster} from "@/components/ui/toaster.tsx";
import {Card, CardContent, CardDescription, CardHeader, CardTitle} from "@/components/ui/card.tsx";
import {Form, FormControl, FormField, FormItem, FormLabel, FormMessage} from "@/components/ui/form.tsx";
import {Input} from "@/components/ui/input.tsx";
import {Button} from "@/components/ui/button.tsx";
import {recoverLastVisitedUrl} from "@/shared/utils/localStorageUtils.ts";


const LoginForm = () => {
    const {t} = useTranslation(['home']);
    const {toast} = useToast();

    const navigate = useNavigate();
    const [searchParams] = useSearchParams();
    const redirectUrl = searchParams.get(REDIRECT) || recoverLastVisitedUrl();

    const isLoading: boolean = useSelector(getLoading);
    const dispatch = useDispatch<RootDispatch>();

    const LoginSchema = z.object({
        tenantCode: z.string().min(1, {message: t('login_form_login_tenant_code_required_message')}),
        email: z.string().min(1, {message: t('login_form_login_email_required_message')}).max(128, {message: t('login_form_login_email_max_length_message')}).email(t('login_form_login_email_invalid_message')),
        password: z.string().min(1, {message: t('login_form_login_password_required_message')}).min(6, {message: t('login_form_login_password_min_length_message')}),
    });

    const defaultLoginRequestModel: LoginRequestModel = {
        tenantCode: "CM20250201",
        email: "ambiandji@gmail.com",
        password: "gestitre123!"
    }

    const form = useForm<z.infer<typeof LoginSchema>>({
        defaultValues: defaultLoginRequestModel,
        resolver: zodResolver(LoginSchema),
        mode: "onChange"
    });

    const showToast = (variant: ToastType, message: string) => {
        toast({
            className: cn('top-0 right-0 flex fixed md:max-w-[420px] md:top-4 md:right-4'),
            variant: variant,
            title: "Gestitre",
            description: t(message),
        });
    }

    const onSubmit = () => {
        const loginFormValue: LoginRequestModel = form.getValues() as LoginRequestModel;
        dispatch(login({loginRequest: loginFormValue}))
            .then(unwrapResult)
            .then((response) => {
                showToast("success", response.message);
                if(!response.content.isMfa) {
                    dispatch(persistAuthentication({ accessToken: response.content.accessToken, refreshToken: response.content.refreshToken}));
                    dispatch(getProfile());
                    navigate(redirectUrl);
                } else {
                    dispatch(persistMfa({email: loginFormValue.email, tenantCode: loginFormValue.tenantCode}));
                    navigate(`/admin/auth/verify-code?${REDIRECT}=${encodeURIComponent(redirectUrl)}`, { state: {'email': loginFormValue.email, tenantCode: loginFormValue.tenantCode}});
                }
            })
            .catch((error) => {
                showToast("danger", error !== null && error.reason !== null? t(error.reason) : t(error));
            });
    };

    return (
        <div>
            <Form {...form}>
                <form onSubmit={form.handleSubmit(onSubmit)} noValidate>
                    <Toaster/>
                    <div className="flex flex-col justify-center items-center mt-24">
                        <Card className="flex flex-col justify-center items-center lg:w-3/12 shadow-xl">
                            <CardHeader className="flex flex-row justify-between items-center w-full text-center">
                                <CardTitle className="text-primary text-3xl w-full">{t('login_form_login_header_label')}</CardTitle>
                                <CardDescription>
                                    <span className="flex flex-row w-full m-5"></span>
                                    <span className="flex flex-row justify-end items-center gap-3 w-6/12"></span>
                                </CardDescription>
                            </CardHeader>
                            <CardContent className="flex flex-col justify-center items-center gap-5 w-full">
                                <div className="flex flex-col w-full gap-4">
                                        <div className="flex flex-col space-y-1.5">
                                            <FormField
                                                control={form.control}
                                                name="tenantCode"
                                                render={({field}) => (
                                                    <FormItem>
                                                        <FormLabel>{t('login_form_login_tenant_code_label')}</FormLabel>
                                                        <FormControl>
                                                            <Input id="tenantCode" type="text" {...field} disabled={isLoading}/>
                                                        </FormControl>
                                                        <FormMessage className="text-xs text-destructive"/>
                                                    </FormItem>
                                                )}
                                            />
                                            <FormField
                                                control={form.control}
                                                name="email"
                                                render={({field}) => (
                                                    <FormItem>
                                                        <FormLabel>{t('login_form_login_email_label')}</FormLabel>
                                                        <FormControl>
                                                            <Input id="email" type="email" {...field}
                                                                   disabled={isLoading}/>
                                                        </FormControl>
                                                        <FormMessage className="text-xs text-destructive"/>
                                                    </FormItem>
                                                )}
                                            />
                                            <FormField
                                                control={form.control}
                                                name="password"
                                                render={({field}) => (
                                                    <FormItem>
                                                        <FormLabel>{t('login_form_login_password_label')}</FormLabel>
                                                        <FormControl>
                                                            <Input id="password" type="password" {...field} disabled={isLoading}/>
                                                        </FormControl>
                                                        <FormMessage className="text-xs text-destructive"/>
                                                    </FormItem>
                                                )}
                                            />
                                        </div>
                                        <div className="flex flex-col justify-start items-start w-full gap-4">
                                            <Button type="button" variant="link">{t('login_form_button_password_forget')}</Button>
                                            <Button variant="default" type="submit" disabled={!form.formState.isValid} className="w-full">
                                                <span className={`material-symbols-outlined text-xl ${isLoading? 'animate-spin': ''}`}>{isLoading? 'progress_activity': 'login'}</span>
                                                <span>{t('login_form_button_label_login')} {isLoading? ' ...': ''}</span>
                                            </Button>
                                        </div>
                                    </div>
                            </CardContent>

                        </Card>
                    </div>
                </form>
            </Form>
        </div>
    );
}

export default LoginForm;