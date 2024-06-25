package com.cryptogate.repository;

import com.cryptogate.entity.UserServiceAuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserServiceAuditRepository extends JpaRepository<UserServiceAuditEntity, Long> {

    @Query(value = "SELECT * FROM user_service_audit ORDER BY request_dttm DESC LIMIT 20", nativeQuery = true)
    List<UserServiceAuditEntity> findLatest20();

}
