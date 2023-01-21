package pl.kacperg.workoutsbackend.users.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class HomeController {
    @PreAuthorize("hasAuthority('SCOPE_USER)')")
    @GetMapping("/user")
    public String user() {
        return "Home user";
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/admin")
    public String admin() {
        return "Home admin";
    }
}
