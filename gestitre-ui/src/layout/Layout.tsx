import {Outlet} from "react-router-dom";
import Sidebar from "@/layout/sidebar/Sidebar.tsx";
import WebNavbar from "@/layout/navbar/WebNavbar.tsx";
import MobileNavbar from "@/layout/navbar/MobileNavbar.tsx";


const Layout = () => {
    return (
        <div className="flex flex-row min-h-screen overflow-x-hidden">
            <div className="hidden md:block">
                <div className="flex flex-row w-screen">
                    <div className="hidden md:block">
                        <Sidebar/>
                    </div>
                    <div className="flex flex-col w-full">
                        <div className="hidden md:block fixed w-full z-10">
                            <WebNavbar/>
                        </div>
                        <div className="md:hidden">
                            <MobileNavbar/>
                        </div>
                        <div className="min-h-full flex-1 mt-24 ml-80">
                            <div className="min-h-full mx-12">
                                <Outlet/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Layout;