START TRANSACTION ;

ALTER TABLE jobs ADD COLUMN ordinal int default 1 not null;

COMMIT ;