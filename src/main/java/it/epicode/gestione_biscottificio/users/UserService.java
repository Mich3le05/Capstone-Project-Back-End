package it.epicode.gestione_biscottificio.users;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserResponse createUser(UserRequest userRequest) {
        User user = new User();
        BeanUtils.copyProperties(userRequest, user);

        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(UserRole.valueOf(userRequest.getRole().toUpperCase()));

        userRepository.save(user);
        return new UserResponse(user.getId(), user.getName(), user.getSurname(), user.getUsername(), user.getEmail(), user.getRole().toString());
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Cripta la password
        return userRepository.save(user);
    }
}
