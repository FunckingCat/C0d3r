START TRANSACTION ;

CREATE TABLE EXECUTION_TYPES (
     name VARCHAR(50) NOT NULL UNIQUE PRIMARY KEY,    -- Название типа запуска (например, 'ON_DEMAND')
     description TEXT                                 -- Описание типа запуска
);

INSERT INTO EXECUTION_TYPES (name, description)
VALUES
    ('ON_DEMAND', 'Выполнение по запросу'),
    ('SCHEDULED', 'Регулярное выполнение по расписанию'),
    ('WEBHOOK', 'Запуск по событию');

CREATE TABLE JOBS (
      id BIGSERIAL PRIMARY KEY,                 -- Идентификатор задачи
      user_id UUID NOT NULL,                    -- Идентификатор пользователя
      name VARCHAR(255) NOT NULL UNIQUE,               -- Название задачи
      created_at TIMESTAMP DEFAULT NOW(),        -- Время создания задачи
      docker_image VARCHAR(1024) NOT NULL,       -- Ссылка на Docker-образ
      command TEXT[] NOT NULL,                  -- Команда запуска контейнера
      environment_variables TEXT DEFAULT '{}', -- Переменные окружения в формате JSON
      execution_type VARCHAR NOT NULL REFERENCES EXECUTION_TYPES(name), -- Ссылка на EXECUTION_TYPES
      schedule VARCHAR(255),                    -- Планировщик CronJob, если используется
      CONSTRAINT check_schedule_execution CHECK (
          (execution_type = 'SCHEDULED' AND schedule IS NOT NULL) OR (execution_type != 'SCHEDULED')
      )
);

GRANT ALL PRIVILEGES ON TABLE EXECUTION_TYPES TO backend_user;
GRANT ALL PRIVILEGES ON TABLE JOBS TO backend_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO backend_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO backend_user;

COMMIT ;