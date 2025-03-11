import {useTranslation} from "react-i18next";
import {useDispatch, useSelector} from "react-redux";
import {getLoading} from "@/pages/admin/auth/AuthSlice.ts";
import {RootDispatch} from "@/core/Store.ts";
import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import {DEFAULT_PAGE_VALUE} from "@/shared/constant/page.constant.ts";
import useDebounce from "@/hooks/useDebounce.tsx";
import {DEBOUNCE_TIMEOUT} from "@/shared/constant/globalConstant.ts";
import {StockMoveModel} from "@/pages/stockmove/StockMoveModel.ts";
import {
    getPageSize,
    getTotalElements,
    getTotalPages,
    searchStockMoves,
    selectStockMoves
} from "@/pages/stockmove/StockMoveSlice.ts";
import {SearchParamModel} from "@/shared/model/searchParamModel.ts";
import {Direction} from "@/shared/constant/directionConstant.ts";
import {ColumnDef} from "@tanstack/react-table";
import {Checkbox} from "@/components/ui/checkbox.tsx";
import {format} from "date-fns";
import DataTable from "@/components/DataTable.tsx";

const StockMoveTree = () => {

    const {t} = useTranslation(['home']);

    const stockMoves: Array<StockMoveModel> = useSelector(selectStockMoves);
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
        field: "createdDate",
        direction: Direction.DESC,
        keyword: keyword
    });

    const columns: ColumnDef<StockMoveModel>[] = [
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
            header: () => (<div className="text-left">{t("stock_move_tree_code_isin")}</div>)
        },
        {
            accessorKey: "reference",
            header: () => (<div className="text-left">{t("stock_move_tree_reference")}</div>),
        },
        {
            accessorKey: "createdDate",
            header: () => (<div className="text-left">{t("stock_move_tree_created_date")}</div>),
            cell: ({row}) => (
                <div className="text-left">{row.original.createdDate === undefined? '': format(row.original.createdDate, 'dd/MM/yyyy')}</div>
            )
        },
        {
            accessorKey: "accountingDate",
            header: () => (<div className="text-left">{t("stock_move_tree_accounting_date")}</div>),
            cell: ({row}) => (
                <div className="text-left">{row.original.accountingDate === undefined? '': format(row.original.accountingDate, 'dd/MM/yyyy')}</div>
            )
        },
        {
            accessorKey: "type",
            header: () => (<div className="text-left">{t("stock_move_tree_type")}</div>),
            cell: ({row}) => (
                <div className="text-left capitalize">{row.original.type}</div>
            )
        },
        {
            accessorKey: "nature",
            header: () => (<div className="text-left">{t("stock_move_tree_nature")}</div>),
            cell: ({row}) => (
                <div className="text-left capitalize">{row.original.nature}</div>
            )
        },
        {
            accessorKey: "quantityCredit",
            header: () => (<div className="text-left">{t("stock_move_tree_quantity_credit")}</div>),
            cell: ({row}) => (
                <div className="text-right">{row.original.quantityCredit}</div>
            )
        },
        {
            accessorKey: "state",
            header: () => (<div className="text-left">{t("stock_move_tree_state")}</div>),
            cell: ({row}) => (
                <div className="text-left capitalize">{row.original.state}</div>
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
        dispatch(searchStockMoves({
            page: page,
            size: size,
            field: "createdDate",
            direction: Direction.DESC,
            keyword: keyword
        }));
    }, [debounceKeyword, page, size]);

    const handlePageChange = (page: number) => {
        setSearchParam({page: page, size: size, field: "createdDate", direction: Direction.DESC, keyword: keyword});
        //TODO add page in pageInfo in backend and manage page in store
        setPage(page);
    }

    const handleSizeChange = (size: number) => {
        setSearchParam({page: DEFAULT_PAGE_VALUE, size: size, field: "createdDate", direction: Direction.DESC, keyword: keyword});
        //TODO add page in pageInfo in backend and manage page in store
        setSize(size);
        setPage(DEFAULT_PAGE_VALUE);
    }

    const handleFilterChange = (keyword: string) => {
        setKeyword(keyword);
        setPage(DEFAULT_PAGE_VALUE);
    }

    const handleDelete = (rows: Array<StockMoveModel>) => {
        console.log(rows); //TODO
    }

    const handleEdit = (row: StockMoveModel) => {
        navigate(`/stock-moves/form/details/${row.id}`);
    }

    const handleNew = () => {
        navigate('/stock-moves/form/new');
    }

    const handleClear = () => {
        setKeyword('');
    }

    return (
        <div className="text-3xl text-primary min-h-full">
            <DataTable
                title={'stock_move_tree_title'}
                columns={columns} data={stockMoves}
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

export default StockMoveTree;