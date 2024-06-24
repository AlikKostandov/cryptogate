package com.cryptogate.entity;

import com.cryptogate.enums.OperationType;
import com.cryptogate.enums.PolicyCategory;
import com.cryptogate.enums.SourceType;
import com.cryptogate.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "policy_service_audit")
public class PolicyServiceAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "request_dttm")
    private LocalDateTime requestDttm = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type")
    private OperationType operationType;

    @Column(name = "policy_uuid")
    private UUID policyUuid;

    @Enumerated(EnumType.STRING)
    @Column(name = "policy_category")
    private PolicyCategory policyCategory;

    @Column(name = "source_uuid")
    private UUID sourceUuid;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_type")
    private SourceType sourceType;

    @Column(name = "transaction", columnDefinition = "TEXT", length = 2048)
    private String transaction;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TransactionStatus status;

    @Column(name = "error_desc")
    private String errorDesc;
}
