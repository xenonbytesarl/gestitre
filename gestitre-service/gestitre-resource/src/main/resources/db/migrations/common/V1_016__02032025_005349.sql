-- Permissions search for user, role, shareholder and stock:move
insert into t_permission (c_id, c_created_at, c_updated_at, c_name) values ('01955687-b761-7980-8430-7e55ae3518d8', current_timestamp, null, 'search:user');
insert into t_permission (c_id, c_created_at, c_updated_at, c_name) values ('01955687-b761-779b-b7e3-e266e7e886a0', current_timestamp, null, 'search:role');
insert into t_permission (c_id, c_created_at, c_updated_at, c_name) values ('019558b6-4374-73ef-886d-d9d3774ecbb8', current_timestamp, null, 'search:shareholder');
insert into t_permission (c_id, c_created_at, c_updated_at, c_name) values ('019558b6-4374-7db9-bec1-9864baa94fa3', current_timestamp, null, 'search:stock:move');
-- Permissions for the role System
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-768a-88d6-82335f785612', '01955687-b761-7980-8430-7e55ae3518d8');
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-768a-88d6-82335f785612', '01955687-b761-779b-b7e3-e266e7e886a0');

-- Permissions for the role Manager
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-73da-95db-d2fa15c9b206', '019558b6-4374-73ef-886d-d9d3774ecbb8');
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-73da-95db-d2fa15c9b206', '019558b6-4374-7db9-bec1-9864baa94fa3');

-- Permissions for the role User
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-75e4-85a7-8deb40ac98b8', '019558b6-4374-73ef-886d-d9d3774ecbb8');
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-75e4-85a7-8deb40ac98b8', '019558b6-4374-7db9-bec1-9864baa94fa3');