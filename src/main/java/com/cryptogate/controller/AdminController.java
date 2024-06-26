package com.cryptogate.controller;

import com.cryptogate.service.UserService;
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
public class AdminController {

    private final UserService userService;

    @GetMapping("/admin")
    public String showAdminPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin-page";
    }

    @PostMapping("admin/user/add")
    public String addNewUser(@RequestParam String userAddress,
                             @RequestParam String username,
                             @RequestParam Long role,
                             @RequestParam Long department) {
        userService.registerUser(userAddress, username, role, department);
        return "redirect:/admin";
    }

    @GetMapping("/admin/remove/{userAddress}")
    public String removeUserByPassNumber(@PathVariable String userAddress) {
        userService.removeUser(userAddress);
        return "redirect:/admin";
    }

}
