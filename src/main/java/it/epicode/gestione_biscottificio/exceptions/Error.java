package it.epicode.gestione_biscottificio.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Error {
    private String message;
    private String details;
    private int status;
}
