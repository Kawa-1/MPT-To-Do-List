FROM mysql:8.0

ENV MYSQL_DATABASE=todo_calendar \
    MYSQL_ROOT_PASSWORD=my-secret-pw

COPY init_schema.sql /docker-entrypoint-initdb.d/
