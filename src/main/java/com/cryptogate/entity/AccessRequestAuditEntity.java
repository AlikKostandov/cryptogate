package com.cryptogate.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "access_request_audit")
public class AccessRequestAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "request_dttm")
    private LocalDateTime requestDttm = LocalDateTime.now();

    @Column(name = "initiator_address")
    private String initiatorAddress;

    @Column(name = "source_uuid")
    private UUID sourceUuid;

    @Column(name = "access_granted")
    private boolean accessGranted;

    @Column(name = "error_desc")
    private String errorDesc;
}
