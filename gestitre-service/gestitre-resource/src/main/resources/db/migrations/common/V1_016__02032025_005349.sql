-- Permissions for company
insert into t_permission (c_id, c_created_at, c_updated_at, c_name) values ('01955687-b761-7980-8430-7e55ae3518d8', current_timestamp, null, 'search:user');
insert into t_permission (c_id, c_created_at, c_updated_at, c_name) values ('01955687-b761-779b-b7e3-e266e7e886a0', current_timestamp, null, 'search:role');
-- Permissions for the role System
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-768a-88d6-82335f785612', '01955687-b761-7980-8430-7e55ae3518d8');
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-768a-88d6-82335f785612', '01955687-b761-779b-b7e3-e266e7e886a0');