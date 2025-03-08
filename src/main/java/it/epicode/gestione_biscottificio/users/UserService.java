package it.epicode.gestione_biscottificio.users;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserResponse createUser(UserRequest userRequest) {
        User user = new User();
        BeanUtils.copyProperties(userRequest, user);

        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        user.setRole(UserRole.valueOf(userRequest.getRole()));

        userRepository.save(user);
        return new UserResponse(user.getId(), user.getName(), user.getSurname(), user.getUsername(), user.getEmail(), user.getRole().toString());
    }

    public UserResponse findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return new UserResponse(user.getId(), user.getName(), user.getSurname(), user.getUsername(), user.getEmail(), user.getRole().toString());
    }
}
