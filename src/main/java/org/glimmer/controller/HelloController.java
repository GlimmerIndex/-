package org.glimmer.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/test/user")
    @PreAuthorize("hasAuthority('system:anti:test')")
    String test(){
        return "test";
    }
    @GetMapping("/test/admin")
    @PreAuthorize("hasAuthority('system:admin:test')")
    public String admin(){
        return "admin";
    }


}
