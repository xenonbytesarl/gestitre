--t_stock_move
create table if not exists t_stock_move
(
    c_id              uuid                        not null,
    c_tenant_id       uuid,
    c_created_at      timestamp with time zone    not null,
    c_updated_at      timestamp with time zone,
    c_reference       varchar(32)                not null,
    c_company_name    varchar(64)                 not null,
    c_isin_code       varchar(32)                 not null,
    c_nature          varchar(32)                 not null,
    c_type            varchar(32)                 not null,
    c_state           varchar(32)                 not null,
    c_quantity_credit numeric(38,0)               not null,
    c_quantity_debit  numeric(38,0)               not null,
    c_filename        varchar(512)                not null,
    c_observation     varchar(1024)               not null,
    c_created_date    timestamp with time zone    not null,
    c_company_id      uuid                        not null,
    constraint Pk_t_stock_move_c_id primary key (c_id),
    constraint Uk_t_stock_move_c_reference unique (c_reference),
    constraint Fk_t_stock_move_c_company_id foreign key (c_company_id) references t_company (c_id)
);

-- t_stock_move_line
create table if not exists t_stock_move_line
(
    c_id             uuid                        not null,
    c_tenant_id      uuid,
    c_created_at     timestamp with time zone    not null,
    c_updated_at     timestamp with time zone,
    c_reference      varchar(32)                not null,
    c_account_number varchar(64)                 not null,
    c_name           varchar(128)                not null,
    c_quantity       numeric(38,0)               not null,
    c_administrator  varchar(128),
    c_type           varchar(32)                 not null,
    c_state          varchar(32)                 not null,
    c_city           varchar(64),
    c_created_date   timestamp with time zone    not null,
    c_zip_code       varchar(64),
    c_stock_move_id  uuid                        not null,
    c_shareholder_id uuid                        not null,
    constraint Pk_t_stock_move_line_c_id primary key (c_id),
    constraint Uk_t_stock_move_line_c_reference unique (c_reference),
    constraint Fk_T_stock_move_line_c_shareholder_id  foreign key (c_shareholder_id) references t_shareholder (c_id),
    constraint Fk_T_stock_move_line_c_stock_move_id foreign key (c_stock_move_id)  references t_stock_move (c_id)
);
