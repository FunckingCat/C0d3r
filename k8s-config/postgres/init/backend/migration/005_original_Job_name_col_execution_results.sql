START TRANSACTION ;

ALTER TABLE execution_results ADD COLUMN original_job_name varchar;

COMMIT ;