package pl.dare.carsharing.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.dare.carsharing.model.LoginRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        // Tworzenie tokenu do uwierzytelnienia
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(), loginRequest.getPassword()));

        // Ustawienie kontekstu bezpieczeństwa
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generowanie tokenu JWT
        String jwt = jwtTokenUtil.generateJwtToken(loginRequest.getEmail());

        return ResponseEntity.ok("Bearer " + jwt);
    }
}

