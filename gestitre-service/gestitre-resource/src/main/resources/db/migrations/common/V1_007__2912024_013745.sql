-- Root Tenant
insert into t_tenant(c_id, c_created_at, c_name, c_active) values ('01937563-b2fc-79f9-bf40-d03bc39383f1', current_timestamp,  'ROOT', true);

-- Root Company
insert into t_company (c_id, c_created_at, c_company_name, c_company_manager_name, c_licence, c_legal_form, c_address_city, c_address_zip_code, c_address_country, c_contact_email, c_contact_name, c_active, c_contact_phone, c_created_date) VALUES
    ('01937563-f905-7965-a014-da683621056c', current_timestamp, 'Root Company', 'Root Manager', 'MONTH_36', 'SA', 'City', '308', 'Cameroon', 'root-company@gmail.com', 'Default', true, '645254236', current_timestamp);

--Root User / password: gestitre123!
insert into t_user (c_id, c_created_at,  c_name, c_email, c_password, c_account_enabled, c_credential_expired, c_account_locked, c_account_expired, c_use_mfa, c_failed_login_attempt, c_tenant_id, c_company_id, c_timezone, c_language) values
   ('01937564-456e-7514-8c6c-1db19c1614d6', current_timestamp, 'Administrator', 'ambiandji@gmail.com', '$2a$12$251yRnRsGv9U8bAtwgUlV.8qRBTNvWVFKlhHPWApk9ct7DBCIJjam', true, false, false, false, true, 0, '01937563-b2fc-79f9-bf40-d03bc39383f1', '01937563-f905-7965-a014-da683621056c', 'Africa_Douala', 'FR');

insert into t_user_role(c_user_id, c_role_id) values ('01937564-456e-7514-8c6c-1db19c1614d6','01935bdb-8ca1-768a-88d6-82335f785612')