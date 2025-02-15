-- t_shareholder
alter table t_shareholder alter column c_shareholder_type drop not null;
alter table t_shareholder alter column c_representative_name drop not null;