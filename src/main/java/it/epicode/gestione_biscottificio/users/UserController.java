package it.epicode.gestione_biscottificio.users;

import it.epicode.gestione_biscottificio.response.CreateResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public CreateResponse create(@RequestBody UserRequest userRequest) {
        UserResponse response = userService.createUser(userRequest);
        return new CreateResponse(response.getId());
    }

    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public UserResponse findByEmail(@PathVariable String email) {
        return userService.findByEmail(email)
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getSurname(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRole().toString()
                ))
                .orElseThrow(() -> new EntityNotFoundException("Utente con email " + email + " non trovato"));
    }

}
