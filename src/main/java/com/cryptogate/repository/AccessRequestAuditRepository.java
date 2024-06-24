package com.cryptogate.repository;

import com.cryptogate.entity.AccessRequestAuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessRequestAuditRepository extends JpaRepository<AccessRequestAuditEntity, Long> {

}
