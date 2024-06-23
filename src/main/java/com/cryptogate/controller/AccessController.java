package com.cryptogate.controller;

import com.cryptogate.service.AccessControlService;
import com.cryptogate.service.SourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.StringJoiner;

import static com.cryptogate.util.CommonConstants.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AccessController {

    private final AccessControlService accessControlService;

    private final SourceService sourceService;

    @GetMapping("/user/sources")
    public String showUserPage(@RequestParam String userAddress,
                               @RequestParam(required = false) Boolean accessGranted,
                               @RequestParam(required = false) String sourceId,
                               Model model) throws Exception {
        model.addAttribute("sources", sourceService.getAllSources());
        model.addAttribute("userAddress", userAddress);
        model.addAttribute("accessGranted", accessGranted);
        model.addAttribute("sourceId", sourceId);
        return "user-page";
    }

    @PostMapping("user/requestAccess/{userAddress}/{sourceId}")
    public String checkAccess(@PathVariable String userAddress,
                              @PathVariable String sourceId,
                              Model model) {
        boolean accessGranted = accessControlService.checkAccess(userAddress, sourceId);
        String baseUrl = "redirect:/user/sources";
        StringJoiner pathWithParams = new StringJoiner(AND, baseUrl + REQ_PARAMS, EMPTY_LINE);
        pathWithParams.add(USER_ADDRESS_PATH_VAR + userAddress)
                .add(ACCESS_GRANTED_PATH_VAR + accessGranted)
                .add(SOURCE_ID_PATH_VAR + sourceId);
        return pathWithParams.toString();
    }
}

