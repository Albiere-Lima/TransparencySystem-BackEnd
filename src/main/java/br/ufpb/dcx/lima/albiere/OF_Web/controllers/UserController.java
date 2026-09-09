package br.ufpb.dcx.lima.albiere.OF_Web.controllers;

import br.ufpb.dcx.lima.albiere.OF_Web.dtos.UserResponseDTO;
import br.ufpb.dcx.lima.albiere.OF_Web.models.User;
import br.ufpb.dcx.lima.albiere.OF_Web.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody User user) {
        UserResponseDTO createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
