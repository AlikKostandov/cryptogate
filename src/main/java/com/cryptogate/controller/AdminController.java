package com.cryptogate.controller;

import com.cryptogate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/admin")
    public String showLoginForm() {
        return "admin-page";
    }

    @PostMapping("admin/user/add")
    public String addNewUser(@RequestParam String userAddress,
                             @RequestParam String username,
                             @RequestParam String password,
                             @RequestParam Long role,
                             @RequestParam Long department ) throws Exception {
        userService.registerUser(userAddress, username, password, role, department);
        return "redirect:/admin";
    }
}
