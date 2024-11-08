import {SidebarMenuModel} from "@/layout/sidebar/SidebarMenuModel.ts";
import {useTranslation} from "react-i18next";
import {useSelector} from "react-redux";
import {selectSelectedSidebarMenu} from "@/layout/sidebar/SidebarSlice.tsx";
import {useNavigate} from "react-router-dom";

type Props = {
    sidebarMenu: SidebarMenuModel
}

const SidebarMenu = ({sidebarMenu}: Props) => {

    const {t} = useTranslation(['home']);

    const selectedSidebarMenu: SidebarMenuModel | undefined = useSelector(selectSelectedSidebarMenu);

    const navigate = useNavigate();

    const handleSelectSidebarMenu = (_event: React.MouseEvent<HTMLDivElement>, sidebarMenu: SidebarMenuModel) => {
        _event.preventDefault();
        navigate(sidebarMenu.link);
    }

    return (
        <div
            onClick={(event) => handleSelectSidebarMenu(event, sidebarMenu)}
            className={`
                py-5 border-t-2 w-full border-primary-foreground/40 cursor-pointer 
                hover:bg-gradient-to-r hover:from-primary-foreground/50 hover:to-primary-foreground/50 hover:text-primary 
                hover:transition hover:duration-300 hover:ease-in-out ${selectedSidebarMenu?.link === sidebarMenu.link? 
                'bg-gradient-to-r from-primary-foreground/50 to-primary-foreground/50  text-primary transition ' +
                'duration-300 hover:ease-in-out': 'text-primary-foreground'}`
            }
        >
            <p className="flex flex-row justify-start gap-2 pl-3">
                <span className="material-symbols-outlined">{sidebarMenu.icon}</span>
                <span>{t(sidebarMenu.label)}</span>
            </p>
        </div>
    );
};

export default SidebarMenu;