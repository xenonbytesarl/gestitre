import {useTranslation} from "react-i18next";
import {CompanyModel} from "@/pages/company/CompanyModel.ts";
import {useDispatch, useSelector} from "react-redux";

import {RootDispatch} from "@/core/Store.ts";
import {
    getLoading,
    getPageSize,
    getTotalElements,
    getTotalPages,
    searchUsers,
    selectUsers
} from "@/pages/admin/user/UserSlice.ts";
import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import {DEFAULT_DIRECTION_VALUE, DEFAULT_PAGE_VALUE} from "@/shared/constant/page.constant.ts";
import useDebounce from "@/hooks/useDebounce.tsx";
import {DEBOUNCE_TIMEOUT} from "@/shared/constant/globalConstant.ts";
import {SearchParamModel} from "@/shared/model/searchParamModel.ts";
import {Direction} from "@/shared/constant/directionConstant.ts";
import {ColumnDef} from "@tanstack/react-table";
import {Checkbox} from "@/components/ui/checkbox.tsx";
import DataTable from "@/components/DataTable.tsx";
import {UserModel} from "@/pages/admin/user/UserModel.ts";
import {searchCompanies, selectCompanies} from "@/pages/company/CompanySlice.ts";


const UserTree = () => {

    const {t} = useTranslation(['home']);

    const users: Array<UserModel> = useSelector(selectUsers);
    const companies: Array<CompanyModel> = useSelector(selectCompanies);
    const pageSize: number = useSelector(getPageSize);
    const totalElements = useSelector(getTotalElements);
    const totalPages = useSelector(getTotalPages);
    const isLoading = useSelector(getLoading);
    const dispatch = useDispatch<RootDispatch>();

    const navigate = useNavigate();

    const [page, setPage] = useState<number>(DEFAULT_PAGE_VALUE);
    const [size, setSize] = useState<number>(pageSize);
    const [keyword, setKeyword] = useState<string>('');
    const debounceKeyword = useDebounce(keyword, DEBOUNCE_TIMEOUT );

    const [, setSearchParam] = useState<SearchParamModel>({
        page: DEFAULT_PAGE_VALUE,
        size: pageSize,
        field: "name",
        direction: Direction.ASC,
        keyword: keyword
    });

    const columns: ColumnDef<UserModel>[] = [
        {
            id: "select",
            header: ({ table }) => (
                <Checkbox
                    checked={
                        table.getIsAllPageRowsSelected() ||
                        (table.getIsSomePageRowsSelected() && "indeterminate")
                    }
                    onCheckedChange={(value) => table.toggleAllPageRowsSelected(!!value)}
                    aria-label="Select all"
                />
            ),
            cell: ({ row }) => (
                <Checkbox
                    checked={row.getIsSelected()}
                    onCheckedChange={(value) => row.toggleSelected(!!value)}
                    aria-label="Select row"
                />
            ),
            enableSorting: false,
            enableHiding: false,
        },
        {
            accessorKey: "name",
            header: () => (<div className="text-left">{t("user_tree_name")}</div>),
        },
        {
            accessorKey: "email",
            header: () => (<div className="text-left">{t("user_tree_email")}</div>),
        },
        {
            accessorKey: "companyId",
            header: () => (<div className="text-left">{t("user_tree_company_id")}</div>),
            cell: ({row}) => (
                <div className="text-left capitalize">{companies.find(company => company.id === row.original.companyId)?.companyName}</div>
            )
        },
        {
            accessorKey: "roles",
            header: () => (<div className="text-left">{t("user_tree_roles")}</div>),
            cell: ({row}) => (
                <div className="text-left capitalize">{row.original?.roles[0].name}</div>
            )
        },
        {
            accessorKey: "action",
            header: () => "",
            cell: ({row}) => (
                <div  className="flex flex-row justify-end items-center gap-4 text-end ">
                    <span  onClick={() => handleEdit(row.original)} className="material-symbols-outlined text-primary text-xl cursor-pointer">edit</span>
                    <span  onClick={() => handleEdit(row.original)} className="material-symbols-outlined text-destructive text-xl cursor-pointer">delete</span>
                </div>
            )
        }
    ];

    const handlePageChange = (page: number) => {
        setSearchParam({page: page, size: size, field: "name", direction: DEFAULT_DIRECTION_VALUE, keyword: keyword});
        //TODO add page in pageInfo in backend and manage page in store
        setPage(page);
    }

    const handleSizeChange = (size: number) => {
        setSearchParam({page: DEFAULT_PAGE_VALUE, size: size, field: "name", direction: DEFAULT_DIRECTION_VALUE, keyword: keyword});
        //TODO add page in pageInfo in backend and manage page in store
        setSize(size);
        setPage(DEFAULT_PAGE_VALUE);
    }

    const handleFilterChange = (keyword: string) => {
        setKeyword(keyword);
        setPage(DEFAULT_PAGE_VALUE);
    }

    const handleDelete = (rows: Array<UserModel>) => {
        console.log(rows); //TODO
    }

    const handleEdit = (row: UserModel) => {
        navigate(`/admin/users/form/details/${row.id}`);
    }

    const handleNew = () => {
        navigate('/admin/users/form/new');
    }

    const handleClear = () => {
        setKeyword('');
    }

    useEffect(() => {
        dispatch(searchCompanies({
            page: page,
            size: size,
            field: "companyName",
            direction: DEFAULT_DIRECTION_VALUE,
            keyword: keyword
        }));
    }, []);

    useEffect(() => {
        dispatch(searchUsers({
            page: page,
            size: size,
            field: "name",
            direction: DEFAULT_DIRECTION_VALUE,
            keyword: keyword
        }));
    }, [debounceKeyword, page, size]);


    return (
        <div className="text-3xl text-primary min-h-full">
            <DataTable
                title={'user_tree_title'}
                columns={columns} data={users}
                totalElements={totalElements}
                page={page}
                size={size}
                totalPages={totalPages}
                isLoading={isLoading}
                keyword={keyword}
                handlePageChange={handlePageChange}
                handleNew={handleNew}
                handleFilterChange={handleFilterChange}
                handleSizeChange={handleSizeChange}
                handleClear={handleClear}
                handleDelete={handleDelete}
            />
        </div>
    );
};

export default UserTree;