-- Variables passées par psql : app_db, app_user, app_password

-- 1. Rôle dédié à l'application (créé s'il n'existe pas, mot de passe resynchronisé sinon)
SELECT format('CREATE ROLE %I LOGIN', :'app_user')
    WHERE NOT EXISTS (SELECT FROM pg_roles WHERE rolname = :'app_user')
    \gexec

SELECT format('ALTER ROLE %I WITH LOGIN PASSWORD %L', :'app_user', :'app_password')
    \gexec

-- 2. Suppression de la base (coupe les connexions ouvertes, PG 13+)
SELECT format('DROP DATABASE IF EXISTS %I WITH (FORCE)', :'app_db')
    \gexec
-- 3. Recréation, avec le user applicatif comme propriétaire
SELECT format('CREATE DATABASE %I OWNER %I', :'app_db', :'app_user')
    \gexec