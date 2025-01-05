START TRANSACTION ;

CREATE TABLE EXECUTION_TYPES (
     name VARCHAR(50) NOT NULL UNIQUE PRIMARY KEY,
     description TEXT
);

INSERT INTO EXECUTION_TYPES (name, description)
VALUES
    ('ON_DEMAND', 'Выполнение по запросу'),
    ('SCHEDULED', 'Регулярное выполнение по расписанию'),
    ('WEBHOOK', 'Запуск по событию');

CREATE TABLE JOBS (
      id BIGSERIAL PRIMARY KEY,
      user_id UUID NOT NULL,
      name VARCHAR(255) NOT NULL UNIQUE,
      created_at TIMESTAMP DEFAULT NOW(),
      docker_image VARCHAR(1024) NOT NULL,
      command TEXT[] NOT NULL,
      environment_variables TEXT DEFAULT '{}',
      execution_type VARCHAR NOT NULL REFERENCES EXECUTION_TYPES(name),
      schedule VARCHAR(255),
      CONSTRAINT check_schedule_execution CHECK (
          (execution_type = 'SCHEDULED' AND schedule IS NOT NULL) OR (execution_type != 'SCHEDULED')
      )
);

GRANT ALL PRIVILEGES ON TABLE EXECUTION_TYPES TO backend_user;
GRANT ALL PRIVILEGES ON TABLE JOBS TO backend_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO backend_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO backend_user;

COMMIT ;