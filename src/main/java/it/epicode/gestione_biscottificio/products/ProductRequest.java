package it.epicode.gestione_biscottificio.products;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotBlank(message = "Il titolo non può essere vuoto")
    private String title;

    @NotBlank(message = "L'immagine non può essere vuota")
    private String image;

    @NotNull(message = "Il prezzo non può essere nullo")
    private Double price;

    @NotBlank(message = "La descrizione non può essere vuota")
    private String description;
}