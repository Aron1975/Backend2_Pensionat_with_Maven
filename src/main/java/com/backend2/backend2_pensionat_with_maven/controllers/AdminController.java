package com.backend2.backend2_pensionat_with_maven.controllers;

import com.backend2.backend2_pensionat_with_maven.dtos.UserDto;
import com.backend2.backend2_pensionat_with_maven.services.RoleService;
import com.backend2.backend2_pensionat_with_maven.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/admin/users")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "listUsers";
    }

    @GetMapping("/new")
    public String createUserForm(Model model) {
        model.addAttribute("user", new UserDto());
        model.addAttribute("roles", roleService.getAllRoles());
        return "usersForm";
    }

    @PostMapping
    public String saveUser(@ModelAttribute("user") UserDto userDto) {
        userService.spara(userDto);
        return "redirect:/admin/users";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable UUID id, Model model) {
        UserDto userDto = userService.getUser(id);
        model.addAttribute("user", userDto);
        model.addAttribute("roles", roleService.getAllRoles());
        return "usersForm";
    }

    @PostMapping("/{id}")
    public String updateUser(@PathVariable UUID id, @ModelAttribute("user") UserDto userDto) {
        userDto.setId(id);
        userService.spara(userDto);
        return "redirect:/admin/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable UUID id) {
        userService.deleteUserById(id);
        return "redirect:/admin/users";
    }
}