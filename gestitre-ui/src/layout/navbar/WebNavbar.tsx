import {useTranslation} from "react-i18next";
import {useDispatch, useSelector} from "react-redux";
import {RootDispatch} from "@/Store.ts";
import {LanguageModel} from "@/layout/navbar/LanguageModel.ts";
import {changeLanguage, selectLanguage} from "@/layout/navbar/NavbarSlice.ts";
import {useEffect, useRef, useState} from "react";
import i18n from "@/i18n.tsx";
import {getImageUrl} from "@/utils/imageUtils.ts";

const WebNavbar = () => {
    const {t} = useTranslation(['home']);

    const dispatch = useDispatch<RootDispatch>();
    const language: LanguageModel = useSelector(selectLanguage);

    const dropdownRef = useRef<HTMLDivElement>(null);
    const [isOpenLanguageMenu, setIsOpenLanguageMenu] = useState(false);

    useEffect(() => {
        // @ts-expect-error
        const closeDropDown = (event) => {
            if(dropdownRef.current && !dropdownRef.current.contains(event.target)) {
                setIsOpenLanguageMenu(false);
            }
        }
        document.addEventListener("click", closeDropDown);
        return () => document.body.removeEventListener("click", closeDropDown);
    }, []);

    const toggleDropDown = () => {
        setIsOpenLanguageMenu(!isOpenLanguageMenu);
    }

    const handleChangeLanguage = (event: React.MouseEvent<HTMLDivElement>, language: LanguageModel) => {
        //TODO continue to find how to hide sub menu
        event.preventDefault();
        if(isOpenLanguageMenu) {
            i18n.changeLanguage(language.name).then();
            dispatch(changeLanguage(language));
        }
    }

    return (
        <div className="flex flex-row justify-between items-center bg-white shadow-lg py-6 px-8 ml-80">
            <div>{t('navbar_connected_user')}: MBIANDJI Brice</div>
            <div className="relative mr-5">
                <div
                    ref={dropdownRef}
                    onClick={toggleDropDown}
                    className="flex flex-row justify-start items-center text-secondary-500 hover:text-primary cursor-pointer">
                    <span className="font-medium">
                        <img
                            src={`${language.name === 'fr' ? getImageUrl('/images/french.png') : getImageUrl('/images/english.png')}`}
                            alt="..."
                            className="size-6 object-cover rounded-full"/>
                    </span>
                </div>
                <div
                    className={`absolute top-full -right-0 mt-7 w-32 bg-white shadow-2xl z-10 rounded-b-lg ${isOpenLanguageMenu ? 'transition-all duration-300 ease-in opacity-100 transform scale-110 cursor-pointer' : 'cursor-auto transition-all duration-300 ease-out opacity-0 scale-75'}`}>
                    <div onClick={(event) => handleChangeLanguage(event, {name: 'en'})}
                         className="flex flex-row justify-start items-center gap-2 text-secondary-500 p-2 text-sm hover:text-primary-foreground hover:bg-gradient-to-r hover:from-primary hover:to-primary hover:transition-all hover:ease-in-out hover:duration-300 ">
                        <img src={getImageUrl('/images/english.png')} alt="..."
                             className="size-6 object-cover rounded-full"/>
                        <span>English</span>
                    </div>
                    <div onClick={(event) => handleChangeLanguage(event, {name: 'fr'})}
                         className="flex flex-row justify-start items-center gap-2 text-secondary-500 p-2 text-sm  rounded-b-lg hover:text-primary-foreground hover:bg-gradient-to-r hover:from-primary hover:to-primary hover:transition-all hover:ease-in-out hover:duration-300">
                        <img src={getImageUrl('/images/french.png')} alt="..."
                             className="size-6 object-cover rounded-full"/>
                        French
                    </div>
                </div>
            </div>
        </div>
    );
};

export default WebNavbar;