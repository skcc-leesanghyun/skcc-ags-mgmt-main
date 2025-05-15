-- PostgreSQL Role and User Creation Script
-- Description: Creates roles and users for AGS Management System
-- Author: Claude
-- Date: 2024

-- Connect to PostgreSQL
\c postgres;

-- Create AGS schema if not exists
CREATE SCHEMA IF NOT EXISTS AGS;

-- Create Admin Role with DDL privileges
CREATE ROLE ags_admin_role;

-- Grant DDL privileges to Admin Role
GRANT ALL PRIVILEGES ON SCHEMA AGS TO ags_admin_role;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA AGS TO ags_admin_role;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA AGS TO ags_admin_role;

-- Set default privileges for future objects
ALTER DEFAULT PRIVILEGES IN SCHEMA AGS GRANT ALL PRIVILEGES ON TABLES TO ags_admin_role;
ALTER DEFAULT PRIVILEGES IN SCHEMA AGS GRANT ALL PRIVILEGES ON SEQUENCES TO ags_admin_role;

-- Create Developer Role with DML privileges
CREATE ROLE ags_developer_role;

-- Grant DML privileges to Developer Role
GRANT USAGE ON SCHEMA AGS TO ags_developer_role;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA AGS TO ags_developer_role;
GRANT USAGE ON ALL SEQUENCES IN SCHEMA AGS TO ags_developer_role;

-- Set default privileges for future objects
ALTER DEFAULT PRIVILEGES IN SCHEMA AGS GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO ags_developer_role;
ALTER DEFAULT PRIVILEGES IN SCHEMA AGS GRANT USAGE ON SEQUENCES TO ags_developer_role;

-- Create Admin User
CREATE USER ags_admin WITH PASSWORD 'Admin@123' IN ROLE ags_admin_role;

-- Create Developer User
CREATE USER ags_developer WITH PASSWORD 'Dev@123' IN ROLE ags_developer_role;

-- Grant schema ownership to admin role
ALTER SCHEMA AGS OWNER TO ags_admin_role;

-- Verify roles and users
\du

-- Verify schema privileges
\dn+

-- Note: Please change the passwords in production environment
-- Note: The actual passwords should be stored securely and not in version control 
