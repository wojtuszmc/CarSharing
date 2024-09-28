package pl.dare.carsharing.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dare.carsharing.model.LoginRequest;
import pl.dare.carsharing.model.UserDto;
import pl.dare.carsharing.model.UsersResponse;
import pl.dare.carsharing.service.UserService;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString

@RestController
@RequestMapping("users")
public class UserResource {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        userService.registerUser(userDto);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        boolean isValid = userService.validatePassword(loginRequest.getPassword(), loginRequest.getHashedPassword());
        if (isValid) {
            return ResponseEntity.ok("Login successful.");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials.");
        }
    }
}
