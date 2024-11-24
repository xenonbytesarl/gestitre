-- Permission
insert into t_permission (c_id, c_created_at, c_updated_at, c_name) values ('01935bd8-b568-7b70-bf61-cbbbdc707687', current_timestamp, null, 'create:certificate:template');
insert into t_permission (c_id, c_created_at, c_updated_at, c_name) values ('01935bd8-d4d3-76a6-8bec-7edf01ea264d', current_timestamp, null, 'update:certificate:template');
insert into t_permission (c_id, c_created_at, c_updated_at, c_name) values ('01935bd9-2c9f-7be6-8800-e0e332a9fe34', current_timestamp, null, 'read:certificate:template');
insert into t_permission (c_id, c_created_at, c_updated_at, c_name) values ('01935bd9-2c9f-72a8-840b-ae72e943c715', current_timestamp, null, 'create:company');
insert into t_permission (c_id, c_created_at, c_updated_at, c_name) values ('01935bd9-2c9f-7951-bfc5-3c02921ac515', current_timestamp, null, 'update:company');
insert into t_permission (c_id, c_created_at, c_updated_at, c_name) values ('01935bd9-2c9f-7147-8f3e-97dbf32b3a08', current_timestamp, null, 'read:company');
insert into t_permission (c_id, c_created_at, c_updated_at, c_name) values ('01935bd9-2c9f-7c1c-b2ea-5ab839d2d87c', current_timestamp, null, 'create:user');
insert into t_permission (c_id, c_created_at, c_updated_at, c_name) values ('01935bd9-2c9f-79ae-a784-6b175f97189f', current_timestamp, null, 'update:user');
insert into t_permission (c_id, c_created_at, c_updated_at, c_name) values ('01935bd9-2c9f-75e6-9eef-85958f3c9edc', current_timestamp, null, 'read:user');

-- Roles
insert into t_role (c_id, c_created_at, c_updated_at, c_name, c_active) values ('01935bdb-8ca1-75e4-85a7-8deb40ac98b8', current_timestamp, null, 'User', true);
insert into t_role (c_id, c_created_at, c_updated_at, c_name, c_active) values ('01935bdb-8ca1-73da-95db-d2fa15c9b206', current_timestamp, null, 'Admin', true);
insert into t_role (c_id, c_created_at, c_updated_at, c_name, c_active) values ('01935bdb-8ca1-768a-88d6-82335f785612', current_timestamp, null, 'System', true);

-- Permissions for the role User
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-75e4-85a7-8deb40ac98b8', '01935bd9-2c9f-7be6-8800-e0e332a9fe34');
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-75e4-85a7-8deb40ac98b8', '01935bd9-2c9f-7147-8f3e-97dbf32b3a08');
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-75e4-85a7-8deb40ac98b8', '01935bd9-2c9f-75e6-9eef-85958f3c9edc');

-- Permissions for the role Admin
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-73da-95db-d2fa15c9b206', '01935bd9-2c9f-7be6-8800-e0e332a9fe34');
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-73da-95db-d2fa15c9b206', '01935bd9-2c9f-7147-8f3e-97dbf32b3a08');
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-73da-95db-d2fa15c9b206', '01935bd9-2c9f-75e6-9eef-85958f3c9edc');

-- Permissions for the role System
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-768a-88d6-82335f785612', '01935bd9-2c9f-7be6-8800-e0e332a9fe34');
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-768a-88d6-82335f785612', '01935bd9-2c9f-7147-8f3e-97dbf32b3a08');
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-768a-88d6-82335f785612', '01935bd9-2c9f-75e6-9eef-85958f3c9edc');
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-768a-88d6-82335f785612', '01935bd8-d4d3-76a6-8bec-7edf01ea264d');
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-768a-88d6-82335f785612', '01935bd9-2c9f-7951-bfc5-3c02921ac515');
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-768a-88d6-82335f785612', '01935bd9-2c9f-79ae-a784-6b175f97189f');
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-768a-88d6-82335f785612', '01935bd8-b568-7b70-bf61-cbbbdc707687');
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-768a-88d6-82335f785612', '01935bd9-2c9f-72a8-840b-ae72e943c715');
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-768a-88d6-82335f785612', '01935bd9-2c9f-7c1c-b2ea-5ab839d2d87c');