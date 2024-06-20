package com.cryptogate.controller;

import com.cryptogate.dto.BaseUserEntity;
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
public class SourceController {

    private final PIPService pipService;

    @GetMapping("/sources")
    public String showSourcePage(Model model) {
        model.addAttribute("sources", pipService.getAllSources());
        model.addAttribute("users", pipService.getAllUsers().stream()
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
        pipService.addSource(owner, title, sourceType, secretLevel, allowedUser);
        return "redirect:/sources";
    }

    @GetMapping("/sources/remove/{sourceId}")
    public String removeSource(@PathVariable String sourceId) {
        pipService.removeSource(sourceId);
        return "redirect:/sources";
    }

}
