CREATE TABLE IF NOT EXISTS policy_service_audit
(
    id              serial,
    request_dttm    timestamp default now() not null,
    operation_type  varchar(30)             not null,
    policy_uuid     uuid                    not null,
    policy_category varchar(30),
    source_uuid     uuid,
    source_type     varchar(30),
    transaction     text,
    status          varchar(30)             not null,
    error_desc      varchar(100)
);

COMMENT ON TABLE policy_service_audit IS 'История создания/удаления политик доступа';

COMMENT ON COLUMN policy_service_audit.request_dttm IS 'Дата и время изменения';
COMMENT ON COLUMN policy_service_audit.operation_type IS 'Вид операции';
COMMENT ON COLUMN policy_service_audit.policy_category IS 'Ресурс/Тип ресурса';
COMMENT ON COLUMN policy_service_audit.source_uuid IS 'UUID ресурса';
COMMENT ON COLUMN policy_service_audit.source_type IS 'Тип ресурсов';
COMMENT ON COLUMN policy_service_audit.transaction IS 'Транзакция';
COMMENT ON COLUMN policy_service_audit.status IS 'Статус транзакция';
COMMENT ON COLUMN policy_service_audit.error_desc IS 'Описание ошибки';