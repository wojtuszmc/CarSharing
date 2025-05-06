package pl.dare.carsharing.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dare.carsharing.config.UserAuth;
import pl.dare.carsharing.config.UserContext;
import pl.dare.carsharing.model.LoginRequest;
import pl.dare.carsharing.model.UserDto;
import pl.dare.carsharing.model.UsersResponse;
import pl.dare.carsharing.service.UserService;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString

@RestController
@RequestMapping("users")
public class UserResource {
    @Autowired
    private UserService userService;

    @Autowired
    private UserContext userContext;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        userService.registerUser(userDto);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    @UserAuth
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        boolean isValid = userService.validatePassword(loginRequest.getPassword(), loginRequest.getHashedPassword());
        if (isValid) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login succesful");
            response.put("userContextId", userContext.getUserId());
            return ResponseEntity.ok(response.toString());
        } else {
            return ResponseEntity.status(401).body("Invalid credentials.");
        }
    }

    @GetMapping("/me")
    @UserAuth
    public ResponseEntity<?> getCurrentUser() {
        Map<String, Object> response = new HashMap<>();
        response.put("ContextUserId", userContext.getUserId());
        return ResponseEntity.ok(response);
    }
}
