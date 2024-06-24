package com.cryptogate.repository;

import com.cryptogate.entity.UserServiceAuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserServiceAuditRepository extends JpaRepository<UserServiceAuditEntity, Long> {

}
