-- Create table t_company
-- create table if not exists t_company (
--     c_id UUID not null ,
--     c_created_at timestamp with time zone not null ,
--     c_updated_at timestamp with time zone,
--     c_company_name varchar(64) not null ,
--     c_company_manager_name varchar(64) not null ,
--     c_licence varchar(64) not null ,
--     c_legal_form varchar(64) not null ,
--     c_address_street varchar(128) ,
--     c_address_city varchar(64) not null ,
--     c_address_zip_code varchar(32) not null ,
--     c_address_country varchar(64) not null ,
--     c_contact_phone varchar(32) ,
--     c_contact_fax varchar(64) ,
--     c_contact_email varchar(128) not null ,
--     c_contact_name varchar(64) not null ,
--     c_activity varchar(128) ,
--     c_registration_number varchar(64) ,
--     c_website_url varchar(64) ,
--     c_isin_code varchar(64) ,
--     c_tax_number varchar(64) ,
--     c_logo_filename varchar(512) not null ,
--     c_stamp_filename varchar(512) not null ,
--     c_gross_dividend_stock_unit numeric(38,2) ,
--     c_nominal_value numeric(38,2) ,
--     c_stock_quantity bigint ,
--     c_active boolean not null ,
--     c_certificate_template_id uuid ,
--     constraint Pk_t_company_c_id primary key (c_id),
--     constraint Uk_t_company_c_company_name unique (c_company_name),
--     constraint Uk_t_company_c_email unique (c_email),
--     constraint Uk_t_company_c_fax unique (c_fax),
--     constraint Uk_t_company_c_phone unique (c_phone)
-- );

insert into t_company (c_id, c_created_at, c_company_name, c_company_manager_name, c_licence, c_legal_form, c_address_city, c_address_zip_code, c_address_country, c_contact_email, c_contact_name, c_logo_filename, c_stamp_filename, c_active, c_contact_phone) VALUES
('0192f2b7-c1d4-795e-8e9f-b382b4c61d01', current_timestamp, 'Company Name x', 'Company Manager Name x', 'MONTH_12', 'SA', 'City x', '308', 'Country x', 'emailx@gmail.com', 'Name x', './gestitre/logo/logox.png', './gestitre/stamp/stampx.png', true, '645254238');

insert into t_company (c_id, c_created_at, c_company_name, c_company_manager_name, c_licence, c_legal_form, c_address_city, c_address_zip_code, c_address_country, c_contact_email, c_contact_name, c_logo_filename, c_stamp_filename, c_active, c_contact_phone) VALUES
    ('0192f2b8-60a0-713c-b928-df5a1fb672b8', current_timestamp,  'Company Name y', 'Company Manager Name y', 'MONTH_24', 'EI', 'City y', '309', 'Country y', 'emaily@gmail.com', 'Name y', './gestitre/logo/logoy.png', './gestitre/stamp/stampy.png', true, '645 254 239');
