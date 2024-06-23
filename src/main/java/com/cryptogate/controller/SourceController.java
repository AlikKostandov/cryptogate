package com.cryptogate.controller;

import com.cryptogate.dto.BaseUserEntity;
import com.cryptogate.service.SourceService;
import com.cryptogate.service.UserService;
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
public class SourceController {

    private final UserService userService;

    private final SourceService sourceService;

    @GetMapping("/sources")
    public String showSourcePage(Model model) throws Exception {
        model.addAttribute("sources", sourceService.getAllSources());
        model.addAttribute("users",
                userService.getAllUsers().stream()
                .map(BaseUserEntity::getUserAddress)
                .collect(Collectors.toList()));
        return "source-page";
    }

    @PostMapping("sources/source/add")
    public String addNewSource(@RequestParam String owner,
                               @RequestParam String title,
                               @RequestParam Long sourceType,
                               @RequestParam Long secretLevel,
                               @RequestParam String allowedUser) throws Exception {
        sourceService.addSource(owner, title, sourceType, secretLevel, allowedUser);
        return "redirect:/sources";
    }

    @GetMapping("/sources/remove/{sourceId}")
    public String removeSource(@PathVariable String sourceId) throws Exception {
        sourceService.removeSource(sourceId);
        return "redirect:/sources";
    }

}
