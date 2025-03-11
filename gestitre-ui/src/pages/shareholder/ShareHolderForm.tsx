import {useTranslation} from "react-i18next";
import {useNavigate, useParams} from "react-router-dom";
import {useToast} from "@/hooks/use-toast.ts";
import {useDispatch, useSelector} from "react-redux";
import {RootDispatch} from "@/core/Store.ts";
import {
    createShareHolder,
    findShareHolderById,
    getCurrentShareHolder,
    getLoading,
    resetCurrentShareHolder,
    updateShareHolder
} from "@/pages/shareholder/ShareholderSlice.tsx";
import {useEffect, useState} from "react";
import {FormModeType} from "@/shared/model/FormModeType.ts";
import {z} from "zod";
import {ShareHolderModel} from "@/pages/shareholder/ShareHolderModel.ts";
import {ShareHolderTypeEnum} from "@/pages/shareholder/ShareHolderTypeEnum.ts";
import {useForm} from "react-hook-form";
import {zodResolver} from "@hookform/resolvers/zod";
import {changeNullToEmptyString} from "@/shared/utils/changeNullToEmptyString.ts";
import {unwrapResult} from "@reduxjs/toolkit";
import {ToastType} from "@/shared/constant/globalConstant.ts";
import {cn} from "@/lib/utils.ts";
import {Toaster} from "@/components/ui/toaster.tsx";
import {Card, CardContent, CardDescription, CardHeader, CardTitle} from "@/components/ui/card.tsx";
import FormCrudButton from "@/components/FormCrudButton.tsx";
import {Form, FormControl, FormField, FormItem, FormLabel, FormMessage} from "@/components/ui/form.tsx";
import {Input} from "@/components/ui/input.tsx";
import {Popover, PopoverContent, PopoverTrigger} from "@/components/ui/popover.tsx";
import {Button} from "@/components/ui/button.tsx";
import {Command, CommandGroup, CommandInput, CommandItem, CommandList} from "@/components/ui/command.tsx";
import {Calendar} from "@/components/ui/calendar.tsx";
import {format} from "date-fns";
import {CalendarIcon} from "lucide-react";
import {Tabs, TabsContent, TabsList, TabsTrigger} from "@/components/ui/tabs.tsx";
import {countries} from "@/shared/constant/country.ts";
import {DevTool} from "@hookform/devtools";

const accountTypes = [
    {label: 'shareholder_form_account_type_nominative_pure', name: 'NOMINATIVE_PURE'},
    {label: 'shareholder_form_account_type_nominative_administrative', name: 'NOMINATIVE_ADMINISTERED'}
];

const shareHolderTypes = [
    {label: 'shareholder_form_shareholder_type_representative', name: 'REPRESENTATIVE'},
    {label: 'shareholder_form_shareholder_type_successor', name: 'SUCCESSOR'}
];

const ShareHolderForm = () => {

    const {t} = useTranslation(['home']);

    const {shareHolderId} = useParams();

    const {toast} = useToast();

    const shareHolder: ShareHolderModel = useSelector(getCurrentShareHolder);
    const isLoading: boolean = useSelector(getLoading);
    const dispatch = useDispatch<RootDispatch>();

    const [openAccountTypePopOver, setOpenAccountTypePopOver] = useState(false);
    const [accountTypePopOverLabel, setAccountTypePopOverLabel] = useState<string | undefined>("");
    const [openShareHolderTypePopOver, setOpenShareHolderTypePopOver] = useState(false);
    const [shareHolderTypePopOverLabel, setShareHolderTypePopOverLabel] = useState("");
    const [openTaxResidencePopOver, setOpenTaxResidencePopOver] = useState(false);
    const [taxResidencePopOverLabel, setTaxResidencePopOverLabel] = useState("");

    const navigate = useNavigate();
    const [mode, setMode] = useState<FormModeType>(shareHolderId? FormModeType.READ: FormModeType.CREATE);

    const ShareHolderSchema = z.object({
        id: z.string(),
        name: z.string().min(1, {message: t('shareholder_form_name_required_message')}).max(128, {message: t('shareholder_form_name_max_length_message')}),
        email: z.union([z.string().email(t('shareholder_form_email_invalid_message')), z.literal('')]),
        accountNumber: z.string().min(1, t('shareholder_form_account_number_required_message')).max(32, {message: t('shareholder_form_account_number_max_length_message')}),
        accountType: z.string().min(1, {message: t('shareholder_form_account_type_required_message')}),
        taxResidence: z.string().min(1, {message: t('shareholder_form_tax_residence_required_message')}),
        phone: z.string().max(32, {message: t('shareholder_form_phone_length_message')}),
        city: z.string(),
        zipCode: z.string(),
        initialBalance:  z.coerce.number().min(1, {message: t('shareholder_form_initial_balance_positive_message')}),
        bankAccountNumber: z.string(),
        administrator: z.string().max(128, {message: t('shareholder_form_administrator_max_length_message')}),
        shareHolderType: z.string().optional(),
        createdDate: z.preprocess((arg) => typeof arg === "string" ? new Date(arg) : arg , z.date(), {required_error: t('shareholder_form_created_date_required_message')}),
        tenantId: z.string(),
        representative: z.object({
            name: z.string(),
            phone: z.string(),
            email: z.union([z.string().email(t('shareholder_form_representative_email_invalid_message')), z.literal('')]),
        }),
        successor: z.object({
            name: z.string(),
            phone: z.string(),
            email: z.union([z.string().email(t('shareholder_form_successor_email_invalid_message')), z.literal('')]),
        }),
        active: z.boolean()
    }).superRefine((data, ctx) => {
        if(data.shareHolderType && data.shareHolderType === ShareHolderTypeEnum.REPRESENTATIVE.valueOf()) {
            if(data.representative && !data.representative.name) {
                ctx.addIssue({
                    code: z.ZodIssueCode.custom,
                    path: ["representative", "name"],
                    message: t('shareholder_form_representative_name_required_message')
                });
            }
            if(data.representative && !data.representative.phone) {
                ctx.addIssue({
                    code: z.ZodIssueCode.custom,
                    path: ["representative", "phone"],
                    message: t('shareholder_form_representative_phone_required_message')
                });
            }
        }

        if(data.shareHolderType && data.shareHolderType === ShareHolderTypeEnum.SUCCESSOR.valueOf()) {
            if(data.successor && !data.successor?.name) {
                ctx.addIssue({
                    code: z.ZodIssueCode.custom,
                    path: ["successor", "name"],
                    message: t('shareholder_form_successor_name_required_message')
                });
            }
            if(data.successor && !data.successor?.phone) {
                ctx.addIssue({
                    code: z.ZodIssueCode.custom,
                    path: ["successor", "phone"],
                    message: t('shareholder_form_successor_phone_required_message')
                });
            }
        }
    });

    const defaultShareHolderValue: ShareHolderModel = {
        id: '',
        name: '',
        email: '',
        accountNumber: '',
        accountType: undefined,
        taxResidence: '',
        phone: '',
        city: '',
        zipCode: '',
        bankAccountNumber: '',
        administrator: '',
        initialBalance: 0,
        shareHolderType: undefined,
        tenantId: '',
        createdDate: new Date(),
        representative: {
            name: '',
            phone: '',
            email: ''
        },
        successor: {
            name: '',
            phone: '',
            email: ''
        },
        active: true
    };

    const form = useForm<z.infer<typeof ShareHolderSchema>>({
        defaultValues: defaultShareHolderValue,
        resolver: zodResolver(ShareHolderSchema),
        mode: "onChange"
    });



    useEffect(() => {
        // @ts-ignore
        if(!shareHolder && shareHolderId) {
            dispatch(findShareHolderById(shareHolderId));
        }
    }, [dispatch]);

    useEffect(() => {
        if(shareHolder) {
            form.reset(changeNullToEmptyString(shareHolder, defaultShareHolderValue));
            resetPopOverLabel(shareHolder);
        }
    }, [shareHolder]);

    const onSubmit = () => {
        const shareHolderFormValue: ShareHolderModel = form.getValues() as ShareHolderModel;
        if(!shareHolderFormValue.shareHolderType) {
            shareHolderFormValue.representative = undefined;
            shareHolderFormValue.successor = undefined;
        } else if(shareHolderFormValue.shareHolderType === ShareHolderTypeEnum.SUCCESSOR.valueOf()) {
            shareHolderFormValue.representative = undefined;
        } else {
            shareHolderFormValue.successor = undefined;
        }
        if(shareHolderFormValue.id) {
            dispatch(updateShareHolder(shareHolderFormValue))
                .then(unwrapResult)
                .then((response) => {
                    setMode(FormModeType.READ);
                    showToast("success", response.message);
                    navigate(`/shareholders/form/details/${response.content.id}`);
                })
                .catch((error) => {
                    setMode(FormModeType.CREATE);
                    showToast("danger", error !== null && error.reason !== null? t(error.reason) : t(error));
                });
        } else {
            dispatch(createShareHolder(shareHolderFormValue))
                .then(unwrapResult)
                .then((response) => {
                    setMode(FormModeType.READ);
                    showToast("success", response.message);
                    navigate(`/shareholders/form/details/${response.content.id}`);
                })
                .catch((error) => {
                    setMode(FormModeType.CREATE);
                    showToast("danger", error !== null && error.reason !== null? t(error.reason) : t(error));
                });
        }
    };

    const showToast = (variant: ToastType, message: string) => {
        toast({
            className: cn('top-0 right-0 flex fixed md:max-w-[420px] md:top-4 md:right-4'),
            variant: variant,
            title: "Gestitre",
            description: t(message),
        });
    }

    const onEdit = () => {
        setMode(FormModeType.EDIT);
    }

    const onCancel = () => {
        if(shareHolder) {
            form.reset(changeNullToEmptyString(shareHolder, defaultShareHolderValue));
            setMode(FormModeType.READ);
            resetPopOverLabel(shareHolder);
        } else {
            form.reset();
            resetPopOverLabel(undefined);
        }
    }

    const onCreate = () => {
        setMode(FormModeType.CREATE);
        form.reset(defaultShareHolderValue);
        resetPopOverLabel(undefined);
        dispatch(resetCurrentShareHolder())
        navigate('/shareholders/form/new');
    }

    const resetPopOverLabel = (shareHolder: ShareHolderModel | undefined) => {
        if(shareHolder) {
            setAccountTypePopOverLabel(accountTypes.find(accountType => shareHolder?.accountType?.valueOf() === accountType.name)?.label as string);
            setShareHolderTypePopOverLabel(shareHolderTypes.find(shareHolderType => shareHolder.shareHolderType?.valueOf() === shareHolderType.name)?.label as string);
            setTaxResidencePopOverLabel(countries.find(taxResidence => shareHolder.taxResidence === taxResidence.name)?.label as string);
        } else {
            setAccountTypePopOverLabel('');
            setShareHolderTypePopOverLabel('');
            setTaxResidencePopOverLabel('');
        }
        setOpenAccountTypePopOver(false);
        setOpenShareHolderTypePopOver(false);
        setOpenTaxResidencePopOver(false);
    }
    return (
        <div>
            <Form {...form}>
                <form onSubmit={form.handleSubmit(onSubmit)} noValidate>
                    <Toaster/>
                    <Card className="shadow-xl">
                        <CardHeader>
                            <CardTitle className="flex flex-row justify-start items-center text-primary gap-5">
                                <span onClick={() => navigate(`/shareholders/tree`)}
                                      className="material-symbols-outlined text-3xl cursor-pointer">arrow_back</span>
                                <span
                                    className="text-2xl">{t(shareHolderId ? 'shareholder_form_edit_title' : 'shareholder_form_new_title')}</span>
                            </CardTitle>
                            <CardDescription>
                                <span className="flex flex-row w-full m-5">
                                    <FormCrudButton
                                        mode={mode}
                                        isLoading={isLoading}
                                        isValid={form.formState.isValid}
                                        onEdit={onEdit}
                                        onCancel={onCancel}
                                        onCreate={onCreate}
                                    />
                                    <span className="flex flex-row justify-end items-center gap-3 w-6/12">
                                    </span>
                                </span>
                            </CardDescription>
                        </CardHeader>
                        <CardContent>
                            <FormField
                                control={form.control}
                                name="id"
                                render={({field}) => (
                                    <FormItem>
                                        <FormControl>
                                            <Input id="id" type="hidden" {...field} />
                                        </FormControl>
                                    </FormItem>
                                )}
                            />
                            <FormField
                                control={form.control}
                                name="tenantId"
                                render={({field}) => (
                                    <FormItem>
                                        <FormControl>
                                            <Input id="tenantId" type="hidden" {...field} />
                                        </FormControl>
                                    </FormItem>
                                )}
                            />
                            <div className="flex flex-row justify-between items-start w-full gap-4 mt-20">
                                <div className="flex flex-col justify-center items-center w-full">
                                    <div className="flex flex-col w-full gap-4">
                                        <div className="flex flex-col space-y-1.5 mb-5">
                                            <FormField
                                                control={form.control}
                                                name="name"
                                                render={({field}) => (
                                                    <FormItem>
                                                        <FormLabel>{t('shareholder_form_name_label')}</FormLabel>
                                                        <FormControl>
                                                            <Input id="name" type="text" {...field}
                                                                   disabled={mode === FormModeType.READ || isLoading}/>
                                                        </FormControl>
                                                        <FormMessage className="text-xs text-destructive"/>
                                                    </FormItem>
                                                )}
                                            />
                                        </div>
                                        <div className="flex flex-col space-y-1.5 mb-5">
                                            <FormField
                                                control={form.control}
                                                name="administrator"
                                                render={({field}) => (
                                                    <FormItem>
                                                        <FormLabel>{t('shareholder_form_administrator_label')}</FormLabel>
                                                        <FormControl>
                                                            <Input id="administrator" type="text" {...field}
                                                                   disabled={mode === FormModeType.READ || isLoading}/>
                                                        </FormControl>
                                                        <FormMessage className="text-xs text-destructive"/>
                                                    </FormItem>
                                                )}
                                            />
                                        </div>
                                        <div className="flex flex-col space-y-1.5 mb-5">
                                            <FormField
                                                control={form.control}
                                                name="taxResidence"
                                                render={() => (
                                                    <FormItem>
                                                        <FormLabel>{t('shareholder_form_tax_residence_label')}</FormLabel>
                                                        <FormControl>
                                                            <Popover open={openTaxResidencePopOver} onOpenChange={setOpenTaxResidencePopOver}>
                                                                <PopoverTrigger asChild>
                                                                    <Button
                                                                        variant="outline"
                                                                        role="combobox"
                                                                        aria-expanded={openTaxResidencePopOver}
                                                                        className="w-full justify-between"
                                                                        disabled={mode === FormModeType.READ || isLoading}
                                                                    >
                                                                    <span>{taxResidencePopOverLabel
                                                                        //@ts-ignore
                                                                        ? t(countries.find((taxResidence) => taxResidence.label === taxResidencePopOverLabel)?.label)
                                                                        //@ts-ignore
                                                                        : shareHolder ? t(countries.find((taxResidenceEdit) => taxResidenceEdit.name === shareHolder.taxResidence)?.label) : t('shareholder_form_tax_residence_pop_over_place_holder')}</span>
                                                                        <span
                                                                            className="opacity-50 material-symbols-outlined">unfold_more</span>
                                                                    </Button>
                                                                </PopoverTrigger>
                                                                <PopoverContent className="w-[--radix-popover-trigger-width]">
                                                                    <Command>
                                                                        <CommandInput id="type" placeholder={t('shareholder_form_tax_residence_pop_over_place_holder')} />
                                                                        <CommandList>
                                                                            <Command>{t('shareholder_form_pop_tax_residence_over_not_found')}</Command>
                                                                            <CommandGroup>
                                                                                {countries.map((taxResidence) => (
                                                                                    <CommandItem
                                                                                        key={taxResidence.label}
                                                                                        value={taxResidence.label}
                                                                                        onSelect={(currentValue) => {
                                                                                            setTaxResidencePopOverLabel(currentValue === taxResidencePopOverLabel ? "" : currentValue);
                                                                                            setOpenTaxResidencePopOver(false);
                                                                                            console.log(currentValue)
                                                                                            form.setValue(
                                                                                                "taxResidence",
                                                                                                currentValue === taxResidencePopOverLabel ? "" : taxResidence.name,
                                                                                                {shouldTouch: true, shouldDirty: true, shouldValidate: true}
                                                                                            );
                                                                                        }}
                                                                                    >
                                                                                    <span
                                                                                        className={`mr-2 h-4 w-4 material-symbols-outlined ${taxResidencePopOverLabel === taxResidence.label ? 'opacity-100' : 'opacity-0'}`}
                                                                                    >check</span>
                                                                                        {t(taxResidence.label)}
                                                                                    </CommandItem>
                                                                                ))}
                                                                            </CommandGroup>
                                                                        </CommandList>
                                                                    </Command>
                                                                </PopoverContent>
                                                            </Popover>
                                                        </FormControl>
                                                        <FormMessage className="text-xs text-destructive"/>
                                                    </FormItem>
                                                )}
                                            />
                                        </div>
                                        <div className="flex flex-col space-y-1.5 mb-5">
                                            <FormField
                                                control={form.control}
                                                name="bankAccountNumber"
                                                render={({field}) => (
                                                    <FormItem>
                                                        <FormLabel>{t('shareholder_form_bank_account_number_label')}</FormLabel>
                                                        <FormControl>
                                                            <Input id="bankAccountNumber" type="text" {...field}
                                                                   disabled={mode === FormModeType.READ || isLoading}/>
                                                        </FormControl>
                                                        <FormMessage className="text-xs text-destructive"/>
                                                    </FormItem>
                                                )}
                                            />
                                        </div>
                                    </div>
                                </div>
                                <div className="flex flex-col justify-center items-center w-full">
                                    <div className="flex flex-col w-full gap-4">
                                        <div className="flex flex-col space-y-1.5 mb-5">
                                            <FormField
                                                control={form.control}
                                                name="accountNumber"
                                                render={({field}) => (
                                                    <FormItem>
                                                        <FormLabel>{t('shareholder_form_account_number_label')}</FormLabel>
                                                        <FormControl>
                                                            <Input id="accountNumber" type="text" {...field}
                                                                   disabled={mode === FormModeType.READ || isLoading}/>
                                                        </FormControl>
                                                        <FormMessage className="text-xs text-destructive"/>
                                                    </FormItem>
                                                )}
                                            />
                                        </div>
                                        <div className="flex flex-col space-y-1.5 mb-5">
                                            <FormField
                                                control={form.control}
                                                name="city"
                                                render={({field}) => (
                                                    <FormItem>
                                                        <FormLabel>{t('shareholder_form_city_label')}</FormLabel>
                                                        <FormControl>
                                                            <Input id="city" type="text" {...field}
                                                                   disabled={mode === FormModeType.READ || isLoading}/>
                                                        </FormControl>
                                                        <FormMessage className="text-xs text-destructive"/>
                                                    </FormItem>
                                                )}
                                            />
                                        </div>
                                        <div className="flex flex-col space-y-1.5 mb-5">
                                            <FormField
                                                control={form.control}
                                                name="phone"
                                                render={({field}) => (
                                                    <FormItem>
                                                        <FormLabel>{t('shareholder_form_phone_label')}</FormLabel>
                                                        <FormControl>
                                                            <Input id="phone" type="text" {...field}
                                                                   disabled={mode === FormModeType.READ || isLoading}/>
                                                        </FormControl>
                                                        <FormMessage className="text-xs text-destructive"/>
                                                    </FormItem>
                                                )}
                                            />
                                        </div>
                                        <div className="flex flex-col space-y-1.5 mb-5">
                                            <FormField
                                                control={form.control}
                                                name="initialBalance"
                                                render={({field}) => (
                                                    <FormItem>
                                                        <FormLabel>{t('shareholder_form_bank_initial_balance_label')}</FormLabel>
                                                        <FormControl>
                                                            <Input id="initialBalance" type="number" {...field}
                                                                   disabled={mode === FormModeType.READ || isLoading}/>
                                                        </FormControl>
                                                        <FormMessage className="text-xs text-destructive"/>
                                                    </FormItem>
                                                )}
                                            />
                                        </div>
                                    </div>
                                </div>
                                <div className="flex flex-col justify-center items-center w-full">
                                    <div className="flex flex-col w-full gap-4">
                                        <div className="flex flex-col space-y-1.5 mb-5">
                                            <FormField
                                                control={form.control}
                                                name="accountType"
                                                render={() => (
                                                    <FormItem>
                                                        <FormLabel>{t('shareholder_form_account_type_label')}</FormLabel>
                                                        <FormControl>
                                                            <Popover open={openAccountTypePopOver} onOpenChange={setOpenAccountTypePopOver}>
                                                                <PopoverTrigger asChild>
                                                                    <Button
                                                                        variant="outline"
                                                                        role="combobox"
                                                                        aria-expanded={openAccountTypePopOver}
                                                                        className="w-full justify-between"
                                                                        disabled={mode === FormModeType.READ || isLoading}
                                                                    >
                                                                    <span>{accountTypePopOverLabel
                                                                        //@ts-ignore
                                                                        ? t(accountTypes.find((accountType) => accountType.label === accountTypePopOverLabel)?.label)
                                                                        //@ts-ignore
                                                                        : shareHolder ? t(accountTypes.find((accountTypeEdit) => accountTypeEdit.name === shareHolder.accountType)?.label) : t('shareholder_form_account_type_pop_over_place_holder')}</span>
                                                                        <span
                                                                            className="opacity-50 material-symbols-outlined">unfold_more</span>
                                                                    </Button>
                                                                </PopoverTrigger>
                                                                <PopoverContent className="w-[--radix-popover-trigger-width]">
                                                                    <Command>
                                                                        <CommandInput id="accountType" placeholder={t('shareholder_form_account_type_pop_over_place_holder')} />
                                                                        <CommandList>
                                                                            <Command>{t('shareholder_form_pop_account_type_over_not_found')}</Command>
                                                                            <CommandGroup>
                                                                                {accountTypes.map((accountType) => (
                                                                                    <CommandItem
                                                                                        key={accountType.label}
                                                                                        value={accountType.label}
                                                                                        onSelect={(currentValue) => {
                                                                                            setAccountTypePopOverLabel(currentValue === accountTypePopOverLabel ? "" : currentValue);
                                                                                            setOpenAccountTypePopOver(false);
                                                                                            form.setValue(
                                                                                                "accountType",
                                                                                                currentValue === accountTypePopOverLabel ? "" : accountType.name,
                                                                                                {shouldTouch: true, shouldDirty: true, shouldValidate: true}
                                                                                            );
                                                                                        }}
                                                                                    >
                                                                                    <span
                                                                                        className={`mr-2 h-4 w-4 material-symbols-outlined ${accountTypePopOverLabel === accountType.label ? 'opacity-100' : 'opacity-0'}`}
                                                                                    >check</span>
                                                                                        {t(accountType.label)}
                                                                                    </CommandItem>
                                                                                ))}
                                                                            </CommandGroup>
                                                                        </CommandList>
                                                                    </Command>
                                                                </PopoverContent>
                                                            </Popover>
                                                        </FormControl>
                                                        <FormMessage className="text-xs text-destructive"/>
                                                    </FormItem>
                                                )}
                                            />
                                        </div>
                                        <div className="flex flex-col space-y-1.5 mb-5">
                                            <FormField
                                                control={form.control}
                                                name="zipCode"
                                                render={({field}) => (
                                                    <FormItem>
                                                        <FormLabel>{t('shareholder_form_zip_code_label')}</FormLabel>
                                                        <FormControl>
                                                            <Input id="zipCode" type="text" {...field}
                                                                   disabled={mode === FormModeType.READ || isLoading}/>
                                                        </FormControl>
                                                        <FormMessage className="text-xs text-destructive"/>
                                                    </FormItem>
                                                )}
                                            />
                                        </div>
                                        <div className="flex flex-col space-y-1.5 mb-5">
                                            <FormField
                                                control={form.control}
                                                name="email"
                                                render={({field}) => (
                                                    <FormItem>
                                                        <FormLabel>{t('shareholder_form_email_label')}</FormLabel>
                                                        <FormControl>
                                                            <Input id="email" type="email" {...field}
                                                                   disabled={mode === FormModeType.READ || isLoading}/>
                                                        </FormControl>
                                                        <FormMessage className="text-xs text-destructive"/>
                                                    </FormItem>
                                                )}
                                            />
                                        </div>
                                        <div className="flex flex-col space-y-1.5 mb-5">
                                            <FormField
                                                control={form.control}
                                                name="createdDate"
                                                render={({ field }) => (
                                                    <FormItem>
                                                        <FormLabel>{t('shareholder_form_created_date_label')}</FormLabel>
                                                        <Popover>
                                                            <PopoverTrigger asChild>
                                                                <FormControl>
                                                                    <Button
                                                                        variant={"outline"}
                                                                        className={cn(
                                                                            "w-full  text-left font-normal",
                                                                            !field.value && "text-muted-foreground"
                                                                        )}
                                                                        disabled={mode === FormModeType.READ || isLoading}
                                                                    >
                                                                        {field.value ? (
                                                                            format(field.value, "dd/MM/yyyy HH:mm:ss")
                                                                        ) : (
                                                                            <span>{t('shareholder_form_created_date_default_message')}</span>
                                                                        )}
                                                                        <CalendarIcon className="ml-auto h-4 w-4 opacity-50" />
                                                                    </Button>
                                                                </FormControl>
                                                            </PopoverTrigger>
                                                            <PopoverContent className="w-auto p-0" align="start">
                                                                <Calendar
                                                                    mode="single"
                                                                    selected={field.value}
                                                                    onSelect={field.onChange}
                                                                    disabled={(date) =>
                                                                        date > new Date() || date < new Date("1900-01-01")
                                                                    }
                                                                    initialFocus
                                                                />
                                                            </PopoverContent>
                                                        </Popover>
                                                        <FormMessage />
                                                    </FormItem>
                                                )}
                                            />
                                        </div>

                                    </div>
                                </div>
                            </div>
                            <Tabs defaultValue="shareholder_type" className="w-full">
                                <TabsList>
                                    <TabsTrigger
                                        value="shareholder_type">{t('shareholder_type_form_tab_shareholder_type')}</TabsTrigger>
                                </TabsList>
                                <TabsContent value="shareholder_type">
                                    <div className="flex flex-row lg:flex-row w-full gap-4">
                                        <div className="lg:w-6/12 mb-4">
                                            <div className="flex flex-col space-y-1.5 mb-5">
                                                <FormField
                                                    control={form.control}
                                                    name="shareHolderType"
                                                    render={() => (
                                                        <FormItem>
                                                            <FormLabel>{t('shareholder_form_shareholder_type_label')}</FormLabel>
                                                            <FormControl>
                                                                <Popover open={openShareHolderTypePopOver} onOpenChange={setOpenShareHolderTypePopOver}>
                                                                    <PopoverTrigger asChild>
                                                                        <Button
                                                                            variant="outline"
                                                                            role="combobox"
                                                                            aria-expanded={openShareHolderTypePopOver}
                                                                            className="w-full justify-between"
                                                                            disabled={mode === FormModeType.READ || isLoading}
                                                                        >
                                                                    <span>{shareHolderTypePopOverLabel
                                                                        //@ts-ignore
                                                                        ? t(shareHolderTypes.find((shareHolderType) => shareHolderType.label === shareHolderTypePopOverLabel)?.label)
                                                                        //@ts-ignore
                                                                        : shareHolder && shareHolder.shareHolderType ? t(shareHolderTypes.find((shareHolderTypeEdit) => shareHolderTypeEdit.name === shareHolder.shareHolderType)?.label) : t('shareholder_form_shareholder_type_pop_over_place_holder')}</span>
                                                                            <span
                                                                                className="opacity-50 material-symbols-outlined">unfold_more</span>
                                                                        </Button>
                                                                    </PopoverTrigger>
                                                                    <PopoverContent className="w-[--radix-popover-trigger-width]">
                                                                        <Command>
                                                                            <CommandInput id="shareHolderType" placeholder={t('shareholder_form_shareholder_type_pop_over_place_holder')} />
                                                                            <CommandList>
                                                                                <Command>{t('shareholder_form_pop_shareholder_type_over_not_found')}</Command>
                                                                                <CommandGroup>
                                                                                    {shareHolderTypes.map((shareHolderType) => (
                                                                                        <CommandItem
                                                                                            key={shareHolderType.label}
                                                                                            value={shareHolderType.label}
                                                                                            onSelect={(currentValue) => {
                                                                                                setShareHolderTypePopOverLabel(currentValue === shareHolderTypePopOverLabel ? "" : currentValue);
                                                                                                setOpenShareHolderTypePopOver(false);
                                                                                                form.setValue(
                                                                                                    "shareHolderType",
                                                                                                    currentValue === shareHolderTypePopOverLabel ? "" : shareHolderType.name,
                                                                                                    {shouldTouch: true, shouldDirty: true, shouldValidate: true}
                                                                                                );
                                                                                            }}
                                                                                        >
                                                                                    <span
                                                                                        className={`mr-2 h-4 w-4 material-symbols-outlined ${shareHolderTypePopOverLabel === shareHolderType.label ? 'opacity-100' : 'opacity-0'}`}
                                                                                    >check</span>
                                                                                            {t(shareHolderType.label)}
                                                                                        </CommandItem>
                                                                                    ))}
                                                                                </CommandGroup>
                                                                            </CommandList>
                                                                        </Command>
                                                                    </PopoverContent>
                                                                </Popover>
                                                            </FormControl>
                                                            <FormMessage className="text-xs text-destructive"/>
                                                        </FormItem>
                                                    )}
                                                />
                                            </div>
                                        </div>

                                        {
                                            form.getValues().shareHolderType === ShareHolderTypeEnum.SUCCESSOR.valueOf() &&
                                            <div className="lg:w-6/12 mb-4">
                                                <div className="flex flex-col space-y-1.5 mb-5">
                                                    <FormField
                                                        control={form.control}
                                                        name="successor.name"
                                                        render={({field}) => (
                                                            <FormItem>
                                                                <FormLabel>{t('shareholder_form_successor_name_label')}</FormLabel>
                                                                <FormControl>
                                                                    <Input id="successor.name" type="text" {...field}
                                                                           disabled={mode === FormModeType.READ || isLoading}/>
                                                                </FormControl>
                                                                <FormMessage className="text-xs text-destructive"/>
                                                            </FormItem>
                                                        )}
                                                    />
                                                </div>
                                                <div className="flex flex-col space-y-1.5 mb-5">
                                                    <FormField
                                                        control={form.control}
                                                        name="successor.phone"
                                                        render={({field}) => (
                                                            <FormItem>
                                                                <FormLabel>{t('shareholder_form_successor_phone_label')}</FormLabel>
                                                                <FormControl>
                                                                    <Input id="successor.phone" type="text" {...field}
                                                                           disabled={mode === FormModeType.READ || isLoading}/>
                                                                </FormControl>
                                                                <FormMessage className="text-xs text-destructive"/>
                                                            </FormItem>
                                                        )}
                                                    />
                                                </div>
                                                <div className="flex flex-col space-y-1.5 mb-5">
                                                    <FormField
                                                        control={form.control}
                                                        name="successor.email"
                                                        render={({field}) => (
                                                            <FormItem>
                                                                <FormLabel>{t('shareholder_form_successor_email_label')}</FormLabel>
                                                                <FormControl>
                                                                    <Input id="successor.email" type="email" {...field}
                                                                           disabled={mode === FormModeType.READ || isLoading}/>
                                                                </FormControl>
                                                            </FormItem>
                                                        )}
                                                    />
                                                </div>
                                            </div>
                                        }

                                        {
                                            form.getValues().shareHolderType === ShareHolderTypeEnum.REPRESENTATIVE.valueOf() &&
                                            <div className="lg:w-6/12 mb-4">
                                                <div className="flex flex-col space-y-1.5 mb-5">
                                                    <FormField
                                                        control={form.control}
                                                        name="representative.name"
                                                        render={({field}) => (
                                                            <FormItem>
                                                                <FormLabel>{t('shareholder_form_representative_name_label')}</FormLabel>
                                                                <FormControl>
                                                                    <Input id="representative.name" type="text" {...field}
                                                                           disabled={mode === FormModeType.READ || isLoading}/>
                                                                </FormControl>
                                                                <FormMessage className="text-xs text-destructive"/>
                                                            </FormItem>
                                                        )}
                                                    />
                                                </div>
                                                <div className="flex flex-col space-y-1.5 mb-5">
                                                    <FormField
                                                        control={form.control}
                                                        name="representative.phone"
                                                        render={({field}) => (
                                                            <FormItem>
                                                                <FormLabel>{t('shareholder_form_representative_phone_label')}</FormLabel>
                                                                <FormControl>
                                                                    <Input id="representative.phone" type="text" {...field}
                                                                           disabled={mode === FormModeType.READ || isLoading}/>
                                                                </FormControl>
                                                                <FormMessage className="text-xs text-destructive"/>
                                                            </FormItem>
                                                        )}
                                                    />
                                                </div>
                                                <div className="flex flex-col space-y-1.5 mb-5">
                                                    <FormField
                                                        control={form.control}
                                                        name="representative.email"
                                                        render={({field}) => (
                                                            <FormItem>
                                                                <FormLabel>{t('shareholder_form_representative_email_label')}</FormLabel>
                                                                <FormControl>
                                                                    <Input id="representative.email" type="email" {...field}
                                                                           disabled={mode === FormModeType.READ || isLoading}/>
                                                                </FormControl>
                                                            </FormItem>
                                                        )}
                                                    />
                                                </div>
                                            </div>
                                        }

                                    </div>
                                </TabsContent>
                            </Tabs>
                        </CardContent>
                    </Card>
                </form>
                <DevTool control={form.control}/>
            </Form>

        </div>
    );
};

export default ShareHolderForm;