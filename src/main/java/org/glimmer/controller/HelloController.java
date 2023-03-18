package org.glimmer.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // TODO(AntiO2) Remember delete it
public class HelloController {
    @GetMapping("/test")
    @PreAuthorize("hasAuthority('system:anti:test')")
    String test(){
        return "test";
    }
    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('system:admin:test')")
    public String admin(){
        return "admin";
    }
}
