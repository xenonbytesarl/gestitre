create table if not exists t_mail_template
(
    c_id         uuid                       not null,
    c_created_at timestamp with time zone   not null,
    c_updated_at timestamp with time zone,
    c_name       varchar(64)                not null,
    c_type       varchar(32)                not null,
    c_active     boolean                     not null ,
    constraint Pk_t_mail_template_c_id primary key (c_id),
    constraint Uk_t_mail_template_c_name unique (c_name),
    constraint Uk_t_mail_template_c_type unique (c_type)
);


create table if not exists  t_mail
(
    c_id               uuid                        not null,
    c_created_at       timestamp with time zone not null,
    c_updated_at       timestamp with time zone,
    c_to               varchar(255)                not null,
    c_from             varchar(255)                not null,
    c_cc               varchar(512),
    c_subject          varchar(512)                not null,
    c_message          text,
    c_state            varchar(255)                not null,
    c_type             varchar(255)                not null,
    c_send_at          timestamp with time zone,
    c_attempt_to_send  numeric(38,0),
    c_mail_template_id uuid                        not null,
    c_mail_server_id   uuid                        not null,
    constraint Pk_t_mail_c_id primary key (c_id),
    constraint Fk_t_mail_c_mail_server_id foreign key (c_mail_server_id) references t_mail_server (c_id),
    constraint Fk_t_mail_c_mail_template_id foreign key (c_mail_template_id) references t_mail_template (c_id)
);

create table if not exists t_mail_attribute
(
    c_mail_id         UUID         NOT NULL,
    c_attribute_value varchar(255),
    c_attribute_key   varchar(255) NOT NULL,
    constraint Pk_t_mail_attribute_c_id_c_attribute_key primary key (c_mail_id, c_attribute_key),
    constraint Fk_t_mail_attribute_c_mail_id foreign key (c_mail_id) references t_mail (c_id)
);

-- Permission for mail template
insert into t_permission (c_id, c_created_at, c_updated_at, c_name) values ('01959617-60db-727b-b652-aaba79cbdf2d', current_timestamp, null, 'read:mail_template');
insert into t_permission (c_id, c_created_at, c_updated_at, c_name) values ('01959617-60db-7a72-89ab-076d4d8a51d5', current_timestamp, null, 'update:mail_template');
insert into t_permission (c_id, c_created_at, c_updated_at, c_name) values ('01959617-60db-7c98-9723-070485203f2a', current_timestamp, null, 'create:mail_template');

-- Permission for mail
insert into t_permission (c_id, c_created_at, c_updated_at, c_name) values ('0195961a-2fe7-722f-aba8-877783d337f0', current_timestamp, null, 'read:mail');
insert into t_permission (c_id, c_created_at, c_updated_at, c_name) values ('0195961a-2fe7-7b8f-bbb8-e1fbd461c931', current_timestamp, null, 'update:mail');
insert into t_permission (c_id, c_created_at, c_updated_at, c_name) values ('0195961a-2fe7-7601-b60c-f0dcf8e05cc5', current_timestamp, null, 'create:mail');

-- Permission for role User to read mail template
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-75e4-85a7-8deb40ac98b8', '01959617-60db-727b-b652-aaba79cbdf2d');

-- Permission for role User to create, read, update mail
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-75e4-85a7-8deb40ac98b8', '0195961a-2fe7-722f-aba8-877783d337f0');
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-75e4-85a7-8deb40ac98b8', '0195961a-2fe7-7b8f-bbb8-e1fbd461c931');
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-75e4-85a7-8deb40ac98b8', '0195961a-2fe7-7601-b60c-f0dcf8e05cc5');


-- Permission for role Admin to read mail template
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-73da-95db-d2fa15c9b206', '01959617-60db-727b-b652-aaba79cbdf2d');

-- Permission for role Admin to create, read, update mail
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-73da-95db-d2fa15c9b206', '0195961a-2fe7-722f-aba8-877783d337f0');
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-73da-95db-d2fa15c9b206', '0195961a-2fe7-7b8f-bbb8-e1fbd461c931');
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-73da-95db-d2fa15c9b206', '0195961a-2fe7-7601-b60c-f0dcf8e05cc5');

-- Permission for role System to create, update and read mail template
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-768a-88d6-82335f785612', '01959617-60db-727b-b652-aaba79cbdf2d');
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-768a-88d6-82335f785612', '01959617-60db-7a72-89ab-076d4d8a51d5');
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-768a-88d6-82335f785612', '01959617-60db-7c98-9723-070485203f2a');

-- Permission for role System to create, update and read mail
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-768a-88d6-82335f785612', '0195961a-2fe7-722f-aba8-877783d337f0');
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-768a-88d6-82335f785612', '0195961a-2fe7-7b8f-bbb8-e1fbd461c931');
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-768a-88d6-82335f785612', '0195961a-2fe7-7601-b60c-f0dcf8e05cc5');