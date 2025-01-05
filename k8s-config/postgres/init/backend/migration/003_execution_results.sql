START TRANSACTION ;

CREATE TABLE execution_statuses (
    name VARCHAR(50) NOT NULL UNIQUE PRIMARY KEY,
    description TEXT
);

INSERT INTO execution_statuses (name, description) VALUES
       ('PENDING', 'Ожидает выполнения'),
       ('RUNNING', 'В процессе выполнения'),
       ('COMPLETED', 'Выполнено успешно'),
       ('FAILED', 'Завершено с ошибкой'),
       ('CANCELLED', 'Задача отменена');


CREATE TABLE execution_results (
   id SERIAL PRIMARY KEY,
   job_id BIGINT,
   started_at TIMESTAMP NOT NULL,
   finished_at TIMESTAMP,
   status VARCHAR NOT NULL REFERENCES execution_statuses(name),
   logs TEXT[],
   exit_code INT,
   error_message TEXT,
   recorded_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   CONSTRAINT fk_job FOREIGN KEY (job_id) REFERENCES JOBS (id) ON DELETE SET NULL
);

GRANT ALL PRIVILEGES ON TABLE execution_statuses TO backend_user;
GRANT ALL PRIVILEGES ON TABLE execution_results TO backend_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO backend_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO backend_user;

COMMIT ;