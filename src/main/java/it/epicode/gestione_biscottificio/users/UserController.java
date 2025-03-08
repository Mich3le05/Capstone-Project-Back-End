package it.epicode.gestione_biscottificio.users;

import it.epicode.gestione_biscottificio.response.CreateResponse;
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

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public UserResponse findByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }
}
