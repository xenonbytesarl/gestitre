import {TimezoneEnum} from "@/pages/admin/user/TimezoneEnum.ts";
import {useTranslation} from "react-i18next";
import {useNavigate, useParams} from "react-router-dom";
import {useToast} from "@/hooks/use-toast.ts";
import {useDispatch, useSelector} from "react-redux";
import {RootDispatch} from "@/core/Store.ts";
import {UserModel} from "@/pages/admin/user/UserModel.ts";
import {createUser, findUserById, getCurrentUser, getLoading, resetCurrentUser} from "@/pages/admin/user/UserSlice.ts";
import {CompanyModel} from "@/pages/company/CompanyModel.ts";
import {searchCompanies, selectCompanies} from "@/pages/company/CompanySlice.ts";
import {useEffect, useState} from "react";
import {FormModeType} from "@/shared/model/FormModeType.ts";
import {z} from "zod";
import {useForm} from "react-hook-form";
import {zodResolver} from "@hookform/resolvers/zod";
import {DEFAULT_DIRECTION_VALUE, MAX_SIZE_VALUE} from "@/shared/constant/page.constant.ts";
import {changeNullToEmptyString} from "@/shared/utils/changeNullToEmptyString.ts";
import {unwrapResult} from "@reduxjs/toolkit";
import {ToastType} from "@/shared/constant/globalConstant.ts";
import {cn} from "@/lib/utils.ts";

const timezoneTypes = [
    {label: 'user_form_timezone_africa_brazaville', name: TimezoneEnum.Africa_Brazzaville},
    {label: 'user_form_timezone_africa_douala', name: TimezoneEnum.Africa_Douala},
    {label: 'user_form_timezone_africa_kinshasa', name: TimezoneEnum.Africa_Kinshasa},
    {label: 'user_form_timezone_africa_libreville', name: TimezoneEnum.Africa_Libreville},
    {label: 'user_form_timezone_europe_paris', name: TimezoneEnum.Europe_Paris},
    {label: 'user_form_timezone_america_los_angeles', name: TimezoneEnum.America_Los_Angeles},
];

const UserForm = () => {

    const {t} = useTranslation(['home']);

    const {userId} = useParams();

    const {toast} = useToast();

    const user: UserModel = useSelector(getCurrentUser);
    const companies: Array<CompanyModel> = useSelector(selectCompanies);
    const isLoading: boolean = useSelector(getLoading);
    const dispatch = useDispatch<RootDispatch>();

    const [openCompanyPopOver, setOpenCompanyPopOver] = useState(false);
    const [companyPopOverLabel, setCompanyPopOverLabel] = useState("");
    const [openTimezonePopOver, setOpenTimezonePopOver] = useState(false);
    const [timezonePopOverLabel, setTimezonePopOverLabel] = useState("");

    const navigate = useNavigate();
    const [mode, setMode] = useState<FormModeType>(userId? FormModeType.READ: FormModeType.CREATE);

    const UserSchema = z.object({
        id: z.string().min(0),
        name: z.string().min(1, {message: t('user_form_name_required_message')}).max(128, {message: t('user_form_name_max_length_message')}),
        email: z.string().min(1, {message: t('user_form_email_required_message')}).max(128, {message: t('user_form_email_max_length_message')}).email(t('user_form_email_invalid_message')),
        password: z.string().min(1, {message: t('user_form_password_required_message')}).min(6, {message: t('user_form_password_min_length_message')}),
        confirmPassword: z.string().min(1, {message: t('user_form_confirm_password_required_message')}).min(6, {message: t('user_form_confirm_password_min_length_message')}),
        timezone: z.string().min(1, {message: t('user_form_timezone_required_message')}),
        companyId: z.string().min(1, {message: t('user_form_company_required_message')}),
        tenantId: z.string().min(0),
        roles: z.array(z.object({
            id: z.string().min(1, {message: t('user_form_role_id_required_message')}),
            name: z.string().min(1, {message: t('user_form_role_name_required_message')}).max(128, {message: t('user_form_role_name_max_length_message')}),
            permissions: z.array(z.object({
                id: z.string().min(1, {message: t('user_form_permission_id_required_message')}),
                name: z.string().min(1, {message: t('user_form_permission_name_required_message')}).max(128, {message: t('user_form_permission_name_max_length_message')}),
            })),
            active: z.boolean(),
        })),
        useMfa: z.boolean(),
        accountEnabled: z.boolean(),
        accountExpired: z.boolean(),
        accountLocked: z.boolean(),
        credentialExpired: z.boolean(),
        failedLoginAttempt: z.coerce.number().nonnegative({message: t('user_form_failed_login_attempt_positive_message')})

    });

    const defaultUserValue: UserModel = {
        id: '',
        name: '',
        email: '',
        password: '',
        confirmPassword: '',
        timezone: TimezoneEnum.Africa_Douala,
        companyId: '',
        tenantId: '',
        roles: [{
            id: '',
            name: '',
            permissions: [{
                id: '',
                name: ''
            }],
            active: true
        }],
        useMfa: true,
        accountEnabled: false,
        accountExpired: false,
        accountLocked: false,
        credentialExpired: false,
        failedLoginAttempt: 0
    };

    const form = useForm<z.infer<typeof UserSchema>>({
        defaultValues: defaultUserValue,
        resolver: zodResolver(UserSchema),
        mode: "onChange"
    });

    useEffect(() => {
        dispatch(searchCompanies({page: 0, size: MAX_SIZE_VALUE, field: "name", direction: DEFAULT_DIRECTION_VALUE, keyword: ""}));
    }, []);

    useEffect(() => {
        // @ts-ignore
        if(!user && userId) {
            dispatch(findUserById(userId));
        }
    }, [dispatch]);

    useEffect(() => {
        if(user) {
            form.reset(changeNullToEmptyString(user))
        }
    }, [user]);

    const onSubmit = () => {
        const userFormValue: UserModel = form.getValues() as UserModel;
        dispatch(createUser(userFormValue))
            .then(unwrapResult)
            .then((response) => {
                setMode(FormModeType.READ);
                showToast("success", response.message);
                navigate(`/admin/users/form/details/${response.content.id}`);
            })
            .catch((error) => {
                setMode(FormModeType.CREATE);
                showToast("danger", error !== null && error.reason !== null? t(error.reason) : t(error));
            });
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
        if(user) {
            form.reset(changeNullToEmptyString(user));
            setMode(FormModeType.READ);
            resetPopOverLabel(user);
        } else {
            form.reset();
            resetPopOverLabel(undefined);
        }
    }

    const onCreate = () => {
        setMode(FormModeType.CREATE);
        form.reset(defaultUserValue);
        resetPopOverLabel(undefined);
        dispatch(resetCurrentUser())
        navigate('/companies/form/new');
    }

    const resetPopOverLabel = (company: CompanyModel | undefined) =>{
        if(company) {
            setCompanyPopOverLabel(companies.find(company => company.id === user.companyId)?.companyName as string);
            setTimezonePopOverLabel(timezoneTypes.find(timezone => user.timezone === timezone.name)?.label as string);
        } else {
            setCompanyPopOverLabel('');
            setTimezonePopOverLabel('');
        }
        setOpenCompanyPopOver(false);
        setOpenTimezonePopOver(false);
    }

    return (
        <div>
            User Form
        </div>
    );
};

export default UserForm;