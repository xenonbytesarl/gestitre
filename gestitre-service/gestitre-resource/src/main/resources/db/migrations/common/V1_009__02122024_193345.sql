-- Permissions for the mail server
insert into t_permission (c_id, c_created_at, c_updated_at, c_name) values ('019388a6-dbab-732b-b07e-3732dc877200', current_timestamp, null, 'create:mail_server');
insert into t_permission (c_id, c_created_at, c_updated_at, c_name) values ('019388a7-7b20-7177-ba71-4657237a214a', current_timestamp, null, 'update:mail_server');
insert into t_permission (c_id, c_created_at, c_updated_at, c_name) values ('019388a7-a906-73c7-afae-cea8d3c61103', current_timestamp, null, 'read:mail_server');

-- Permission for role User to read mail server
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-75e4-85a7-8deb40ac98b8', '019388a7-a906-73c7-afae-cea8d3c61103');

-- Permission for role Admin to read mail server
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-73da-95db-d2fa15c9b206', '019388a7-a906-73c7-afae-cea8d3c61103');


-- Permissions for the role System to create update and read mail server
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-768a-88d6-82335f785612', '019388a6-dbab-732b-b07e-3732dc877200');
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-768a-88d6-82335f785612', '019388a7-7b20-7177-ba71-4657237a214a');
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-768a-88d6-82335f785612', '019388a7-a906-73c7-afae-cea8d3c61103');