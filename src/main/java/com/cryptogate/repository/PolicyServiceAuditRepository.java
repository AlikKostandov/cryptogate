package com.cryptogate.repository;

import com.cryptogate.entity.PolicyServiceAuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyServiceAuditRepository extends JpaRepository<PolicyServiceAuditEntity, Long> {

}
