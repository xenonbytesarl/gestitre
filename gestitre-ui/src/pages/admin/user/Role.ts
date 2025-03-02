import {Permission} from "@/pages/admin/user/Permission.ts";

export interface Role {
    id: string;
    name: string;
    permissions?: Array<Permission>;
    active: boolean;
}