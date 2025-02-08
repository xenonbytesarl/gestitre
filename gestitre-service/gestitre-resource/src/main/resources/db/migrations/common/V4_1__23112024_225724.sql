-- Create table t_permission
create table if not exists t_permission
(
    c_id         uuid                        not null ,
    c_created_at timestamp with time zone not null ,
    c_updated_at timestamp with time zone,
    c_name       varchar(64)                 not null ,
    constraint Pk_t_permission_c_id primary key (c_id),
    constraint Uk_t_permission_c_name unique (c_name)
);

-- Create table t_role
create table if not exists t_role
(
    c_id         uuid                        not null ,
    c_created_at timestamp with time zone not null ,
    c_updated_at timestamp with time zone,
    c_name       varchar(64)                 not null ,
    c_active     boolean                     not null ,
    constraint Pk_t_role_c_id primary key (c_id),
    constraint Uk_t_role_c_name unique (c_name)
);

-- Create table t_role_permission
create table if not exists t_role_permission
(
    c_permission_id uuid not null ,
    c_role_id       uuid not null ,
    constraint Pk_t_role_permission_c_role_id_c_permission_id primary key (c_permission_id, c_role_id),
    constraint Fk_t_role_permission_c_role_id foreign key (c_role_id) references t_role (c_id),
    constraint Fk_t_role_permission_c_permission_id foreign key (c_permission_id) references t_permission (c_id)
);

-- Create table t_user
create table if not exists t_user
(
    c_id                   uuid                        not null ,
    c_created_at           timestamp with time zone not null ,
    c_updated_at           timestamp with time zone,
    c_name                 varchar(128)                not null ,
    c_email                varchar(128)                not null ,
    c_password             varchar(1024)               not null ,
    c_account_enabled      boolean                     not null ,
    c_credential_expired   boolean                     not null ,
    c_account_locked       boolean                     not null ,
    c_account_expired      boolean                     not null ,
    c_use_mfa              boolean                     not null ,
    c_failed_login_attempt bigint                      not null ,
    c_tenant_id              uuid                      not null ,
    c_company_id             uuid                      not null ,
    constraint Pk_t_user_c_id primary key (c_id),
    constraint Uk_t_user_c_email UNIQUE (c_email),
    constraint Fk_t_tenant_c_tenant_id foreign key (c_tenant_id) references t_tenant (c_id),
    constraint Fk_t_company_c_company_id foreign key (c_company_id) references t_company (c_id)
);

-- Create table t_user_role
create table if not exists t_user_role
(
    c_user_id       uuid not null ,
    c_role_id       uuid not null ,
    constraint Pk_t_user_role_c_user_id_c_role_id primary key (c_user_id, c_role_id),
    constraint Fk_t_user_role_c_user_id foreign key (c_user_id) references t_user (c_id),
    constraint Fk_t_user_role_c_role_id foreign key (c_role_id) references t_role (c_id)
);