-- Create table t_verification
create table if not exists t_verification
(
    c_id         uuid                        not null,
    c_created_at timestamp without time zone not null,
    c_updated_at timestamp without time zone,
    c_type       varchar(32)                not null,
    c_code       varchar(128),
    c_url        varchar(512),
    c_email      varchar(128) not null,
    c_creation_at timestamp without time zone not null,
    c_expired_at timestamp without time zone,
    c_status     varchar(32)                 not null,
    c_user_id    uuid                        not null,
    constraint Pk_t_verification_cid primary key (c_id),
    constraint Fk_t_verification_c_user_id foreign key (c_user_id) references t_user (c_id)
);