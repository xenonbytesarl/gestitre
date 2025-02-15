-- Permission for shareholder
insert into t_permission (c_id, c_created_at, c_updated_at, c_name) values ('0194e738-b0ad-7398-922a-56c83fac15b3', current_timestamp, null, 'read:shareholder');
insert into t_permission (c_id, c_created_at, c_updated_at, c_name) values ('0194e738-8696-780c-84fc-c67317952780', current_timestamp, null, 'update:shareholder');
insert into t_permission (c_id, c_created_at, c_updated_at, c_name) values ('0194e738-532d-70ce-a97a-7f8cbf8bedbd', current_timestamp, null, 'create:shareholder');

-- Permission for role User to create, update and read shareholder
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-75e4-85a7-8deb40ac98b8', '0194e738-b0ad-7398-922a-56c83fac15b3');
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-75e4-85a7-8deb40ac98b8', '0194e738-8696-780c-84fc-c67317952780');
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-75e4-85a7-8deb40ac98b8', '0194e738-532d-70ce-a97a-7f8cbf8bedbd');

-- Permission for role Admin to create, update and read shareholder
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-73da-95db-d2fa15c9b206', '0194e738-b0ad-7398-922a-56c83fac15b3');
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-73da-95db-d2fa15c9b206', '0194e738-8696-780c-84fc-c67317952780');
insert into t_role_permission (c_role_id, c_permission_id) values ('01935bdb-8ca1-73da-95db-d2fa15c9b206', '0194e738-532d-70ce-a97a-7f8cbf8bedbd');