package com.backend2.backend2_pensionat_with_maven.controllers;

import com.backend2.backend2_pensionat_with_maven.dtos.UserDto;
import com.backend2.backend2_pensionat_with_maven.repos.UserRepo;
import com.backend2.backend2_pensionat_with_maven.services.RoleService;
import com.backend2.backend2_pensionat_with_maven.services.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/admin/users")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final UserRepo userRepo;

    public AdminController(UserService userService, RoleService roleService, UserRepo userRepo) {
        this.userService = userService;
        this.roleService = roleService;
        this.userRepo = userRepo;
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

    @PostMapping("/add")
    public String saveUser(@Valid @ModelAttribute("user") UserDto user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", new UserDto());
            model.addAttribute("roles", roleService.getAllRoles());
            return "usersForm";
        } else if (userRepo.getUserByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("user", new UserDto());
            model.addAttribute("roles", roleService.getAllRoles());
            return "usersForm";
        }
        userService.spara(user);
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