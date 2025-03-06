START TRANSACTION ;

alter table jobs add column group_id uuid;

COMMIT ;