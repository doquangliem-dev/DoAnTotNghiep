package com.huit.kiemdinh.controller;

import com.huit.kiemdinh.security.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
public class AuthController {
    private final AuthService auth;
    public AuthController(AuthService auth){ this.auth = auth; }

    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        if(auth.isLoggedIn(session)) return "redirect:" + auth.homeFor(session);
        return "login";
    }

    @ResponseBody
    @PostMapping("/api/auth/login")
    public Map<String,Object> login(@RequestBody Map<String,String> body, HttpSession session) {
        Map<String,Object> me=auth.login(body.get("email"), body.get("matKhau"), session);
        me.put("home",auth.homeFor(session));
        return me;
    }

    @ResponseBody
    @GetMapping("/api/auth/me")
    public Map<String,Object> me(HttpSession session) {
        Map<String,Object> me=new LinkedHashMap<>(auth.me(session));
        me.put("home",auth.homeFor(session));
        return me;
    }

    @ResponseBody
    @PostMapping("/api/auth/logout")
    public Map<String,Object> logout(HttpSession session) {
        auth.logout(session);
        return Map.of("success", true);
    }
}
