package com.cryptogate.entity;

import com.cryptogate.enums.OperationType;
import com.cryptogate.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "user_service_audit")
public class UserServiceAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "request_dttm")
    private LocalDateTime requestDttm = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type")
    private OperationType operationType;

    @Column(name = "user_address")
    private String userAddress;

    @Column(name = "transaction", columnDefinition = "TEXT", length = 2048)
    private String transaction;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TransactionStatus status;

    @Column(name = "error_desc")
    private String errorDesc;
}
