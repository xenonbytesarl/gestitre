-- Create table t_mail_server
create table if not exists t_mail_server
(
    c_id           uuid                        not null ,
    c_created_at   timestamp without time zone not null ,
    c_updated_at   timestamp without time zone,
    c_name         varchar(64)                 not null ,
    c_from         varchar(512)                not null ,
    c_type         varchar                     not null ,
    c_state        varchar                     not null ,
    c_host         varchar(64)                 not null ,
    c_port         INTEGER                     not null ,
    c_protocol     varchar(64)                 not null ,
    c_username     varchar(128) ,
    c_password     varchar(64) ,
    c_creation_at  timestamp without time zone not null ,
    c_confirmed_at timestamp without time zone,
    c_use_ssl      boolean,
    c_use_auth     boolean,
    c_active       boolean,
    constraint Pk_t_mail_server primary key (c_id),
    constraint Uk_t_mail_server_c_name unique (c_name)
);

-- Alter table t_verification
alter table t_verification add column c_mail_server_id uuid;
alter table t_verification add constraint Fk_t_verification_c_mail_server_id foreign key (c_mail_server_id) references t_mail_server (c_id);
alter table t_verification alter column c_user_id drop not null;


