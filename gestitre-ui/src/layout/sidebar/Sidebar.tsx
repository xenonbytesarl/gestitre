import {useTranslation} from "react-i18next";
import {useLocation, useNavigate} from "react-router-dom";
import {SidebarMenuModel} from "@/layout/sidebar/SidebarMenuModel.ts";
import {useDispatch, useSelector} from "react-redux";
import {selectSidebarMenu, selectSidebarMenus} from "@/layout/sidebar/SidebarSlice.tsx";
import {RootDispatch} from "@/core/Store.ts";
import {useEffect} from "react";
import SidebarMenu from "@/layout/sidebar/SidebarMenu.tsx";
import {logout} from "@/pages/admin/auth/AuthSlice.ts";

const Sidebar = () => {

    const location = useLocation();
    const navigate = useNavigate();

    const sidebarMenus: SidebarMenuModel[] = useSelector(selectSidebarMenus);

    const dispatch = useDispatch<RootDispatch>();

    useEffect(() => {
        dispatch(selectSidebarMenu("/" + location.pathname.split("/")[1]));
    }, [location])

    const handleLogout = () => {
        dispatch(logout());
        navigate('/admin/auth/login');
    }



    const {t} = useTranslation(['home']);
    return (
        <div className="fixed flex flex-col justify-start items-start bg-gradient-to-br from-primary/90 to-primary/85 text-primary-foreground h-screen w-80">
            <div className="w-full">
                <div className="flex flex-col justify-center items-center w-full my-6">
                    <img className="size-28 rounded-full" src="/images/french.png" alt="..."/>
                    <p onClick={handleLogout} className="flex flex-row justify-center items-center gap-2 w-full my-4 text-lg cursor-pointer">
                        <span  className="material-symbols-outlined">logout</span>
                        <span>{t('sidebar_logout')}</span>
                    </p>
                </div>
                <div className="flex flex-col justify-start items-start w-full">
                    {
                        sidebarMenus.map((sidebarMenu: SidebarMenuModel) => (
                            <SidebarMenu key={sidebarMenu.label} sidebarMenu={sidebarMenu}/>
                        ))
                    }
                </div>
                </div>
                <div className="flex flex-col justify-center items-center w-full mt-8">
                    <img className="size-28 rounded-full" src="/images/french.png" alt="..."/>
                </div>
            </div>
            );
            };

            export default Sidebar;