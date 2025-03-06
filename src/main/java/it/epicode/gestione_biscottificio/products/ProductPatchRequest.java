package it.epicode.gestione_biscottificio.products;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductPatchRequest {
    private String title;
    private String image;
    private Double price;
    private String description;
}