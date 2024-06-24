package com.cryptogate.repository;

import com.cryptogate.entity.SourceServiceAuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceServiceAuditRepository extends JpaRepository<SourceServiceAuditEntity, Long> {

}
