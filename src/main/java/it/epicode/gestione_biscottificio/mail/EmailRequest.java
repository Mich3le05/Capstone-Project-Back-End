package it.epicode.gestione_biscottificio.mail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRequest {
    private String paymentId;
    private String email;
}