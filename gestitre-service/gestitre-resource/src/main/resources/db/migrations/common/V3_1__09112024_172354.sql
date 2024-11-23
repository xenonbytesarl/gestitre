create table if not exists t_tenant
(
    c_id         uuid        not null ,
    c_created_at timestamp with time zone not null ,
    c_updated_at timestamp with time zone,
    c_name       varchar(64) not null ,
    c_active     boolean     not null ,
    constraint Pk_t_tenant_c_id primary key (c_id),
    constraint Uk_t_tenant_c_name unique (c_name)
    );