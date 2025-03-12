package it.epicode.gestione_biscottificio.runner;

import it.epicode.gestione_biscottificio.category.Category;
import it.epicode.gestione_biscottificio.category.CategoryService;
import it.epicode.gestione_biscottificio.products.Product;
import it.epicode.gestione_biscottificio.products.ProductService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AppRunner implements ApplicationRunner {

    private final CategoryService categoryService;
    private final ProductService productService;

    public AppRunner(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @Override
    public void run(ApplicationArguments args) {

    // Aggiunge una categoria se non esiste
        if (categoryService.getAllCategories().isEmpty()) {
            Category category = new Category("Biscotti");
            categoryService.saveCategory(category);
        }

        // Aggiunge un prodotto se non esiste
        Category category = categoryService.getAllCategories().get(0); // Supponiamo di usare la prima categoria

        Product product = new Product();
        product.setTitle("Biscotti Castriciani");
        product.setImage("https://res.cloudinary.com/dn9wsjpqo/image/upload/v1741441716/Products/2.webp.webp");
        product.setPrice(2.5);
        product.setDescription("Biscotti Castriciani morbidi");
        product.setCategory(category);
        product.setStock(100);

        // Genera SKU automaticamente
        if (product.getSku() == null || product.getSku().isEmpty()) {
            product.setSku(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }

        productService.saveProduct(product);
    }
}
