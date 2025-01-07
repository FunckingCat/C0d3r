START TRANSACTION ;

ALTER TABLE jobs ADD COLUMN deleted bool default false;

COMMIT ;