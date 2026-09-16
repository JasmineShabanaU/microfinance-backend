package ad.example.microfinance.controller;

import ad.example.microfinance.entity.User;
import ad.example.microfinance.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping({"/api/v1/auth", "/api/auth"})
@CrossOrigin(origins = {"http://localhost:8081", "http://127.0.0.1:8081"})
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        return ResponseEntity.ok(authService.login(username, password));
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(authService.register(user));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(authService.getAllUsers());
    }

    @GetMapping("/me")
    public ResponseEntity<User> getMe(@RequestParam String username) {
        return ResponseEntity.ok(authService.getCurrentUser(username));
    }
}
