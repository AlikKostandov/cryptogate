CREATE TABLE IF NOT EXISTS access_request_audit
(
    id                serial,
    request_dttm      timestamp default now() not null,
    initiator_address varchar(42)             not null,
    source_uuid       uuid                    not null,
    access_granted    boolean                 not null,
    error_desc        varchar(100)
);

COMMENT ON TABLE access_request_audit IS 'История запросов доступа к ресурсам';

COMMENT ON COLUMN access_request_audit.request_dttm IS 'Дата и время запроса';
COMMENT ON COLUMN access_request_audit.initiator_address IS 'Кто запросил';
COMMENT ON COLUMN access_request_audit.source_uuid IS 'Какой ресурс';
COMMENT ON COLUMN access_request_audit.access_granted IS 'Доступ выдан';
COMMENT ON COLUMN access_request_audit.error_desc IS 'Описание ошибки';

