package ad.example.microfinance.service.impl;

import ad.example.microfinance.entity.User;
import ad.example.microfinance.exception.ResourceNotFoundException;
import ad.example.microfinance.repository.UserRepository;
import ad.example.microfinance.security.JwtUtil;
import ad.example.microfinance.service.AuthService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public AuthServiceImpl(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Map<String, Object> login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid username or password"));

        // Match password with BCrypt, or check plain text fallback for initial seed
        boolean matches = passwordEncoder.matches(password, user.getPassword()) || password.equals(user.getPassword());
        if (!matches) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole(), user.getId());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("refreshToken", refreshToken);
        response.put("username", user.getUsername());
        response.put("fullName", user.getFullName());
        response.put("role", user.getRole());
        response.put("branchId", user.getBranchId());
        response.put("userId", user.getId());
        return response;
    }

    @Override
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User getCurrentUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
