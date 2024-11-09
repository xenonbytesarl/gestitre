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
import {RootDispatch} from "@/Store.ts";
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
        companyName: z.string().min(1, {message: t('company_form_company_name_required_message')}).and(z.string().max(64, {message: 'company_form_company_name_max_length_message'})),
        companyManagerName: z.string().min(1, {message: t('company_form_company_manager_name_required_message')}).and(z.string().max(64, {message: 'company_form_company_manager_name_max_length_message'})),
        licence: z.string().min(1, {message: t('company_form_legal_form_required_message')}),
        legalForm: z.string().min(1, {message: t('company_form_licence_required_message')}),
        address: z.object({
            street: z.string().min(0).max(128, {message: 'company_form_address_street_max_length_message'}),
            city: z.string().min(1, {message: t('company_form_address_city_required_message')}).max(128, {message: 'company_form_address_city_max_length_message'}),
            zipCode: z.string().min(1, {message: t('company_form_zip_code_required_message')}).max(16, {message: 'company_form_address_zip_code_max_length_message'}),
            country: z.string().min(1, {message: t('company_form_address_country_required_message')}).max(64, {message: 'company_form_address_country_max_length_message'}),
        }),
        contact: z.object({
            fax: z.string().min(0).max(32, {message: 'company_form_contact_fax_max_length_message'}),
            phone: z.string().min(0).max(32, {message: 'company_form_contact_phone_max_length_message'}),
            email: z.string().min(1, {message: t('company_form_contact_zip_code_required_message')}).max(128, {message: 'company_form_contact_email_max_length_message'}),
            name: z.string().min(1, {message: t('company_form_contact_country_required_message')}).max(64, {message: 'company_form_contact_name_max_length_message'}),
        }),
        activity: z.string().min(0).max(128, {message: 'company_form_activity_max_length_message'}),
        registrationNumber: z.string().min(0).max(32, {message: 'company_form_registration_number_max_length_message'}),
        certificateTemplateId: z.string().min(0),
        webSiteUrl: z.string().min(0).max(64, {message: 'company_form_website_url_max_length_message'}),
        isinCode: z.string().min(0).max(32, {message: 'company_form_isin_code_max_length_message'}),
        taxCode: z.string().min(0).max(32, {message: 'company_form__tax_code_max_length_message'}),
        logoFilename: z.string().min(0),
        encodedLogo: z.string().min(0),
        mimeLogo: z.string().min(0),
        stampFilename: z.string().min(0),
        encodedStamp: z.string().min(0),
        mimeStamp: z.string().min(0),
        grossDividendStockUnit: z.coerce.number().nonnegative({message: t('company_form_gross_dividend_stock_unit_positive_message')}),
        nominalValue: z.coerce.number().nonnegative({message: t('company_form_nominal_value_positive_message')}),
        netDividendStock: z.coerce.number().nonnegative({message: t('company_form_net_dividend_stock_positive_message')}),
        netDividendStockUnit: z.coerce.number().nonnegative({message: t('company_form_net_dividend_stock_unit_positive_message')}),
        capitalization: z.coerce.number().nonnegative({message: t('company_form_capitalization_positive_message')}),
        stockQuantity: z.coerce.number().nonnegative({message: t('company_form_stock_quantity_positive_message')}),
        ircmRetain: z.coerce.number().nonnegative({message: t('company_form_ircm_retain_positive_message')}),
        active: z.boolean(),
    });

    const defaultCompanyValue: CompanyModel = {
        id: "",
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
        taxCode: "",
        logoFilename: "",
        encodedLogo: "",
        mimeLogo: "",
        stampFilename: "",
        encodedStamp: "",
        mimeStamp: "",
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
        if((!company && companyId) || (company && !company.encodedLogo && companyId) || (company && !company.encodedStamp && companyId)) {
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
        } else {
            setCertificateTemplatePopOverLabel('');
            setLicencePopOverLabel('');
            setLegalFormPopOverLabel('');
        }
        setOpenCertificateTemplatePopOver(false);
        setOpenLicencePopOver(false);
        setOpenLegalFormPopOver(false);
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
            <h1 className="text-5xl">Company Form</h1>
        </div>
    );
};

export default CompanyForm;