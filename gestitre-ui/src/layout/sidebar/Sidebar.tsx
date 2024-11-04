import {useTranslation} from "react-i18next";

const Sidebar = () => {
    const {t} = useTranslation(['home']);
    return (
        <div className="fixed flex flex-col justify-start items-start bg-gradient-to-br from-primary/90 to-primary/85 text-primary-foreground h-screen w-80">
            <div className="w-full">
                <div className="flex flex-col justify-center items-center w-full my-6">
                    <img className="size-28 rounded-full" src="/images/french.png" alt="..."/>
                    <p className="flex flex-row justify-center items-center gap-2 w-full my-4 text-lg cursor-pointer">
                        <span className="material-symbols-outlined">logout</span>
                        <span>{t('sidebar_logout')}</span>
                    </p>
                </div>
                <div className="flex flex-col justify-start items-start w-full">
                    <p className="py-5 border-t-2 w-full border-primary-foreground/40 cursor-pointer hover:bg-gradient-to-r hover:from-primary-foreground/50 hover:to-primary-foreground/50 hover:text-primary hover:transition hover:duration-300 hover:ease-in-out">
                        <p className="flex flex-row justify-start gap-2 pl-3">
                            <span className="material-symbols-outlined">space_dashboard</span>
                            <span>{t('sidebar_menu_information')}</span>
                        </p>
                    </p>
                    <p className="py-5 border-t-2 w-full border-primary-foreground/40 cursor-pointer hover:bg-gradient-to-r hover:from-primary-foreground/50 hover:to-primary-foreground/50 hover:text-primary hover:transition hover:duration-300 hover:ease-in-out">
                        <p className="flex flex-row justify-start gap-2 pl-3">
                            <span className="material-symbols-outlined">groups</span>
                            <span>{t('sidebar_menu_shareholder')}</span>
                        </p>
                    </p>
                    <p className="py-5 border-t-2 w-full border-primary-foreground/40 cursor-pointer hover:bg-gradient-to-r hover:from-primary-foreground/50 hover:to-primary-foreground/50 hover:text-primary hover:transition hover:duration-300 hover:ease-in-out">
                        <p className="flex flex-row justify-start gap-2 pl-3">
                            <span className="material-symbols-outlined">sync_alt</span>
                            <span>{t('sidebar_menu_movement')}</span>
                        </p>
                    </p>
                    <p className="py-5 border-t-2 w-full border-primary-foreground/40 cursor-pointer hover:bg-gradient-to-r hover:from-primary-foreground/50 hover:to-primary-foreground/50 hover:text-primary hover:transition hover:duration-300 hover:ease-in-out">
                        <p className="flex flex-row justify-start gap-2 pl-3">
                            <span className="material-symbols-outlined">account_tree</span>
                            <span>{t('sidebar_menu_account')}</span>
                        </p>
                    </p>
                    <p className="py-5 border-t-2 w-full border-primary-foreground/40 cursor-pointer hover:bg-gradient-to-r hover:from-primary-foreground/50 hover:to-primary-foreground/50 hover:text-primary hover:transition hover:duration-300 hover:ease-in-out">
                        <p className="flex flex-row justify-start gap-2 pl-3">
                            <span className="material-symbols-outlined">history</span>
                            <span>{t('sidebar_menu_history')}</span>
                        </p>
                    </p>
                    <p className="py-5 border-t-2 w-full border-primary-foreground/40 cursor-pointer hover:bg-gradient-to-r hover:from-primary-foreground/50 hover:to-primary-foreground/50 hover:text-primary hover:transition hover:duration-300 hover:ease-in-out">
                        <p className="flex flex-row justify-start gap-2 pl-3">
                            <span className="material-symbols-outlined">source_environment</span>
                            <span>{t('sidebar_menu_company')}</span>
                        </p>
                    </p>
                    <p className="py-5 border-t-2 w-full border-primary-foreground/40 cursor-pointer hover:bg-gradient-to-r hover:from-primary-foreground/50 hover:to-primary-foreground/50 hover:text-primary hover:transition hover:duration-300 hover:ease-in-out">
                        <p className="flex flex-row justify-start gap-2 pl-3">
                            <span className="material-symbols-outlined">history</span>
                            <span>{t('sidebar_menu_user')}</span>
                        </p>
                    </p>
                    <p className="py-5 border-y-2 w-full border-primary-foreground/40 cursor-pointer hover:bg-gradient-to-r hover:from-primary-foreground/50 hover:to-primary-foreground/50 hover:text-primary hover:transition hover:duration-300 hover:ease-in-out">
                        <p className="flex flex-row justify-start gap-2 pl-3">
                            <span className="material-symbols-outlined">export_notes</span>
                            <span>{t('sidebar_menu_about')}</span>
                        </p>
                    </p>
                </div>
            </div>
            <div className="flex flex-col justify-center items-center w-full mt-8">
                <img className="size-28 rounded-full" src="/images/french.png" alt="..."/>
            </div>
        </div>
    );
};

export default Sidebar;