package pl.dare.carsharing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.dare.carsharing.jpa.AppUser;
import pl.dare.carsharing.model.UserDto;
import pl.dare.carsharing.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;


    public void registerUser(UserDto userDto) {
        String hashedPassword = passwordEncoder.encode(userDto.getPassword());

        AppUser user = new AppUser();
        user.setEmail(userDto.getEmail());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }

    public boolean validatePassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
}