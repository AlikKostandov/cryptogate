package com.cryptogate.controller;

import com.cryptogate.dto.Source;
import com.cryptogate.service.PAPService;
import com.cryptogate.service.PIPService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PolicyController {

    private final PAPService papService;

    private final PIPService pipService;

    @GetMapping("/policies")
    public String showPoliciesPage(Model model) {
        model.addAttribute("sources", pipService.getAllSources().stream()
                .map(Source::getSourceId)
                .collect(Collectors.toList()));
        model.addAttribute("policies", papService.getAllPolicy());
        return "policies-page";
    }

    @PostMapping("policies/policy/add")
    public String addNewPolicy(@RequestParam(required = false) String sourceId,
                               @RequestParam(required = false) Long sourceType,
                               @RequestParam(required = false) Long allowedRole,
                               @RequestParam(required = false) Long allowedDepartment) throws Exception {
        papService.addPolicy(sourceId, sourceType, allowedRole, allowedDepartment);
        return "redirect:/policies";
    }

    @GetMapping("/policies/remove/{policyId}")
    public String removePolicy(@PathVariable String policyId) {
        papService.removePolicy(policyId);
        return "redirect:/policies";
    }

}
