package it.epicode.gestione_biscottificio.products;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category);
    List<Product> findByTitleContainingIgnoreCase(String keyword);
}
