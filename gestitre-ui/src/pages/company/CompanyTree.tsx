import {
    getLoading,
    getPageSize,
    getTotalElements,
    getTotalPages, searchCompanies,
    selectCompanies
} from "@/pages/company/CompanySlice.ts";
import {CompanyModel} from "@/pages/company/CompanyModel.ts";
import {useDispatch, useSelector} from "react-redux";
import {RootDispatch} from "@/Store.ts";
import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import {DEFAULT_DIRECTION_VALUE, DEFAULT_PAGE_VALUE} from "@/shared/constant/page.constant.ts";
import {DEBOUNCE_TIMEOUT} from "@/shared/constant/globalConstant.ts";
import useDebounce from "@/hooks/useDebounce.tsx";
import {SearchParamModel} from "@/shared/model/searchParamModel.ts";
import {Direction} from "@/shared/constant/directionConstant.ts";
import {useTranslation} from "react-i18next";
import {ColumnDef} from "@tanstack/react-table";
import {Checkbox} from "@/components/ui/checkbox.tsx";
import DataTable from "@/components/DataTable.tsx";


const CompanyTree = () => {

    const {t} = useTranslation(['home']);

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
        field: "companyName",
        direction: Direction.ASC,
        keyword: keyword
    });

    const columns: ColumnDef<CompanyModel>[] = [
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
            accessorKey: "companyName",
            header: () => (<div className="text-left">{t("company_tree_company_name")}</div>),
        },
        {
            accessorKey: "companyManagerName",
            header: () => (<div className="text-left">{t("company_tree_company_manager_name")}</div>),
        },
        {
            accessorKey: "legalForm",
            header: () => (<div className="text-left">{t("company_tree_legal_form")}</div>),
            cell: ({row}) => (
                <div className="text-left capitalize">{row.original.legalForm}</div>
            )
        },
        {
            accessorKey: "isinCode",
            header: () => (<div className="text-left">{t("company_tree_isin_code")}</div>),
            cell: ({row}) => (
                <div className="text-left capitalize">{row.original?.isinCode}</div>
            )
        },
        {
            accessorKey: "street",
            header: () => (<div className="text-left">{t("company_tree_address")}</div>),
            cell: ({row}) => (
                <div className="text-left capitalize">BP: {row.original.address.zipCode} - {row.original.address.city}</div>
            )
        },
        {
            accessorKey: "createdDate",
            header: () => (<div className="text-left">{t("company_tree_created_date")}</div>),
        },
        {
            accessorKey: "endLicenceDate",
            header: () => (<div className="text-left">{t("company_tree_licence")}</div>),
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
        dispatch(searchCompanies({
            page: page,
            size: size,
            field: "companyName",
            direction: DEFAULT_DIRECTION_VALUE,
            keyword: keyword
        }));
    }, [debounceKeyword, page, size]);


    const handlePageChange = (page: number) => {
        setSearchParam({page: page, size: size, field: "companyName", direction: DEFAULT_DIRECTION_VALUE, keyword: keyword});
        //TODO add page in pageInfo in backend and manage page in store
        setPage(page);
    }

    const handleSizeChange = (size: number) => {
        setSearchParam({page: DEFAULT_PAGE_VALUE, size: size, field: "companyName", direction: DEFAULT_DIRECTION_VALUE, keyword: keyword});
        //TODO add page in pageInfo in backend and manage page in store
        setSize(size);
        setPage(DEFAULT_PAGE_VALUE);
    }

    const handleFilterChange = (keyword: string) => {
        setKeyword(keyword);
        setPage(DEFAULT_PAGE_VALUE);
    }

    const handleDelete = (rows: Array<CompanyModel>) => {
        console.log(rows); //TODO
    }

    const handleEdit = (row: CompanyModel) => {
        navigate(`/companies/form/details/${row.id}`);
    }

    const handleNew = () => {
        navigate('/companies/form/new');
    }

    const handleClear = () => {
        setKeyword('');
    }

    return (
        <div className="text-3xl text-primary min-h-full">
            <DataTable
                title={'company_tree_title'}
                columns={columns} data={companies}
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

export default CompanyTree;