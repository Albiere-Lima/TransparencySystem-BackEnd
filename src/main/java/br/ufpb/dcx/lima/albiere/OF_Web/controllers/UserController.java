package br.ufpb.dcx.lima.albiere.OF_Web.controllers;

import br.ufpb.dcx.lima.albiere.OF_Web.dtos.*;
import br.ufpb.dcx.lima.albiere.OF_Web.models.User;
import br.ufpb.dcx.lima.albiere.OF_Web.repositories.UserRepository;
import br.ufpb.dcx.lima.albiere.OF_Web.services.UserService;
import br.ufpb.dcx.lima.albiere.OF_Web.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "E-mail ou senha incorretos."));
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        // Retorna DTO com os dados do avatar/foto inclusos
        UserResponseDTO userDTO = new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPhone(),
                user.getRegistrationNumber(),
                user.getCampus(),
                user.getDepartment(),
                user.getBio(),
                user.getRole(),
                user.getInitials(),
                user.getAvatarColor(),
                user.getPicture(),
                user.getPassword(),
                user.getNotifyDailySummary(),
                user.getNotifyMonthlyReports(),
                user.getNotifyNewExpenses(),
                user.getNotifySystemAlerts()
        );

        return ResponseEntity.ok(new LoginResponse(token, userDTO));
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<?> updateProfile(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        return userRepository.findById(id).map(user -> {
            if (payload.containsKey("name")) user.setName((String) payload.get("name"));
            if (payload.containsKey("department")) user.setDepartment((String) payload.get("department"));
            if (payload.containsKey("initials")) user.setInitials((String) payload.get("initials"));
            if (payload.containsKey("bio")) user.setBio((String) payload.get("bio"));
            if (payload.containsKey("avatarColor")) user.setAvatarColor((String) payload.get("avatarColor"));
            if (payload.containsKey("surname")) user.setSurname((String) payload.get("surname"));
            if (payload.containsKey("campus")) user.setCampus((String) payload.get("campus"));
            if (payload.containsKey("phone")) user.setPhone((String) payload.get("phone"));
            if (payload.containsKey("registrationNumber")) user.setRegistrationNumber((String) payload.get("registrationNumber"));
            if (payload.containsKey("pictureUrl")) {
                user.setPicture((String) payload.get("pictureUrl"));
            } else if (payload.containsKey("picture")) {
                user.setPicture((String) payload.get("picture"));
            }

            User updatedUser = userRepository.save(user);

            UserResponseDTO responseDTO = new UserResponseDTO(
                    updatedUser.getId(),
                    updatedUser.getName(),
                    updatedUser.getSurname(),
                    updatedUser.getEmail(),
                    updatedUser.getPhone(),
                    updatedUser.getRegistrationNumber(),
                    updatedUser.getCampus(),
                    updatedUser.getDepartment(),
                    updatedUser.getBio(),
                    updatedUser.getRole(),
                    updatedUser.getInitials(),
                    updatedUser.getAvatarColor(),
                    updatedUser.getPicture(),
                    updatedUser.getPassword(),
                    updatedUser.getNotifyDailySummary(),
                    updatedUser.getNotifyMonthlyReports(),
                    updatedUser.getNotifyNewExpenses(),
                    updatedUser.getNotifySystemAlerts()
            );

            return ResponseEntity.ok(responseDTO);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/notifications")
    public ResponseEntity<UserResponseDTO> updateNotifications(@RequestBody UserNotificationsDTO dto, Principal principal) {
        UserResponseDTO updatedUser = userService.updateNotifications(principal.getName(), dto);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordDTO dto, Principal principal) {
        userService.changePassword(principal.getName(), dto);
        return ResponseEntity.ok().build();
    }


}