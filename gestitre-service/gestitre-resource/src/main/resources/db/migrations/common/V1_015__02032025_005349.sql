-- Permissions for company
insert into t_permission (c_id, c_created_at, c_updated_at, c_name) values ('0195542a-197b-75ec-93dc-a2af07e7b961', current_timestamp, null, 'search:certificate:template');
insert into t_permission (c_id, c_created_at, c_updated_at, c_name) values ('0195542a-197b-7e8c-9058-5a584e624d63', current_timestamp, null, 'search:company');
-- Permissions for the role System
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-768a-88d6-82335f785612', '0195542a-197b-75ec-93dc-a2af07e7b961');
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-768a-88d6-82335f785612', '0195542a-197b-7e8c-9058-5a584e624d63');