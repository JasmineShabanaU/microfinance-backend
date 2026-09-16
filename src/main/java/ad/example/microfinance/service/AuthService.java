package ad.example.microfinance.service;

import ad.example.microfinance.entity.User;
import java.util.List;
import java.util.Map;

public interface AuthService {
    Map<String, Object> login(String username, String password);
    User register(User user);
    User getCurrentUser(String username);
    List<User> getAllUsers();
}
