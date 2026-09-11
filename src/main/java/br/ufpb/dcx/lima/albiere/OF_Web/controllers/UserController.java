package br.ufpb.dcx.lima.albiere.OF_Web.controllers;

import br.ufpb.dcx.lima.albiere.OF_Web.dtos.LoginRequest;
import br.ufpb.dcx.lima.albiere.OF_Web.dtos.LoginResponse;
import br.ufpb.dcx.lima.albiere.OF_Web.dtos.UserResponseDTO;
import br.ufpb.dcx.lima.albiere.OF_Web.models.User;
import br.ufpb.dcx.lima.albiere.OF_Web.repositories.UserRepository;
import br.ufpb.dcx.lima.albiere.OF_Web.services.UserService;
import br.ufpb.dcx.lima.albiere.OF_Web.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "E-mail ou senha incorretos."));
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "E-mail ou senha incorretos."));
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        UserResponseDTO userDTO = new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRole());
        return ResponseEntity.ok(new LoginResponse(token, userDTO));
    }

    @PutMapping("/{id}/avatar")
    public ResponseEntity<?> updateAvatar(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        return userRepository.findById(id).map(user -> {
            user.setPicture(payload.get("picture"));
            User updatedUser = userRepository.save(user);
            return ResponseEntity.ok(updatedUser);
        }).orElse(ResponseEntity.notFound().build());
    }
}
