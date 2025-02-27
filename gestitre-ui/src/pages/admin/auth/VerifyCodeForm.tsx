import {useTranslation} from "react-i18next";
import {useToast} from "@/hooks/use-toast.ts";
import {z} from "zod";
import {useLocation, useNavigate} from "react-router-dom";
import {VerifyCodeRequestModel} from "@/pages/admin/auth/VerifyCodeRequestModel.ts";
import {useForm} from "react-hook-form";
import {zodResolver} from "@hookform/resolvers/zod";
import {useDispatch, useSelector} from "react-redux";
import {RootDispatch} from "@/Store.ts";
import {getLoading, verifyCode} from "@/pages/admin/auth/AuthSlice.ts";
import {unwrapResult} from "@reduxjs/toolkit";
import {ToastType} from "@/shared/constant/globalConstant.ts";
import {cn} from "@/lib/utils.ts";
import {Toaster} from "@/components/ui/toaster.tsx";
import {Card, CardContent, CardDescription, CardHeader, CardTitle} from "@/components/ui/card.tsx";
import {Form, FormControl, FormField, FormItem, FormLabel, FormMessage} from "@/components/ui/form.tsx";
import {Input} from "@/components/ui/input.tsx";
import {Button} from "@/components/ui/button.tsx";


const VerifyCodeForm = () => {
    const {t} = useTranslation(['home']);
    const {toast} = useToast();

    const {state} = useLocation();
    console.log(state);


    const navigate = useNavigate();

    const isLoading: boolean = useSelector(getLoading);
    const dispatch = useDispatch<RootDispatch>();

    const VerifyCodeSchema = z.object({
        tenantCode: z.string(),
        email: z.string(),
        code: z.string().min(1, {message: t('verify_code_form_verify_code_code_required_message')}).min(6, {message: t('verify_code_form_verify_code_code_min_message')}).max(6, {message: t('verify_code_form_verify_code_code_max_length_message')}),
    });

    const defaultVerifyCodeRequestModel: VerifyCodeRequestModel = {
        tenantCode: state.tenantCode === undefined? "": state.tenantCode,
        email: state.email === undefined? "": state.email,
        code: ""
    }

    const form = useForm<z.infer<typeof VerifyCodeSchema>>({
        defaultValues: defaultVerifyCodeRequestModel,
        resolver: zodResolver(VerifyCodeSchema),
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
        const verifyCodeRequest: VerifyCodeRequestModel = form.getValues() as VerifyCodeRequestModel;
        dispatch(verifyCode({verifyCodeRequest}))
            .then(unwrapResult)
            .then((response) => {
                showToast("success", response.message);
                navigate(`/dashboard`);
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
                    <div className="flex flex-col justify-center items-center">
                        <Card className="flex flex-col justify-center items-start lg:w-4/12 shadow-xl">
                            <CardHeader className="flex flex-col justify-center items-center w-full text-center">
                                <CardTitle className="text-primary text-3xl w-full">{t('verify_code_form_verify_code_header_label')}</CardTitle>
                                <CardDescription className="text-neutral-500 text-lg w-full m-5">{t('verify_code_form_verify_code_description_label')}</CardDescription>
                            </CardHeader>
                            <CardContent className="flex flex-col justify-center items-center gap-5 w-full">
                                <div className="flex flex-col w-full gap-4">
                                        <div className="flex flex-col space-y-1.5">
                                            <FormField
                                                control={form.control}
                                                name="tenantCode"
                                                render={({field}) => (
                                                    <FormItem>
                                                        <FormControl>
                                                            <Input id="tenantCode" type="hidden" {...field}/>
                                                        </FormControl>
                                                    </FormItem>
                                                )}
                                            />
                                            <FormField
                                                control={form.control}
                                                name="email"
                                                render={({field}) => (
                                                    <FormItem>
                                                        <FormControl>
                                                            <Input id="email" type="hidden" {...field}/>
                                                        </FormControl>
                                                    </FormItem>
                                                )}
                                            />
                                            <FormField
                                                control={form.control}
                                                name="code"
                                                render={({field}) => (
                                                    <FormItem>
                                                        <FormLabel>{t('verify_code_form_verify_code_code_label')}</FormLabel>
                                                        <FormControl>
                                                            <Input id="code" type="text" {...field} disabled={isLoading}/>
                                                        </FormControl>
                                                        <FormMessage className="text-xs text-destructive"/>
                                                    </FormItem>
                                                )}
                                            />
                                        </div>
                                        <div className="flex flex-col justify-start items-start w-full gap-4">
                                            <Button variant="link">{t('verify_code_form_button_generate_code')}</Button>
                                            <Button variant="default" type="submit" disabled={!form.formState.isValid} className="w-full">
                                                <span className={`material-symbols-outlined text-xl ${isLoading? 'animate-spin': ''}`}>{isLoading? 'progress_activity': 'check'}</span>
                                                <span>{t('verify_code_form_button_label_validate')} {isLoading? ' ...': ''}</span>
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

export default VerifyCodeForm;