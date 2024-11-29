-- Permissions for the mail server
insert into t_permission (c_id, c_created_at, c_updated_at, c_name) values ('019388a6-dbab-732b-b07e-3732dc877200', current_timestamp, null, 'create:mail:server');
insert into t_permission (c_id, c_created_at, c_updated_at, c_name) values ('019388a7-7b20-7177-ba71-4657237a214a', current_timestamp, null, 'update:mail:server');
insert into t_permission (c_id, c_created_at, c_updated_at, c_name) values ('019388a7-a906-73c7-afae-cea8d3c61103', current_timestamp, null, 'read:mail:server');
-- Permissions for the role System
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-768a-88d6-82335f785612', '019388a6-dbab-732b-b07e-3732dc877200');
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-768a-88d6-82335f785612', '019388a7-7b20-7177-ba71-4657237a214a');
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-768a-88d6-82335f785612', '019388a7-a906-73c7-afae-cea8d3c61103');