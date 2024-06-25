package com.cryptogate.repository;

import com.cryptogate.entity.AccessRequestAuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessRequestAuditRepository extends JpaRepository<AccessRequestAuditEntity, Long> {

    @Query(value = "SELECT * FROM access_request_audit ORDER BY request_dttm DESC LIMIT 20", nativeQuery = true)
    List<AccessRequestAuditEntity> findLatest20();
}
