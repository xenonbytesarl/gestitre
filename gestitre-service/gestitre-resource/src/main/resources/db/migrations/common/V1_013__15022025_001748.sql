--t_company
alter table t_company add column c_code varchar(16);
alter table t_company add constraint Uk_t_company_c_code unique (c_code);

--t_tenant
alter table t_tenant add column c_code varchar(16);
alter table t_tenant add constraint Uk_t_tenant_c_code unique (c_code);

--t_company
update t_company set c_code='CM20250201' where c_id='01937563-f905-7965-a014-da683621056c';

--t_tenant
update t_tenant set c_code='CM20250201' where c_id='01937563-b2fc-79f9-bf40-d03bc39383f1';

--t_company
alter table t_company alter column c_code set not null ;

--t_tenant
alter table t_tenant alter column c_code set not null ;