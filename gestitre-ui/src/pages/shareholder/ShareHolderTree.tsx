import {useTranslation} from "react-i18next";
import {ShareHolderModel} from "@/pages/shareholder/ShareHolderModel.ts";
import {useDispatch, useSelector} from "react-redux";

import {RootDispatch, RootState} from "@/core/Store.ts";
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
import {searchShareHolders, selectShareHolders} from "@/pages/shareholder/ShareholderSlice.tsx";
import {getPageSize, getTotalElements, getTotalPages} from "@/pages/admin/user/UserSlice.ts";
import {getLoading, getProfileInfo} from "@/pages/admin/auth/AuthSlice.ts";
import {ProfileModel} from "@/pages/admin/user/ProfileModel.ts";
import {findCompanyById, selectCompanyById} from "@/pages/company/CompanySlice.ts";
import {CompanyModel} from "@/pages/company/CompanyModel.ts";
import {format} from "date-fns";

const ShareHolderTree = () => {

    const {t} = useTranslation(['home']);

    const shareholders: Array<ShareHolderModel> = useSelector(selectShareHolders);
    const profile: ProfileModel = useSelector(getProfileInfo);
    const company: CompanyModel = useSelector((state: RootState) => selectCompanyById(state, profile.companyId))
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
        field: "shareholderName",
        direction: Direction.ASC,
        keyword: keyword
    });

    const columns: ColumnDef<ShareHolderModel>[] = [
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
            accessorKey: "codeIsin",
            header: () => (<div className="text-left">{t("shareholder_tree_code_isin")}</div>),
            cell: ({row}) => (
                <div className="text-left capitalize">{row.original.tenantId === profile.tenantId? company?.isinCode: ''}</div>
            )
        },
        {
            accessorKey: "accountNumber",
            header: () => (<div className="text-left">{t("shareholder_tree_account_number")}</div>),
        },
        {
            accessorKey: "name",
            header: () => (<div className="text-left">{t("shareholder_tree_name")}</div>),
        },
        {
            accessorKey: "createdDate",
            header: () => (<div className="text-left">{t("shareholder_tree_created_date")}</div>),
            cell: ({row}) => (
                <div className="text-left capitalize">{format(row.original.createdDate, 'dd/MM/yyyy HH:mm:ss')}</div>
            )
        },
        {
            accessorKey: "initialBalance",
            header: () => (<div className="text-left">{t("shareholder_tree_initial_balance")}</div>)
        },
        {
            accessorKey: "ircmRetain",
            header: () => (<div className="text-left">{t("shareholder_tree_ircm")}</div>),
            cell: ({row}) => (
                <div className="text-left capitalize">{row.original.tenantId === profile.tenantId? company?.ircmRetain: ''}</div>
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

    useEffect(() => {
        if(profile && profile.companyId) {
            dispatch(findCompanyById(profile.companyId));
        }
    }, [dispatch, profile]);

    useEffect(() => {
        dispatch(searchShareHolders({
            page: page,
            size: size,
            field: "name",
            direction: DEFAULT_DIRECTION_VALUE,
            keyword: keyword
        }));
    }, [debounceKeyword, page, size]);


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

    const handleDelete = (rows: Array<ShareHolderModel>) => {
        console.log(rows); //TODO
    }

    const handleEdit = (row: ShareHolderModel) => {
        navigate(`/shareholders/form/details/${row.id}`);
    }

    const handleNew = () => {
        navigate('/shareholders/form/new');
    }

    const handleClear = () => {
        setKeyword('');
    }

    return (
        <div className="text-3xl text-primary min-h-full">
            <DataTable
                title={'shareholder_tree_title'}
                columns={columns} data={shareholders}
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

export default ShareHolderTree;