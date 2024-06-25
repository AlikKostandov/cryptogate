package com.cryptogate.controller;

import com.cryptogate.entity.AccessRequestAuditEntity;
import com.cryptogate.entity.PolicyServiceAuditEntity;
import com.cryptogate.entity.SourceServiceAuditEntity;
import com.cryptogate.entity.UserServiceAuditEntity;
import com.cryptogate.repository.AccessRequestAuditRepository;
import com.cryptogate.repository.PolicyServiceAuditRepository;
import com.cryptogate.repository.SourceServiceAuditRepository;
import com.cryptogate.repository.UserServiceAuditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuditController {

    private final AccessRequestAuditRepository accessRequestAuditRepository;

    private final UserServiceAuditRepository userServiceAuditRepository;

    private final SourceServiceAuditRepository sourceServiceAuditRepository;

    private final PolicyServiceAuditRepository policyServiceAuditRepository;

    @GetMapping("/audit")
    public String getAuditPage() {
        return "audit-page";
    }

    @GetMapping("/audit/access")
    @ResponseBody
    public List<AccessRequestAuditEntity> getAccessRequestAudit() {
        return accessRequestAuditRepository.findLatest20();
    }

    @GetMapping("/audit/user")
    @ResponseBody
    public List<UserServiceAuditEntity> getUserServiceAudit() {
        return userServiceAuditRepository.findLatest20();
    }

    @GetMapping("/audit/source")
    @ResponseBody
    public List<SourceServiceAuditEntity> getSourceServiceAudit() {
        return sourceServiceAuditRepository.findLatest20();
    }

    @GetMapping("/audit/policy")
    @ResponseBody
    public List<PolicyServiceAuditEntity> getPolicyServiceAudit() {
        return policyServiceAuditRepository.findLatest20();
    }

    @GetMapping("/audit/user/transaction/{id}")
    @ResponseBody
    public String getUserTransaction(@PathVariable Long id) {
        UserServiceAuditEntity audit = userServiceAuditRepository.findById(id).orElse(null);
        if (audit != null) {
            return audit.getTransaction();
        }
        return "{}";
    }

    @GetMapping("/audit/source/transaction/{id}")
    @ResponseBody
    public String getSourceTransaction(@PathVariable Long id) {
        SourceServiceAuditEntity audit = sourceServiceAuditRepository.findById(id).orElse(null);
        if (audit != null) {
            return audit.getTransaction();
        }
        return "{}";
    }

    @GetMapping("/audit/policy/transaction/{id}")
    @ResponseBody
    public String getPolicyTransaction(@PathVariable Long id) {
        PolicyServiceAuditEntity audit = policyServiceAuditRepository.findById(id).orElse(null);
        if (audit != null) {
            return audit.getTransaction();
        }
        return "{}";
    }
}
