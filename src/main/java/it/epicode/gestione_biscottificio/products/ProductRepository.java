package it.epicode.gestione_biscottificio.products;

import it.epicode.gestione_biscottificio.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByTitleContainingIgnoreCase(String keyword);
}
