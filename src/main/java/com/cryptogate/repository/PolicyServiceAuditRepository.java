package com.cryptogate.repository;

import com.cryptogate.entity.PolicyServiceAuditEntity;
import com.cryptogate.entity.UserServiceAuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PolicyServiceAuditRepository extends JpaRepository<PolicyServiceAuditEntity, Long> {

    @Query(value = "SELECT * FROM policy_service_audit ORDER BY request_dttm DESC LIMIT 20", nativeQuery = true)
    List<PolicyServiceAuditEntity> findLatest20();
}
