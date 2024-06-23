package com.cryptogate.controller;

import com.cryptogate.service.PEPService;
import com.cryptogate.service.PIPService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final PIPService attributeService;

    private final PEPService accessService;

    @GetMapping("/user/sources")
    public String showUserPage(@RequestParam String userAddress,
                               @RequestParam(required = false) Boolean accessGranted,
                               @RequestParam(required = false) String sourceId,
                               Model model) {
        model.addAttribute("sources", attributeService.getAllSources());
        model.addAttribute("userAddress", userAddress);
        model.addAttribute("accessGranted", accessGranted);
        model.addAttribute("sourceId", sourceId);
        return "user-page";
    }

    @PostMapping("user/requestAccess/{userAddress}/{sourceId}")
    public String checkAccess(@PathVariable String userAddress,
                              @PathVariable String sourceId,
                              Model model) {
        boolean accessGranted = accessService.checkAccess(userAddress, sourceId);
        return "redirect:/user/sources?userAddress=" + userAddress +
                "&accessGranted=" + accessGranted +
                "&sourceId=" + sourceId;
    }
}

