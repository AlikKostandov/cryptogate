CREATE TABLE IF NOT EXISTS source_service_audit
(
    id             serial,
    request_dttm   timestamp default now() not null,
    operation_type varchar(30)             not null,
    source_uuid    uuid                    not null,
    transaction    text,
    status         varchar(30)             not null,
    error_desc     varchar(100)
    );

COMMENT ON TABLE source_service_audit IS 'История создания/удаления ресурсов';

COMMENT ON COLUMN source_service_audit.request_dttm IS 'Дата и время изменения';
COMMENT ON COLUMN source_service_audit.operation_type IS 'Вид операции';
COMMENT ON COLUMN source_service_audit.source_uuid IS 'Ресурс';
COMMENT ON COLUMN source_service_audit.transaction IS 'Транзакция';
COMMENT ON COLUMN source_service_audit.status IS 'Статус транзакция';
COMMENT ON COLUMN source_service_audit.error_desc IS 'Описание ошибки';