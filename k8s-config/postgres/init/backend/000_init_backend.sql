CREATE DATABASE backend;

CREATE USER backend_user WITH PASSWORD 'password';

GRANT ALL PRIVILEGES ON DATABASE backend TO backend_user;

-- Grant all privileges on the database itself
GRANT ALL PRIVILEGES ON DATABASE backend TO backend_user;

-- Grant privileges for schema creation and usage
GRANT CREATE ON DATABASE backend TO backend_user;

-- Grant usage on all schemas in the database
DO $$
    DECLARE
        schema_name text;
    BEGIN
        FOR schema_name IN
            SELECT nspname FROM pg_catalog.pg_namespace
            WHERE nspname NOT IN ('pg_catalog', 'information_schema')
            LOOP
                EXECUTE format('GRANT USAGE, CREATE ON SCHEMA %I TO backend_user', schema_name);
                EXECUTE format('GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA %I TO backend_user', schema_name);
                EXECUTE format('GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA %I TO backend_user', schema_name);
                EXECUTE format('GRANT ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA %I TO backend_user', schema_name);
            END LOOP;
    END $$;

-- Ensure that future objects are covered with default privileges
ALTER DEFAULT PRIVILEGES IN SCHEMA public
    GRANT ALL PRIVILEGES ON TABLES TO backend_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public
    GRANT ALL PRIVILEGES ON SEQUENCES TO backend_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public
    GRANT EXECUTE ON FUNCTIONS TO backend_user;

CREATE EXTENSION IF NOT EXISTS "pgcrypto";
