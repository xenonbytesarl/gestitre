import {TimezoneEnum} from "@/pages/admin/user/TimezoneEnum.ts";
import {useTranslation} from "react-i18next";
import {useNavigate, useParams} from "react-router-dom";
import {useToast} from "@/hooks/use-toast.ts";
import {useDispatch, useSelector} from "react-redux";
import {RootDispatch} from "@/core/Store.ts";
import {UserModel} from "@/pages/admin/user/UserModel.ts";
import {
    createUser,
    findUserById,
    getCurrentUser,
    getLoading,
    getRoles,
    resetCurrentUser,
    searchRoles,
    updateUser
} from "@/pages/admin/user/UserSlice.ts";
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
import {Form, FormControl, FormField, FormItem, FormLabel, FormMessage} from "@/components/ui/form.tsx";
import {Card, CardContent, CardDescription, CardHeader, CardTitle} from "@/components/ui/card.tsx";
import FormCrudButton from "@/components/FormCrudButton.tsx";
import {Input} from "@/components/ui/input.tsx";
import {Popover, PopoverContent, PopoverTrigger} from "@/components/ui/popover.tsx";
import {Button} from "@/components/ui/button.tsx";
import {Command, CommandGroup, CommandInput, CommandItem, CommandList} from "@/components/ui/command.tsx";
import {Toaster} from "@/components/ui/toaster.tsx";
import {Tabs, TabsContent, TabsList, TabsTrigger} from "@/components/ui/tabs.tsx";
import {Checkbox} from "@/components/ui/checkbox.tsx";
import {RoleModel} from "@/pages/admin/user/RoleModel.ts";
import {LanguageEnum} from "@/pages/admin/user/LanguageEnum.ts";

const timezoneTypes = [
    {label: 'user_form_timezone_africa_brazaville', name: 'Africa_Brazzaville'},
    {label: 'user_form_timezone_africa_douala', name: 'Africa_Douala'},
    {label: 'user_form_timezone_africa_kinshasa', name: 'Africa_Kinshasa'},
    {label: 'user_form_timezone_africa_libreville', name: 'Africa_Libreville'},
    {label: 'user_form_timezone_europe_paris', name: 'Europe_Paris'},
    {label: 'user_form_timezone_america_los_angeles', name: 'America_Los_Angeles'},
];

const languageTypes = [
    {label: 'user_form_language_fr', name: LanguageEnum.FR},
    {label: 'user_form_language_en', name: LanguageEnum.EN}
];

const UserForm = () => {

    const {t} = useTranslation(['home']);

    const {userId} = useParams();

    const {toast} = useToast();

    const user: UserModel = useSelector(getCurrentUser);
    const companies: Array<CompanyModel> = useSelector(selectCompanies);
    const roles: Array<RoleModel> = useSelector(getRoles);
    const isLoading: boolean = useSelector(getLoading);
    const dispatch = useDispatch<RootDispatch>();

    const [openCompanyPopOver, setOpenCompanyPopOver] = useState(false);
    const [companyPopOverLabel, setCompanyPopOverLabel] = useState("");
    const [openRolePopOver, setOpenRolePopOver] = useState(false);
    const [rolePopOverLabel, setRolePopOverLabel] = useState("");
    const [openTimezonePopOver, setOpenTimezonePopOver] = useState(false);
    const [timezonePopOverLabel, setTimezonePopOverLabel] = useState("");
    const [openLanguagePopOver, setOpenLanguagePopOver] = useState(false);
    const [languagePopOverLabel, setLanguagePopOverLabel] = useState("");

    const navigate = useNavigate();
    const [mode, setMode] = useState<FormModeType>(userId? FormModeType.READ: FormModeType.CREATE);

    const UserSchema = z.object({
        id: z.string(),
        name: z.string().min(1, {message: t('user_form_name_required_message')}).max(128, {message: t('user_form_name_max_length_message')}),
        email: z.string().min(1, {message: t('user_form_email_required_message')}).max(128, {message: t('user_form_email_max_length_message')}).email(t('user_form_email_invalid_message')),
        password: z.string().min(1, {message: t('user_form_password_required_message')}).min(6, {message: t('user_form_password_min_length_message')}),
        confirmPassword: z.string().min(1, {message: t('user_form_confirm_password_required_message')}).min(6, {message: t('user_form_confirm_password_min_length_message')}),
        timezone: z.string().min(1, {message: t('user_form_timezone_required_message')}),
        language: z.string().min(1, {message: t('user_form_language_required_message')}),
        companyId: z.string().min(1, {message: t('user_form_company_id_required_message')}),
        tenantId: z.string(),
        roles: z.array(z.object({
            id: z.string().min(1, {message: t('user_form_roles_id_required_message')}),
            name: z.string().min(1, {message: t('user_form_roles_name_required_message')}),
            active: z.boolean(),
        })).nonempty(t('user_form_roles_required_message')),
        useMfa: z.boolean(),
        accountEnabled: z.boolean(),
        accountExpired: z.boolean(),
        accountLocked: z.boolean(),
        credentialExpired: z.boolean(),
        failedLoginAttempt: z.coerce.number().nonnegative({message: t('user_form_failed_login_attempt_positive_message')})

    }).superRefine((data, ctx) => {
        if(data.password && data.confirmPassword && data.password !== data.confirmPassword) {
            ctx.addIssue({
                path: ["confirmPassword"],
                message: t('user_form_confirm_password_not_match_message'),
                code: z.ZodIssueCode.custom
            });
        }
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
        language: LanguageEnum.FR,
        roles: [],
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
        dispatch(searchCompanies({page: 0, size: MAX_SIZE_VALUE, field: "companyName", direction: DEFAULT_DIRECTION_VALUE, keyword: ""}));
    }, []);

    useEffect(() => {
        dispatch(searchRoles({page: 0, size: MAX_SIZE_VALUE, field: "name", direction: DEFAULT_DIRECTION_VALUE, keyword: ""}));
    }, []);

    useEffect(() => {
        // @ts-ignore
        if(!user && userId) {
            dispatch(findUserById(userId));
        }
    }, [dispatch]);

    useEffect(() => {
        if(user) {
            form.reset(changeNullToEmptyString(user, defaultUserValue));
            resetPopOverLabel(user);
        }
    }, [user]);

    const onSubmit = () => {
        const userFormValue: UserModel = form.getValues() as UserModel;
        if(userFormValue.id) {
            dispatch(updateUser(userFormValue))
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
        } else {
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
        if(user) {
            form.reset(changeNullToEmptyString(user, defaultUserValue));
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
        navigate('/admin/users/form/new');
    }

    const resetPopOverLabel = (user: UserModel | undefined) => {
        if(user) {
            setCompanyPopOverLabel(companies.find(company => company.id === user.companyId)?.companyName as string);
            setRolePopOverLabel(roles.find(role => user.roles.find(userRole => userRole.id === role.id))?.name as string);
            setTimezonePopOverLabel(timezoneTypes.find(timezone => user.timezone.valueOf() === timezone.name)?.label as string);
            setLanguagePopOverLabel(languageTypes.find(language => user.language.valueOf() === language.name)?.label as string);
        } else {
            setCompanyPopOverLabel('');
            setRolePopOverLabel('');
            setTimezonePopOverLabel('');
            setLanguagePopOverLabel('');
        }
        setOpenCompanyPopOver(false);
        setOpenRolePopOver(false);
        setOpenTimezonePopOver(false);
        setOpenLanguagePopOver(false);
    }

    return (
      <div>
          <Form {...form}>
              <form onSubmit={form.handleSubmit(onSubmit)} noValidate>
                  <Toaster/>
                  <Card className="shadow-xl">
                      <CardHeader>
                          <CardTitle className="flex flex-row justify-start items-center text-primary gap-5">
                                <span onClick={() => navigate(`/admin/users/tree`)}
                                      className="material-symbols-outlined text-3xl cursor-pointer">arrow_back</span>
                              <span
                                  className="text-2xl">{t(userId ? 'user_form_edit_title' : 'user_form_new_title')}</span>
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
                                                      <FormLabel>{t('user_form_name_label')}</FormLabel>
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
                                              name="email"
                                              render={({field}) => (
                                                  <FormItem>
                                                      <FormLabel>{t('user_form_email_label')}</FormLabel>
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
                                              name="language"
                                              render={() => (
                                                  <FormItem>
                                                      <FormLabel>{t('user_form_language_label')}</FormLabel>
                                                      <FormControl>
                                                          <Popover open={openLanguagePopOver} onOpenChange={setOpenLanguagePopOver}>
                                                              <PopoverTrigger asChild>
                                                                  <Button
                                                                      variant="outline"
                                                                      role="combobox"
                                                                      aria-expanded={openLanguagePopOver}
                                                                      className="w-full justify-between"
                                                                      disabled={mode === FormModeType.READ || isLoading}
                                                                  >
                                                                    <span>{languagePopOverLabel
                                                                        //@ts-ignore
                                                                        ? t(languageTypes.find((language) => language.label === languagePopOverLabel)?.label)
                                                                        //@ts-ignore
                                                                        : user ? t(languageTypes.find((languageEdit) => languageEdit.label === user.language)?.label) : t('user_form_language_pop_over_place_holder')}</span>
                                                                      <span
                                                                          className="opacity-50 material-symbols-outlined">unfold_more</span>
                                                                  </Button>
                                                              </PopoverTrigger>
                                                              <PopoverContent className="w-[--radix-popover-trigger-width]">
                                                                  <Command>
                                                                      <CommandInput id="type" placeholder={t('user_form_language_pop_over_place_holder')} />
                                                                      <CommandList>
                                                                          <Command>{t('user_form_pop_language_over_not_found')}</Command>
                                                                          <CommandGroup>
                                                                              {languageTypes.map((language) => (
                                                                                  <CommandItem
                                                                                      key={language.label}
                                                                                      value={language.label}
                                                                                      onSelect={(currentValue) => {
                                                                                          setLanguagePopOverLabel(currentValue === languagePopOverLabel ? "" : currentValue);
                                                                                          setOpenLanguagePopOver(false);
                                                                                          form.setValue(
                                                                                              "language",
                                                                                              currentValue === languagePopOverLabel ? "" : language.name.valueOf(),
                                                                                              {shouldTouch: true, shouldDirty: true, shouldValidate: true}
                                                                                          );
                                                                                      }}
                                                                                  >
                                                                                    <span
                                                                                        className={`mr-2 h-4 w-4 material-symbols-outlined ${languagePopOverLabel === language.label ? 'opacity-100' : 'opacity-0'}`}
                                                                                    >check</span>
                                                                                      {t(language.label)}
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
                              <div className="flex flex-col justify-center items-center w-full">
                                  <div className="flex flex-col w-full gap-4">
                                      <div className="flex flex-col space-y-1.5 mb-5">
                                          <FormField
                                              control={form.control}
                                              name="password"
                                              render={({field}) => (
                                                  <FormItem>
                                                      <FormLabel>{t('user_form_password_label')}</FormLabel>
                                                      <FormControl>
                                                          <Input id="password" type="password" {...field}
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
                                              name="confirmPassword"
                                              render={({field}) => (
                                                  <FormItem>
                                                      <FormLabel>{t('user_form_confirm_password_label')}</FormLabel>
                                                      <FormControl>
                                                          <Input id="confirmPassword" type="password" {...field}
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
                                              name="timezone"
                                              render={() => (
                                                  <FormItem>
                                                      <FormLabel>{t('user_form_timezone_label')}</FormLabel>
                                                      <FormControl>
                                                          <Popover open={openTimezonePopOver} onOpenChange={setOpenTimezonePopOver}>
                                                              <PopoverTrigger asChild>
                                                                  <Button
                                                                      variant="outline"
                                                                      role="combobox"
                                                                      aria-expanded={openTimezonePopOver}
                                                                      className="w-full justify-between"
                                                                      disabled={mode === FormModeType.READ || isLoading}
                                                                  >
                                                                    <span>{timezonePopOverLabel
                                                                        //@ts-ignore
                                                                        ? t(timezoneTypes.find((timezone) => timezone.label === timezonePopOverLabel)?.label)
                                                                        //@ts-ignore
                                                                        : user ? t(timezoneTypes.find((timezoneEdit) => timezoneEdit.label === user.timezone)?.label) : t('user_form_timezone_pop_over_place_holder')}</span>
                                                                      <span
                                                                          className="opacity-50 material-symbols-outlined">unfold_more</span>
                                                                  </Button>
                                                              </PopoverTrigger>
                                                              <PopoverContent className="w-[--radix-popover-trigger-width]">
                                                                  <Command>
                                                                      <CommandInput id="type" placeholder={t('user_form_timezone_pop_over_place_holder')} />
                                                                      <CommandList>
                                                                          <Command>{t('user_form_pop_timezone_over_not_found')}</Command>
                                                                          <CommandGroup>
                                                                              {timezoneTypes.map((timezone) => (
                                                                                  <CommandItem
                                                                                      key={timezone.label}
                                                                                      value={timezone.label}
                                                                                      onSelect={(currentValue) => {
                                                                                          setTimezonePopOverLabel(currentValue === timezonePopOverLabel ? "" : currentValue);
                                                                                          setOpenTimezonePopOver(false);
                                                                                          form.setValue(
                                                                                              "timezone",
                                                                                              currentValue === timezonePopOverLabel ? "" : timezone.name.valueOf(),
                                                                                              {shouldTouch: true, shouldDirty: true, shouldValidate: true}
                                                                                          );
                                                                                      }}
                                                                                  >
                                                                                    <span
                                                                                        className={`mr-2 h-4 w-4 material-symbols-outlined ${timezonePopOverLabel === timezone.label ? 'opacity-100' : 'opacity-0'}`}
                                                                                    >check</span>
                                                                                      {t(timezone.label)}
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
                                              name="companyId"
                                              render={() => (
                                                  <FormItem>
                                                      <FormLabel>{t('user_form_company_id_label')}</FormLabel>
                                                      <FormControl>
                                                          <Popover open={openCompanyPopOver} onOpenChange={setOpenCompanyPopOver}>
                                                              <PopoverTrigger asChild>
                                                                  <Button
                                                                      variant="outline"
                                                                      role="combobox"
                                                                      aria-expanded={openCompanyPopOver}
                                                                      className="w-full justify-between"
                                                                      disabled={mode === FormModeType.READ || isLoading}
                                                                  >
                                                                    <span>{companyPopOverLabel
                                                                        //@ts-ignore
                                                                        ? t(companies.find((company) => company.companyName === companyPopOverLabel)?.companyName)
                                                                        //@ts-ignore
                                                                        : user ? t(companies.find((companyFormEdit) => companyFormEdit.id === user.companyId)?.label) : t('user_form_company_id_pop_over_place_holder')}</span>
                                                                      <span
                                                                          className="opacity-50 material-symbols-outlined">unfold_more</span>
                                                                  </Button>
                                                              </PopoverTrigger>
                                                              <PopoverContent className="w-[--radix-popover-trigger-width]">
                                                                  <Command>
                                                                      <CommandInput id="type" placeholder={t('user_form_company_id_pop_over_place_holder')} />
                                                                      <CommandList>
                                                                          <Command>{t('user_form_pop_company_id_over_not_found')}</Command>
                                                                          <CommandGroup>
                                                                              {companies.map((company) => (
                                                                                  <CommandItem
                                                                                      key={company.id}
                                                                                      value={company.companyName}
                                                                                      onSelect={(currentValue) => {
                                                                                          setCompanyPopOverLabel(currentValue === companyPopOverLabel ? "" : currentValue);
                                                                                          setOpenCompanyPopOver(false);
                                                                                          form.setValue(
                                                                                              "companyId",
                                                                                              currentValue === companyPopOverLabel ? "" : company.id,
                                                                                              {shouldTouch: true, shouldDirty: true, shouldValidate: true}
                                                                                          );
                                                                                      }}
                                                                                  >
                                                                                    <span
                                                                                        className={`mr-2 h-4 w-4 material-symbols-outlined ${companyPopOverLabel === company.companyName ? 'opacity-100' : 'opacity-0'}`}
                                                                                    >check</span>
                                                                                      {t(company.companyName)}
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
                          <Tabs defaultValue="security" className="w-full">
                              <TabsList>
                                  <TabsTrigger value="security">{t('user_form_tab_security')}</TabsTrigger>
                                  <TabsTrigger value="status">{t('user_form_tab_status')}</TabsTrigger>
                              </TabsList>
                              <TabsContent value="security">
                                  <div className="flex flex-col lg:flex-row w-full gap-4">
                                      <div className="lg:w-6/12 mb-4">
                                          <div className="flex flex-col space-y-1.5 mb-5">
                                              <FormField
                                                  control={form.control}
                                                  name="roles"
                                                  render={() => (
                                                      <FormItem>
                                                          <FormLabel>{t('company_form_roles_label')}</FormLabel>
                                                          <FormControl>
                                                              <Popover open={openRolePopOver} onOpenChange={setOpenRolePopOver}>
                                                                  <PopoverTrigger asChild>
                                                                      <Button
                                                                          variant="outline"
                                                                          role="combobox"
                                                                          aria-expanded={openRolePopOver}
                                                                          className="w-full justify-between"
                                                                          disabled={mode === FormModeType.READ || isLoading}
                                                                      >
                                                                    <span>{rolePopOverLabel
                                                                        //@ts-ignore
                                                                        ? t(roles.find((role) => role.name === rolePopOverLabel)?.name)
                                                                        //@ts-ignore
                                                                        : user ? t(roles.find((roleFormEdit) => user.roles.find(userRole => roleFormEdit.id === userRole.id))?.label) : t('user_form_roles_pop_over_place_holder')}</span>
                                                                          <span
                                                                              className="opacity-50 material-symbols-outlined">unfold_more</span>
                                                                      </Button>
                                                                  </PopoverTrigger>
                                                                  <PopoverContent className="w-[--radix-popover-trigger-width]">
                                                                      <Command>
                                                                          <CommandInput id="type" placeholder={t('user_form_roles_pop_over_place_holder')} />
                                                                          <CommandList>
                                                                              <Command>{t('user_form_pop_roles_over_not_found')}</Command>
                                                                              <CommandGroup>
                                                                                  {roles.map((role) => (
                                                                                      <CommandItem
                                                                                          key={role.id}
                                                                                          value={role.name}
                                                                                          onSelect={(currentValue) => {
                                                                                              console.log(currentValue)
                                                                                              setRolePopOverLabel(currentValue === rolePopOverLabel ? "" : currentValue);
                                                                                              setOpenRolePopOver(false);
                                                                                              const selectedRoles: RoleModel[] = currentValue === rolePopOverLabel ? [] : [roles.find(value => value.id === role.id) as RoleModel];
                                                                                              // @ts-ignore
                                                                                              form.setValue("roles", selectedRoles, {shouldTouch: true, shouldDirty: true, shouldValidate: true});
                                                                                          }}
                                                                                      >
                                                                                    <span
                                                                                        className={`mr-2 h-4 w-4 material-symbols-outlined ${rolePopOverLabel === role.name ? 'opacity-100' : 'opacity-0'}`}
                                                                                    >check</span>
                                                                                          {t(role.name)}
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
                                      <div className="lg:w-6/12 mb-4">
                                          <div className="flex flex-col space-y-1.5 mt-12">
                                              <FormField
                                                  control={form.control}
                                                  name="useMfa"
                                                  render={({field}) => (
                                                      <FormItem
                                                          className="flex flex-row items-center space-x-2 space-y-0">
                                                          <FormControl>
                                                              <Checkbox id="useMfa" checked={field.value}
                                                                        disabled={mode === FormModeType.READ || isLoading}
                                                                        onCheckedChange={field.onChange}/>
                                                          </FormControl>
                                                          <FormLabel
                                                              className="font-normal">{t('user_form_mfa_label')}</FormLabel>
                                                      </FormItem>
                                                  )}
                                              />
                                          </div>
                                      </div>
                                  </div>
                              </TabsContent>
                              <TabsContent value="status">
                                  <div className="flex flex-col lg:flex-row w-full gap-4">
                                      <div className="lg:w-4/12 mb-4">
                                          <div className="flex flex-col space-y-1.5 mt-16">
                                              <FormField
                                                  control={form.control}
                                                  name="failedLoginAttempt"
                                                  render={({field}) => (
                                                      <FormItem>
                                                          <FormLabel>{t('user_form_failed_login_attempt_label')}</FormLabel>
                                                          <FormControl>
                                                              <Input id="failedLoginAttempt" type="number" {...field}
                                                                     disabled={true}/>
                                                          </FormControl>
                                                          <FormMessage className="text-xs text-destructive"/>
                                                      </FormItem>
                                                  )}
                                              />
                                          </div>
                                      </div>
                                      <div className="lg:w-4/12 mb-4">
                                          <div className="flex flex-col space-y-1.5 mt-16">
                                              <FormField
                                                  control={form.control}
                                                  name="accountEnabled"
                                                  render={({field}) => (
                                                      <FormItem
                                                          className="flex flex-row items-center space-x-2 space-y-0">
                                                          <FormControl>
                                                              <Checkbox id="accountEnabled" checked={field.value}
                                                                        disabled={true}
                                                                        onCheckedChange={field.onChange}/>
                                                          </FormControl>
                                                          <FormLabel
                                                              className="font-normal">{t('user_form_account_enabled_label')}</FormLabel>
                                                      </FormItem>
                                                  )}
                                              />
                                          </div>
                                          <div className="flex flex-col space-y-1.5 mt-16">
                                              <FormField
                                                  control={form.control}
                                                  name="accountLocked"
                                                  render={({field}) => (
                                                      <FormItem
                                                          className="flex flex-row items-center space-x-2 space-y-0">
                                                          <FormControl>
                                                              <Checkbox id="accountLocked" checked={field.value}
                                                                        disabled={true}
                                                                        onCheckedChange={field.onChange}/>
                                                          </FormControl>
                                                          <FormLabel
                                                              className="font-normal">{t('user_form_account_locked_label')}</FormLabel>
                                                      </FormItem>
                                                  )}
                                              />
                                          </div>
                                      </div>
                                      <div className="lg:w-4/12 mb-4">
                                          <div className="flex flex-col space-y-1.5 mt-16">
                                              <FormField
                                                  control={form.control}
                                                  name="accountExpired"
                                                  render={({field}) => (
                                                      <FormItem
                                                          className="flex flex-row items-center space-x-2 space-y-0">
                                                          <FormControl>
                                                              <Checkbox id="accountExpired" checked={field.value}
                                                                        disabled={true}
                                                                        onCheckedChange={field.onChange}/>
                                                          </FormControl>
                                                          <FormLabel
                                                              className="font-normal">{t('user_form_account_expired_label')}</FormLabel>
                                                      </FormItem>
                                                  )}
                                              />
                                          </div>
                                          <div className="flex flex-col space-y-1.5 mt-16">
                                              <FormField
                                                  control={form.control}
                                                  name="credentialExpired"
                                                  render={({field}) => (
                                                      <FormItem
                                                          className="flex flex-row items-center space-x-2 space-y-0">
                                                          <FormControl>
                                                              <Checkbox id="credentialExpired" checked={field.value}
                                                                        disabled={true}
                                                                        onCheckedChange={field.onChange}/>
                                                          </FormControl>
                                                          <FormLabel
                                                              className="font-normal">{t('user_form_credential_expired_label')}</FormLabel>
                                                      </FormItem>
                                                  )}
                                              />
                                          </div>
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

export default UserForm;