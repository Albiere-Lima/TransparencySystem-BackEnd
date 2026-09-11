package br.ufpb.dcx.lima.albiere.OF_Web.controllers;

import br.ufpb.dcx.lima.albiere.OF_Web.models.User;
import br.ufpb.dcx.lima.albiere.OF_Web.services.AuthService;
import br.ufpb.dcx.lima.albiere.OF_Web.utils.JwtUtil;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

        @PostMapping("/google")
        public ResponseEntity<?> googleAuth(@RequestBody GoogleAuthRequest request) {
            try {
                User user = authService.processGoogleLogin(request.getGoogleToken());
                String appToken = jwtUtil.generateToken(user.getEmail(), user.getRole());
                Map<String, Object> response = new HashMap<>();
                response.put("token", appToken);
                response.put("user", user);

                return ResponseEntity.ok(response);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
            }
        }

}

@Data
class GoogleAuthRequest {
    private String googleToken;
}