CREATE TABLE IF NOT EXISTS user_service_audit
(
    id             serial,
    request_dttm   timestamp default now() not null,
    operation_type varchar(30)             not null,
    user_address   varchar(42)             not null,
    transaction    text,
    status         varchar(30)             not null,
    error_desc     varchar(100)
);

COMMENT ON TABLE user_service_audit IS 'История создания/удаления пользователей';

COMMENT ON COLUMN user_service_audit.request_dttm IS 'Дата и время изменения';
COMMENT ON COLUMN user_service_audit.operation_type IS 'Вид операции';
COMMENT ON COLUMN user_service_audit.user_address IS 'Пользователь';
COMMENT ON COLUMN user_service_audit.transaction IS 'Транзакция';
COMMENT ON COLUMN user_service_audit.status IS 'Статус транзакция';
COMMENT ON COLUMN user_service_audit.error_desc IS 'Описание ошибки';
