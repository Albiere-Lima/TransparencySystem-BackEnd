package br.ufpb.dcx.lima.albiere.OF_Web.controllers;

import br.ufpb.dcx.lima.albiere.OF_Web.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @PutMapping("/set-role")
    public ResponseEntity<?> setAdminRole(@RequestParam String email, @RequestParam String role) {
        return userRepository.findByEmail(email)
                .map(user -> {
                    user.setRole(role);
                    userRepository.save(user);
                    return ResponseEntity.ok().body("Permissão do usuário atualizada para " + role);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}