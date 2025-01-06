START TRANSACTION;

CREATE TABLE JOB_STATUSES (
      name VARCHAR(50) NOT NULL UNIQUE PRIMARY KEY,
      description TEXT
);

INSERT INTO JOB_STATUSES (name, description)
VALUES
    ('PENDING', 'Ожидает запуска'),
    ('RUNNING', 'Выполняется'),
    ('COMPLETED', 'Завершена'),
    ('FAILED', 'Прервана'),
    ('CANCELLED', 'Отменена');

ALTER TABLE JOBS
    ADD COLUMN status VARCHAR(50) DEFAULT 'RUNNING' NOT NULL REFERENCES JOB_STATUSES(name);

GRANT ALL PRIVILEGES ON TABLE JOB_STATUSES TO backend_user;

COMMIT;

