import {SidebarMenuModel} from "@/layout/sidebar/SidebarMenuModel.ts";

export const sidebarMenus: SidebarMenuModel[] = [
    {
        label: 'sidebar_menu_information',
        icon: 'space_dashboard',
        link: '/dashboard',
    },
    {
        label: 'sidebar_menu_shareholder',
        icon: 'groups',
        link: '/shareholders',
    },
    {
        label: 'sidebar_menu_movement',
        icon: 'sync_alt',
        link: '/stock-moves',
    },
    {
        label: 'sidebar_menu_account',
        icon: 'account_tree',
        link: '/accounts',
    },
    {
        label: 'sidebar_menu_history',
        icon: 'history',
        link: '/histories',
    },
    {
        label: 'sidebar_menu_company',
        icon: 'source_environment',
        link: '/companies',
    },
    {
        label: 'sidebar_menu_user',
        icon: 'supervisor_account',
        link: '/admin/users',
    },
    {
        label: 'sidebar_menu_about',
        icon: 'export_notes',
        link: '/about',
    }
];