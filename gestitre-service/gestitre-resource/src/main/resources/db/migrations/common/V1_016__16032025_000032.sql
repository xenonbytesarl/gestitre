--Insert mail template
insert into t_mail_template (c_id, c_created_at, c_name, c_type, c_active) values ('01959c0b-9828-7a07-b6d1-30497c8c9fec', current_timestamp,'MFA', 'mfa', true);
insert into t_mail_template (c_id, c_created_at, c_name, c_type, c_active) values ('01959c0b-9828-7f0b-b953-4f2af994261d', current_timestamp, 'ACTIVATE_ACCOUNT', 'activate-account', true);
insert into t_mail_template (c_id, c_created_at, c_name, c_type, c_active) values ('01959c0b-9828-733c-8ee2-0e7d9ce8768e', current_timestamp, 'RESET_PASSWORD','reset-password', true);
insert into t_mail_template (c_id, c_created_at, c_name, c_type, c_active) values ('01959c0b-9828-7e32-a5f0-2837aba318a7', current_timestamp, 'VERIFY_MAIL_SERVER','verify-server', true);