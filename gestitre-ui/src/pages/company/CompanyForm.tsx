import {LicenceEnum} from "@/pages/company/LicenceEnum.ts";
import {LegalFormEnum} from "@/pages/company/LegalFormEnum.ts";
import {useTranslation} from "react-i18next";
import {useNavigate, useParams} from "react-router-dom";
import {useToast} from "@/hooks/use-toast.ts";
import {
    createCompany,
    findCompanyById,
    getCurrentCompany,
    getLoading,
    resetCurrentCompany
} from "@/pages/company/CompanySlice.ts";
import {useDispatch, useSelector} from "react-redux";
import {CertificateTemplateModel} from "@/pages/company/certificatetemplate/CertificateTemplateModel.ts";
import {
    findCertificateTemplates,
    selectCertificateTemplates
} from "@/pages/company/certificatetemplate/CertificateTemplateSlice.ts";
import {RootDispatch} from "@/core/Store.ts";
import {ChangeEvent, useEffect, useState} from "react";
import {FormModeType} from "@/shared/model/FormModeType.ts";
import {z} from "zod";
import {CompanyModel} from "@/pages/company/CompanyModel.ts";
import {useForm} from "react-hook-form";
import {zodResolver} from "@hookform/resolvers/zod";
import {DEFAULT_DIRECTION_VALUE, MAX_SIZE_VALUE} from "@/shared/constant/page.constant.ts";
import {changeNullToEmptyString} from "@/shared/utils/changeNullToEmptyString.ts";
import {DEFAULT_COMPANY_LOGO_IMAGE, DEFAULT_COMPANY_STAMP_IMAGE, ToastType} from "@/shared/constant/globalConstant.ts";
import {cn} from "@/lib/utils.ts";
import {unwrapResult} from "@reduxjs/toolkit";
import {Form, FormControl, FormField, FormItem, FormLabel, FormMessage} from "@/components/ui/form.tsx";
import {Card, CardContent, CardDescription, CardHeader, CardTitle} from "@/components/ui/card.tsx";
import {Toaster} from "@/components/ui/toaster.tsx";
import FormCrudButton from "@/components/FormCrudButton.tsx";
import {Input} from "@/components/ui/input.tsx";
import {getImageUrl, pathToFile, showString64Image} from "@/shared/utils/imageUtils.ts";
import {Tabs, TabsContent, TabsList, TabsTrigger} from "@/components/ui/tabs.tsx";
import {Popover, PopoverContent, PopoverTrigger} from "@/components/ui/popover.tsx";
import {Button} from "@/components/ui/button.tsx";
import {Command, CommandGroup, CommandInput, CommandItem, CommandList} from "@/components/ui/command.tsx";
import {Checkbox} from "@/components/ui/checkbox.tsx";
import {countries} from "@/shared/constant/country.ts";

const licenceTypes = [
    {label: 'company_form_licence_month_12', name: LicenceEnum.MONTH_12},
    {label: 'company_form_licence_month_24', name: LicenceEnum.MONTH_24},
    {label: 'company_form_licence_month_36', name: LicenceEnum.MONTH_36},
];

const legalFormTypes = [
    {label: 'company_form_legal_form_ei', name: LegalFormEnum.EI},
    {label: 'company_form_legal_form_sa', name: LegalFormEnum.SA},
    {label: 'company_form_legal_form_se', name: LegalFormEnum.SE},
    {label: 'company_form_legal_form_gie', name: LegalFormEnum.GIE},
    {label: 'company_form_legal_form_gip', name: LegalFormEnum.GIP},
    {label: 'company_form_legal_form_sas', name: LegalFormEnum.SAS},
    {label: 'company_form_legal_form_sca', name: LegalFormEnum.SCA},
    {label: 'company_form_legal_form_sci', name: LegalFormEnum.SCI},
    {label: 'company_form_legal_form_scp', name: LegalFormEnum.SCP}
];

const CompanyForm = () => {

    const {t} = useTranslation(['home']);

    const {companyId} = useParams();

    const {toast} = useToast();

    const company: CompanyModel = useSelector(getCurrentCompany);
    const certificateTemplates: Array<CertificateTemplateModel> = useSelector(selectCertificateTemplates);
    const isLoading: boolean = useSelector(getLoading);
    const dispatch = useDispatch<RootDispatch>();

    const [openCertificateTemplatePopOver, setOpenCertificateTemplatePopOver] = useState(false);
    const [certificateTemplatePopOverLabel, setCertificateTemplatePopOverLabel] = useState("");
    const [openLicencePopOver, setOpenLicencePopOver] = useState(false);
    const [licencePopOverLabel, setLicencePopOverLabel] = useState("");
    const [openLegalFormPopOver, setOpenLegalFormPopOver] = useState(false);
    const [legalFormPopOverLabel, setLegalFormPopOverLabel] = useState("");
    const [openCountryPopOver, setOpenCountryPopOver] = useState(false);
    const [countryPopOverLabel, setCountryPopOverLabel] = useState("");

    const navigate = useNavigate();
    const [mode, setMode] = useState<FormModeType>(companyId? FormModeType.READ: FormModeType.CREATE);

    // @ts-ignore
    const [logoContent, setLogoContent] = useState<File>(null);
    const [logoPreview, setLogoPreview] = useState('');
    // @ts-ignore
    const [stampContent, setStampContent] = useState<File>(null);
    const [stampPreview, setStampPreview] = useState('');

    const CompanySchema = z.object({
        id: z.string().min(0),
        code: z.string().min(1, {message: t('company_form_code_required_message')}).min(6, {message: t('company_form_code_min_length_message')}).max(16, {message: t('company_form_code_max_length_message')}),
        companyName: z.string().min(1, {message: t('company_form_company_name_required_message')}).max(64, {message: t('company_form_company_name_max_length_message')}),
        companyManagerName: z.string().min(1, {message: t('company_form_company_manager_name_required_message')}).max(64, {message: t('company_form_company_manager_name_max_length_message')}),
        licence: z.string().min(1, {message: t('company_form_licence_required_message')}),
        legalForm: z.string().min(1, {message: t('company_form_legal_form_required_message')}),
        address: z.object({
            street: z.string().min(0).max(128, {message: t('company_form_address_street_max_length_message')}),
            city: z.string().min(1, {message: t('company_form_address_city_required_message')}).max(64, {message: t('company_form_address_city_max_length_message')}),
            zipCode: z.string().min(1, {message: t('company_form_zip_code_required_message')}).max(16, {message: t('company_form_address_zip_code_max_length_message')}),
            country: z.string().min(1, {message: t('company_form_address_country_required_message')}).max(64, {message: t('company_form_address_country_max_length_message')}),
        }),
        contact: z.object({
            fax: z.string().min(0).max(32, {message: t('company_form_contact_fax_max_length_message')}),
            phone: z.string().min(0).max(32, {message: t('company_form_contact_phone_max_length_message')}),
            email: z.string().min(1, {message: t('company_form_contact_email_required_message')}).max(128, {message: t('company_form_contact_email_max_length_message')}),
            name: z.string().min(1, {message: t('company_form_contact_name_required_message')}).max(64, {message: t('company_form_contact_name_max_length_message')}),
        }),
        activity: z.string().min(0).max(128, {message: t('company_form_activity_max_length_message')}),
        registrationNumber: z.string().min(0).max(32, {message: t('company_form_registration_number_max_length_message')}),
        certificateTemplateId: z.string().min(0),
        webSiteUrl: z.string().min(0).max(64, {message: t('company_form_website_url_max_length_message')}),
        isinCode: z.string().min(0).max(32, {message: t('company_form_isin_code_max_length_message')}),
        taxNumber: z.string().min(0).max(32, {message: t('company_form_tax_code_max_length_message')}),
        logoFilename: z.string().min(0),
        logoEncoded: z.string().min(0),
        logoMimeType: z.string().min(0),
        stampFilename: z.string().min(0),
        stampEncoded: z.string().min(0),
        stampMimeType: z.string().min(0),
        grossDividendStockUnit: z.coerce.number().nonnegative({message: t('company_form_gross_dividend_stock_unit_positive_message')}),
        nominalValue: z.coerce.number().nonnegative({message: t('company_form_nominal_value_positive_message')}),
        netDividendStock: z.coerce.number().nonnegative({message: t('company_form_net_dividend_stock_positive_message')}),
        capitalization: z.coerce.number().nonnegative({message: t('company_form_capitalization_positive_message')}),
        stockQuantity: z.coerce.number().nonnegative({message: t('company_form_stock_quantity_positive_message')}),
        ircmRetain: z.coerce.number().nonnegative({message: t('company_form_ircm_retain_positive_message')}),
        active: z.boolean(),
    });

    const defaultCompanyValue: CompanyModel = {
        id: "",
        code: "",
        companyName: "",
        companyManagerName: "",
        licence: "",
        legalForm: "",
        address: {
            street: "",
            city: "",
            zipCode: "",
            country: ""
        },
        contact: {
            fax: "",
            phone: "",
            email: "",
            name: ""
        },
        activity: "",
        registrationNumber: "",
        certificateTemplateId: "",
        webSiteUrl: "",
        isinCode: "",
        taxNumber: "",
        logoFilename: "",
        logoEncoded: "",
        logoMimeType: "",
        stampFilename: "",
        stampEncoded: "",
        stampMimeType: "",
        grossDividendStockUnit: 0,
        nominalValue: 0,
        netDividendStock: 0,
        capitalization: 0,
        stockQuantity: 0,
        ircmRetain: 0,
        active: true

    };

    const form = useForm<z.infer<typeof CompanySchema>>({
        defaultValues: defaultCompanyValue,
        resolver: zodResolver(CompanySchema),
        mode: "onChange"
    });

    useEffect(() => {
        dispatch(findCertificateTemplates({page: 0, size: MAX_SIZE_VALUE, field: "name", direction: DEFAULT_DIRECTION_VALUE}));
    }, []);

    useEffect(() => {
        // @ts-ignore
        if((!company && companyId) || (company && !company.logoEncoded && companyId) || (company && !company.stampEncoded && companyId)) {
            dispatch(findCompanyById(companyId));
        }
    }, [dispatch]);

    useEffect(() => {
        if(company) {
            form.reset(changeNullToEmptyString(company))
        }
    }, [company]);

    const onSubmit = () => {
        const companyFormValue: CompanyModel = form.getValues() as CompanyModel;
        if (companyFormValue.id) {
            /*const logoValues: File = logoContent === null || logoContent === undefined
                ?
                fileFromBase64(companyFormValue.encodedLogo, companyFormValue.logoFilename, companyFormValue.mimeLogo)
                : logoContent;
            const stampValues: File = stampContent === null || stampContent === undefined
                ?
                fileFromBase64(companyFormValue.encodedStamp, companyFormValue.stampFilename, companyFormValue.mimeStamp)
                : stampContent;
            dispatch(updateCompany({productId: companyFormValue.id, product: companyFormValue, logo: logoValues, stamp: stampValues}))
                .then(unwrapResult)
                .then((response) => {
                    setMode(FormModeType.READ);
                    showToast("info", response.message);
                })
                .catch((error) => {
                    setMode(FormModeType.EDIT);
                    showToast("danger", error !== null && error.reason !== null? t(error.reason) : t(error));
                })*/
        } else {
            const logoValues: File = logoContent;
            const stampValues: File = stampContent;
            if((logoValues === null || logoValues === undefined) && (stampValues === null || stampValues === undefined)) {
                pathToFile(DEFAULT_COMPANY_LOGO_IMAGE, 'image/png').then((logoResponse) => {
                    pathToFile(DEFAULT_COMPANY_STAMP_IMAGE, 'image/png').then((stampResponse) => {
                        dispatch(createCompany({company: companyFormValue, logo: logoResponse, stamp: stampResponse}))
                            .then(unwrapResult)
                            .then((response) => {
                                setMode(FormModeType.READ);
                                showToast("success", response.message);
                                navigate(`/companies/form/details/${response.content.id}`);
                            })
                            .catch((error) => {
                                setMode(FormModeType.CREATE);
                                showToast("danger", error !== null && error.reason !== null? t(error.reason) : t(error));
                        });
                    });

                })
            }
            else if((logoValues === null || logoValues === undefined) && stampValues != null) {
                pathToFile(DEFAULT_COMPANY_LOGO_IMAGE, 'image/png').then((logoResponse) => {
                    dispatch(createCompany({company: companyFormValue, logo: logoResponse, stamp: stampValues}))
                        .then(unwrapResult)
                        .then((response) => {
                            setMode(FormModeType.READ);
                            showToast("success", response.message);
                            navigate(`/companies/form/details/${response.content.id}`);
                        })
                        .catch((error) => {
                            setMode(FormModeType.CREATE);
                            showToast("danger", error !== null && error.reason !== null? t(error.reason) : t(error));
                        });
                })
            }
            else if((stampValues === null || stampValues === undefined)  && logoValues != null) {
                pathToFile(DEFAULT_COMPANY_LOGO_IMAGE, 'image/png').then((stampResponse) => {
                    dispatch(createCompany({company: companyFormValue, logo: logoValues, stamp: stampResponse}))
                        .then(unwrapResult)
                        .then((response) => {
                            setMode(FormModeType.READ);
                            showToast("success", response.message);
                            navigate(`/companies/form/details/${response.content.id}`);
                        })
                        .catch((error) => {
                            setMode(FormModeType.CREATE);
                            showToast("danger", error !== null && error.reason !== null? t(error.reason) : t(error));
                        });
                })
            }
            else {
                dispatch(createCompany({company: companyFormValue, logo: logoValues, stamp: stampValues}))
                    .then(unwrapResult)
                    .then((response) => {
                        setMode(FormModeType.READ);
                        showToast("success", response.message);
                        navigate(`/companies/form/details/${response.content.id}`);
                    })
                    .catch((error) => {
                        setMode(FormModeType.CREATE);
                        showToast("danger", error !== null && error.reason !== null? t(error.reason) : t(error));
                    });
            }

        }
    }

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
        if(company) {
            form.reset(changeNullToEmptyString(company));
            setMode(FormModeType.READ);
            resetPopOverLabel(company);
        } else {
            form.reset();
            resetPopOverLabel(undefined);
        }
    }

    const onCreate = () => {
        setMode(FormModeType.CREATE);
        form.reset(defaultCompanyValue);
        resetPopOverLabel(undefined);
        dispatch(resetCurrentCompany())
        navigate('/companies/form/new');
    }

    const resetPopOverLabel = (company: CompanyModel | undefined) =>{
        if(company) {
            setCertificateTemplatePopOverLabel(certificateTemplates.find(certificateTemplate => certificateTemplate.id === company.certificateTemplateId)?.name as string);
            setLicencePopOverLabel(licenceTypes.find(licence => company.licence === licence.name)?.label as string);
            setLegalFormPopOverLabel(legalFormTypes.find(legalForm => company.legalForm === legalForm.name)?.label as string);
            setCountryPopOverLabel(countries.find(country => company.address.country === country.name)?.label as string);
        } else {
            setCertificateTemplatePopOverLabel('');
            setLicencePopOverLabel('');
            setLegalFormPopOverLabel('');
            setCountryPopOverLabel('');
        }
        setOpenCertificateTemplatePopOver(false);
        setOpenLicencePopOver(false);
        setOpenLegalFormPopOver(false);
        setOpenCountryPopOver(false);
    }

    const handleLogoChange = (event: ChangeEvent<HTMLInputElement>) => {
        const content = event.target && event.target.files && event.target.files[0] || null;
        if(content) {
            setLogoPreview(URL.createObjectURL(content));
            setLogoContent(content);
        }
    }

    const handleStampChange = (event: ChangeEvent<HTMLInputElement>) => {
        const content = event.target && event.target.files && event.target.files[0] || null;
        if(content) {
            setStampPreview(URL.createObjectURL(content));
            setStampContent(content);
        }
    }

    return (
        <div>
            <Form {...form}>
                <form onSubmit={form.handleSubmit(onSubmit)} noValidate>
                    <Toaster/>
                    <Card className="shadow-xl">
                        <CardHeader>
                            <CardTitle className="flex flex-row justify-start items-center text-primary gap-5">
                                <span onClick={() => navigate(`/companies/tree`)}
                                        className="material-symbols-outlined text-3xl cursor-pointer">arrow_back</span>
                                <span
                                        className="text-2xl">{t(companyId ? 'company_form_edit_title' : 'company_form_new_title')}</span>
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
                            <div className="flex flex-col justify-center items-center gap-5">
                                <div className="flex flex-col lg:flex-row w-full gap-4">
                                    <div className="lg:w-4/12 mb-4">
                                        <div className="flex flex-col space-y-1.5 mb-5">
                                            <FormField
                                                control={form.control}
                                                name="companyName"
                                                render={({field}) => (
                                                    <FormItem>
                                                        <FormLabel>{t('company_form_company_name_label')}</FormLabel>
                                                        <FormControl>
                                                            <Input id="companyName" type="text" {...field}
                                                                   disabled={mode === FormModeType.READ || isLoading}/>
                                                        </FormControl>
                                                        <FormMessage className="text-xs text-destructive"/>
                                                    </FormItem>
                                                )}
                                            />
                                        </div>
                                    </div>
                                    <div className="lg:w-4/12 mb-4">
                                        <div className="flex flex-col space-y-1.5 mb-5">
                                            <FormField
                                                control={form.control}
                                                name="companyManagerName"
                                                render={({field}) => (
                                                    <FormItem>
                                                        <FormLabel>{t('company_form_company_manager_name_label')}</FormLabel>
                                                        <FormControl>
                                                            <Input id="companyManagerName" type="text" {...field}
                                                                   disabled={mode === FormModeType.READ || isLoading}/>
                                                        </FormControl>
                                                        <FormMessage className="text-xs text-destructive"/>
                                                    </FormItem>
                                                )}
                                            />
                                        </div>
                                    </div>
                                    <div className="lg:w-4/12 mb-4">
                                        <div className="flex flex-col space-y-1.5 mb-5">
                                            <FormField
                                                control={form.control}
                                                name="code"
                                                render={({field}) => (
                                                    <FormItem>
                                                        <FormLabel>{t('company_form_code_label')}</FormLabel>
                                                        <FormControl>
                                                            <Input id="code" type="text" {...field}
                                                                   disabled={mode === FormModeType.READ || isLoading}/>
                                                        </FormControl>
                                                        <FormMessage className="text-xs text-destructive"/>
                                                    </FormItem>
                                                )}
                                            />
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="flex flex-col justify-center items-center gap-5">
                                <div className="flex flex-col lg:flex-row w-full gap-4">
                                    <div className="lg:w-4/12 mb-4">
                                        <div className="flex flex-col space-y-1.5 mb-5">
                                            <FormField
                                                control={form.control}
                                                name="licence"
                                                render={() => (
                                                    <FormItem>
                                                        <FormLabel>{t('company_form_licence_label')}</FormLabel>
                                                        <FormControl>
                                                            <Popover open={openLicencePopOver} onOpenChange={setOpenLicencePopOver}>
                                                                <PopoverTrigger asChild>
                                                                    <Button
                                                                        variant="outline"
                                                                        role="combobox"
                                                                        aria-expanded={openLicencePopOver}
                                                                        className="w-full justify-between"
                                                                        disabled={mode === FormModeType.READ || isLoading}
                                                                    >
                                                                    <span>{licencePopOverLabel
                                                                        //@ts-ignore
                                                                        ? t(licenceTypes.find((licence) => licence.label === licencePopOverLabel)?.label)
                                                                        //@ts-ignore
                                                                        : company ? t(licenceTypes.find((licenceEdit) => licenceEdit.name === company.licence)?.label) : t('company_form_licence_pop_over_place_holder')}</span>
                                                                        <span
                                                                            className="opacity-50 material-symbols-outlined">unfold_more</span>
                                                                    </Button>
                                                                </PopoverTrigger>
                                                                <PopoverContent className="w-[--radix-popover-trigger-width]">
                                                                    <Command>
                                                                        <CommandInput id="type" placeholder={t('company_form_licence_pop_over_place_holder')} />
                                                                        <CommandList>
                                                                            <Command>{t('company_form_pop_licence_over_not_found')}</Command>
                                                                            <CommandGroup>
                                                                                {licenceTypes.map((licence) => (
                                                                                    <CommandItem
                                                                                        key={licence.label}
                                                                                        value={licence.label}
                                                                                        onSelect={(currentValue) => {
                                                                                            setLicencePopOverLabel(currentValue === licencePopOverLabel ? "" : currentValue);
                                                                                            setOpenLicencePopOver(false);
                                                                                            form.setValue(
                                                                                                "licence",
                                                                                                currentValue === licencePopOverLabel ? "" : licence.name,
                                                                                                {shouldTouch: true, shouldDirty: true, shouldValidate: true}
                                                                                            );
                                                                                        }}
                                                                                    >
                                                                                    <span
                                                                                        className={`mr-2 h-4 w-4 material-symbols-outlined ${licencePopOverLabel === licence.label ? 'opacity-100' : 'opacity-0'}`}
                                                                                    >check</span>
                                                                                        {t(licence.label)}
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
                                    <div className="lg:w-4/12 mb-4">
                                        <div className="flex flex-col space-y-1.5 mb-5">
                                            <FormField
                                                control={form.control}
                                                name="legalForm"
                                                render={() => (
                                                    <FormItem>
                                                        <FormLabel>{t('company_form_legal_form_label')}</FormLabel>
                                                        <FormControl>
                                                            <Popover open={openLegalFormPopOver} onOpenChange={setOpenLegalFormPopOver}>
                                                                <PopoverTrigger asChild>
                                                                    <Button
                                                                        variant="outline"
                                                                        role="combobox"
                                                                        aria-expanded={openLegalFormPopOver}
                                                                        className="w-full justify-between"
                                                                        disabled={mode === FormModeType.READ || isLoading}
                                                                    >
                                                                    <span>{legalFormPopOverLabel
                                                                        //@ts-ignore
                                                                        ? t(legalFormTypes.find((legalForm) => legalForm.label === legalFormPopOverLabel)?.label)
                                                                        //@ts-ignore
                                                                        : company ? t(legalFormTypes.find((legalFormEdit) => legalFormEdit.name === company.legalForm)?.label) : t('company_form_legal_form_pop_over_place_holder')}</span>
                                                                        <span
                                                                            className="opacity-50 material-symbols-outlined">unfold_more</span>
                                                                    </Button>
                                                                </PopoverTrigger>
                                                                <PopoverContent className="w-[--radix-popover-trigger-width]">
                                                                    <Command>
                                                                        <CommandInput id="type" placeholder={t('company_form_legal_form_pop_over_place_holder')} />
                                                                        <CommandList>
                                                                            <Command>{t('company_form_pop_legal_form_over_not_found')}</Command>
                                                                            <CommandGroup>
                                                                                {legalFormTypes.map((legalForm) => (
                                                                                    <CommandItem
                                                                                        key={legalForm.label}
                                                                                        value={legalForm.label}
                                                                                        onSelect={(currentValue) => {
                                                                                            setLegalFormPopOverLabel(currentValue === legalFormPopOverLabel ? "" : currentValue);
                                                                                            setOpenLegalFormPopOver(false);
                                                                                            form.setValue(
                                                                                                "legalForm",
                                                                                                currentValue === legalFormPopOverLabel ? "" : legalForm.name,
                                                                                                {shouldTouch: true, shouldDirty: true, shouldValidate: true}
                                                                                            );
                                                                                        }}
                                                                                    >
                                                                                    <span
                                                                                        className={`mr-2 h-4 w-4 material-symbols-outlined ${legalFormPopOverLabel === legalForm.label ? 'opacity-100' : 'opacity-0'}`}
                                                                                    >check</span>
                                                                                        {t(legalForm.label)}
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
                                    <div className="lg:w-4/12 mb-4">
                                        <div className="flex flex-col space-y-1.5 mb-5">
                                            <FormField
                                                control={form.control}
                                                name="certificateTemplateId"
                                                render={() => (
                                                    <FormItem>
                                                        <FormLabel>{t('company_form_certificate_template_id_label')}</FormLabel>
                                                        <FormControl>
                                                            <Popover open={openCertificateTemplatePopOver} onOpenChange={setOpenCertificateTemplatePopOver}>
                                                                <PopoverTrigger asChild>
                                                                    <Button
                                                                        variant="outline"
                                                                        role="combobox"
                                                                        aria-expanded={openCertificateTemplatePopOver}
                                                                        className="w-full justify-between"
                                                                        disabled={mode === FormModeType.READ || isLoading}
                                                                    >
                                                                    <span>{certificateTemplatePopOverLabel
                                                                        //@ts-ignore
                                                                        ? t(certificateTemplates.find((certificateTemplate) => certificateTemplate.name === certificateTemplatePopOverLabel)?.name)
                                                                        //@ts-ignore
                                                                        : company ? t(certificateTemplates.find((certificateTemplateFormEdit) => certificateTemplateFormEdit.id === company.certificateTemplateId)?.label) : t('company_form_certificate_template_id_pop_over_place_holder')}</span>
                                                                        <span
                                                                            className="opacity-50 material-symbols-outlined">unfold_more</span>
                                                                    </Button>
                                                                </PopoverTrigger>
                                                                <PopoverContent className="w-[--radix-popover-trigger-width]">
                                                                    <Command>
                                                                        <CommandInput id="type" placeholder={t('company_form_certificate_template_id_pop_over_place_holder')} />
                                                                        <CommandList>
                                                                            <Command>{t('company_form_pop_certificate_template_id_over_not_found')}</Command>
                                                                            <CommandGroup>
                                                                                {certificateTemplates.map((certificateTemplate) => (
                                                                                    <CommandItem
                                                                                        key={certificateTemplate.id}
                                                                                        value={certificateTemplate.name}
                                                                                        onSelect={(currentValue) => {
                                                                                            setCertificateTemplatePopOverLabel(currentValue === certificateTemplatePopOverLabel ? "" : currentValue);
                                                                                            setOpenCertificateTemplatePopOver(false);
                                                                                            form.setValue(
                                                                                                "certificateTemplateId",
                                                                                                currentValue === certificateTemplatePopOverLabel ? "" : certificateTemplate.id,
                                                                                                {shouldTouch: true, shouldDirty: true, shouldValidate: true}
                                                                                            );
                                                                                        }}
                                                                                    >
                                                                                    <span
                                                                                        className={`mr-2 h-4 w-4 material-symbols-outlined ${certificateTemplatePopOverLabel === certificateTemplate.name ? 'opacity-100' : 'opacity-0'}`}
                                                                                    >check</span>
                                                                                        {t(certificateTemplate.name)}
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
                                </div>
                            </div>
                            <Tabs defaultValue="other" className="w-full">
                                <TabsList>
                                    <TabsTrigger
                                        value="other">{t('company_form_tab_other')}</TabsTrigger>
                                    <TabsTrigger
                                        value="address">{t('company_form_tab_address')}</TabsTrigger>
                                    <TabsTrigger
                                        value="contact">{t('company_form_tab_contact')}</TabsTrigger>
                                    <TabsTrigger value="images">{t('company_form_tab_images')}</TabsTrigger>
                                </TabsList>
                                <TabsContent value="other">
                                    <div className="flex flex-col lg:flex-row w-full gap-4">
                                        <div className="lg:w-4/12 mb-4">
                                            <div className="flex flex-col space-y-1.5 mb-5">
                                                <FormField
                                                    control={form.control}
                                                    name="isinCode"
                                                    render={({field}) => (
                                                        <FormItem>
                                                            <FormLabel>{t('company_form_isin_code_label')}</FormLabel>
                                                            <FormControl>
                                                                <Input id="isinCode" type="text" {...field}
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
                                                    name="taxNumber"
                                                    render={({field}) => (
                                                        <FormItem>
                                                            <FormLabel>{t('company_form_tax_number_label')}</FormLabel>
                                                            <FormControl>
                                                                <Input id="taxNumber" type="text" {...field}
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
                                                    name="registrationNumber"
                                                    render={({field}) => (
                                                        <FormItem>
                                                            <FormLabel>{t('company_form_registration_number_label')}</FormLabel>
                                                            <FormControl>
                                                                <Input id="registrationNumber"
                                                                       type="text" {...field}
                                                                       disabled={mode === FormModeType.READ || isLoading}/>
                                                            </FormControl>
                                                            <FormMessage className="text-xs text-destructive"/>
                                                        </FormItem>
                                                    )}
                                                />
                                            </div>
                                        </div>
                                        <div className="lg:w-4/12 mb-4">
                                            <div className="flex flex-col space-y-1.5 mb-5">
                                                <FormField
                                                    control={form.control}
                                                    name="activity"
                                                    render={({field}) => (
                                                        <FormItem>
                                                            <FormLabel>{t('company_form_activity_label')}</FormLabel>
                                                            <FormControl>
                                                                <Input id="activity" type="text" {...field}
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
                                                    name="webSiteUrl"
                                                    render={({field}) => (
                                                        <FormItem>
                                                            <FormLabel>{t('company_form_website_url_label')}</FormLabel>
                                                            <FormControl>
                                                                <Input id="webSiteUrl" type="text" {...field}
                                                                       disabled={mode === FormModeType.READ || isLoading}/>
                                                            </FormControl>
                                                            <FormMessage className="text-xs text-destructive"/>
                                                        </FormItem>
                                                    )}
                                                />
                                            </div>
                                            <div className="flex flex-col space-y-1.5 mt-16">
                                                <FormField
                                                    control={form.control}
                                                    name="active"
                                                    render={({field}) => (
                                                        <FormItem
                                                            className="flex flex-row items-center space-x-2 space-y-0">
                                                            <FormControl>
                                                                <Checkbox id="active" checked={field.value}
                                                                          disabled={mode === FormModeType.READ || isLoading}
                                                                          onCheckedChange={field.onChange}/>
                                                            </FormControl>
                                                            <FormLabel
                                                                className="font-normal">{t('company_form_active_label')}</FormLabel>
                                                        </FormItem>
                                                    )}
                                                />
                                            </div>
                                        </div>
                                        <div className="lg:w-4/12 mb-4">
                                            <div className="flex flex-col space-y-1.5 mb-5">
                                                <FormField
                                                    control={form.control}
                                                    name="nominalValue"
                                                    render={({field}) => (
                                                        <FormItem>
                                                            <FormLabel>{t('company_form_nominal_value_label')}</FormLabel>
                                                            <FormControl>
                                                                <Input id="nominalValue" type="number" {...field}
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
                                                    name="grossDividendStockUnit"
                                                    render={({field}) => (
                                                        <FormItem>
                                                            <FormLabel>{t('company_form_gross_dividend_stock_unit_label')}</FormLabel>
                                                            <FormControl>
                                                                <Input id="grossDividendStockUnit"
                                                                       type="number" {...field}
                                                                       disabled={mode === FormModeType.READ || isLoading}/>
                                                            </FormControl>
                                                            <FormMessage className="text-xs text-destructive"/>
                                                        </FormItem>
                                                    )}
                                                />
                                            </div>
                                        </div>
                                    </div>
                                </TabsContent>
                                <TabsContent value="address">
                                    <div className="flex flex-col lg:flex-row w-full gap-4">
                                        <div className="lg:w-6/12 mb-4">
                                            <div className="flex flex-col space-y-1.5 mb-5">
                                                <FormField
                                                    control={form.control}
                                                    name="address.street"
                                                    render={({field}) => (
                                                        <FormItem>
                                                            <FormLabel>{t('company_form_address_street_label')}</FormLabel>
                                                            <FormControl>
                                                                <Input id="address.street" type="text" {...field}
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
                                                    name="address.zipCode"
                                                    render={({field}) => (
                                                        <FormItem>
                                                            <FormLabel>{t('company_form_address_zip_code_label')}</FormLabel>
                                                            <FormControl>
                                                                <Input id="address.zipCode" type="text" {...field}
                                                                       disabled={mode === FormModeType.READ || isLoading}/>
                                                            </FormControl>
                                                            <FormMessage className="text-xs text-destructive"/>
                                                        </FormItem>
                                                    )}
                                                />
                                            </div>
                                        </div>
                                        <div className="lg:w-6/12 mb-4">
                                            <div className="flex flex-col space-y-1.5 mb-5">
                                                <FormField
                                                    control={form.control}
                                                    name="address.city"
                                                    render={({field}) => (
                                                        <FormItem>
                                                            <FormLabel>{t('company_form_address_city_label')}</FormLabel>
                                                            <FormControl>
                                                                <Input id="address.city" type="text" {...field}
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
                                                    name="address.country"
                                                    render={() => (
                                                        <FormItem>
                                                            <FormLabel>{t('company_form_address_country_label')}</FormLabel>
                                                            <FormControl>
                                                                <Popover open={openCountryPopOver} onOpenChange={setOpenCountryPopOver}>
                                                                    <PopoverTrigger asChild>
                                                                        <Button
                                                                            variant="outline"
                                                                            role="combobox"
                                                                            aria-expanded={openCountryPopOver}
                                                                            className="w-full justify-between"
                                                                            disabled={mode === FormModeType.READ || isLoading}
                                                                        >
                                                                    <span>{countryPopOverLabel
                                                                        //@ts-ignore
                                                                        ? t(countries.find((country) => country.label === countryPopOverLabel)?.label)
                                                                        //@ts-ignore
                                                                        : company ? t(countries.find((countryEdit) => countryEdit.name === company.address.country)?.label) : t('company_form_address_country_pop_over_place_holder')}</span>
                                                                            <span
                                                                                className="opacity-50 material-symbols-outlined">unfold_more</span>
                                                                        </Button>
                                                                    </PopoverTrigger>
                                                                    <PopoverContent className="w-[--radix-popover-trigger-width]">
                                                                        <Command>
                                                                            <CommandInput id="type" placeholder={t('company_form_address_country_pop_over_place_holder')} />
                                                                            <CommandList>
                                                                                <Command>{t('company_form_pop_address_country_over_not_found')}</Command>
                                                                                <CommandGroup>
                                                                                    {countries.map((country) => (
                                                                                        <CommandItem
                                                                                            key={country.label}
                                                                                            value={country.label}
                                                                                            onSelect={(currentValue) => {
                                                                                                setCountryPopOverLabel(currentValue === countryPopOverLabel ? "" : currentValue);
                                                                                                setOpenCountryPopOver(false);
                                                                                                form.setValue(
                                                                                                    "address.country",
                                                                                                    currentValue === countryPopOverLabel ? "" : country.name,
                                                                                                    {shouldTouch: true, shouldDirty: true, shouldValidate: true}
                                                                                                );
                                                                                            }}
                                                                                        >
                                                                                    <span
                                                                                        className={`mr-2 h-4 w-4 material-symbols-outlined ${countryPopOverLabel === country.label ? 'opacity-100' : 'opacity-0'}`}
                                                                                    >check</span>
                                                                                            {t(country.label)}
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
                                    </div>
                                </TabsContent>
                                <TabsContent value="contact">
                                    <div className="flex flex-col lg:flex-row w-full gap-4">
                                        <div className="lg:w-6/12 mb-4">
                                            <div className="flex flex-col space-y-1.5 mb-5">
                                                <FormField
                                                    control={form.control}
                                                    name="contact.name"
                                                    render={({field}) => (
                                                        <FormItem>
                                                            <FormLabel>{t('company_form_contact_name_label')}</FormLabel>
                                                            <FormControl>
                                                                <Input id="contact.name" type="text" {...field}
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
                                                    name="contact.email"
                                                    render={({field}) => (
                                                        <FormItem>
                                                            <FormLabel>{t('company_form_contact_email_label')}</FormLabel>
                                                            <FormControl>
                                                                <Input id="contact.email" type="text" {...field}
                                                                       disabled={mode === FormModeType.READ || isLoading}/>
                                                            </FormControl>
                                                            <FormMessage className="text-xs text-destructive"/>
                                                        </FormItem>
                                                    )}
                                                />
                                            </div>
                                        </div>
                                        <div className="lg:w-6/12 mb-4">
                                            <div className="flex flex-col space-y-1.5 mb-5">
                                                <FormField
                                                    control={form.control}
                                                    name="contact.fax"
                                                    render={({field}) => (
                                                        <FormItem>
                                                            <FormLabel>{t('company_form_contact_fax_label')}</FormLabel>
                                                            <FormControl>
                                                                <Input id="contact.fax" type="text" {...field}
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
                                                    name="contact.phone"
                                                    render={({field}) => (
                                                        <FormItem>
                                                            <FormLabel>{t('company_form_contact_phone_label')}</FormLabel>
                                                            <FormControl>
                                                                <Input id="contact.phone" type="text" {...field}
                                                                       disabled={mode === FormModeType.READ || isLoading}/>
                                                            </FormControl>
                                                            <FormMessage className="text-xs text-destructive"/>
                                                        </FormItem>
                                                    )}
                                                />
                                            </div>
                                        </div>
                                    </div>
                                </TabsContent>
                                <TabsContent value="images">
                                    <div className="flex flex-row justify-around items-start mt-8 gap-8">
                                        <div
                                            className="relative flex flex-col justify-center items-center w-72">
                                            <img alt="..." className={`size-48 bg-neutral-100 object-cover
                                flex flex-col items-center justify-center rounded-lg shadow-neutral-400 shadow-lg
                                gap-4 transition-all duration-1000 hover:text-primary`}
                                                //@ts-ignore
                                                 src={logoPreview ? logoPreview : company ? showString64Image(company.logoEncoded, company.logoMimeType) : getImageUrl(DEFAULT_COMPANY_LOGO_IMAGE)}/>
                                            <input
                                                className={`absolute top-0 size-48 opacity-0 ${mode === FormModeType.READ || isLoading ? 'cursor-auto' : 'cursor-pointer'}`}
                                                accept="image/x-png, image/jpeg" type="file"
                                                disabled={mode === FormModeType.READ || isLoading}
                                                onChange={handleLogoChange}/>

                                        </div>
                                        <div
                                            className="relative flex flex-col justify-center items-center w-72">
                                            <img alt="..." className={`size-48 bg-neutral-100 object-cover
                                flex flex-col items-center justify-center rounded-lg shadow-neutral-400 shadow-lg
                                gap-4 transition-all duration-1000 hover:text-primary`}
                                                //@ts-ignore
                                                 src={stampPreview ? stampPreview : company ? showString64Image(company.stampEncoded, company.stampMimeType) : getImageUrl(DEFAULT_COMPANY_STAMP_IMAGE)}/>
                                            <input
                                                className={`absolute top-0 size-48 opacity-0 ${mode === FormModeType.READ || isLoading ? 'cursor-auto' : 'cursor-pointer'}`}
                                                accept="image/x-png, image/jpeg" type="file"
                                                disabled={mode === FormModeType.READ || isLoading}
                                                onChange={handleStampChange}/>

                                        </div>
                                    </div>
                                </TabsContent>
                            </Tabs>
                        </CardContent>
                    </Card>
                </form>
            </Form>
        </div>
    );
};

export default CompanyForm;