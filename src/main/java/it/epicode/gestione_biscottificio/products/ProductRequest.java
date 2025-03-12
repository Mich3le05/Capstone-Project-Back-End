package it.epicode.gestione_biscottificio.products;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @Positive(message = "Il prezzo deve essere positivo")
    private Double price;

    @NotBlank(message = "La descrizione non può essere vuota")
    private String description;

    @NotNull(message = "L'ID della categoria non può essere nullo")
    private Long categoryId;

    @NotNull(message = "La quantità non può essere nulla")
    @Min(value = 0, message = "La quantità non può essere negativa")
    private Integer stock;
}

