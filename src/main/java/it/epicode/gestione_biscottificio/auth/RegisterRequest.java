package it.epicode.gestione_biscottificio.auth;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
}
