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

        if (categoryService.getAllCategories().isEmpty()) {
            Category tipiciCastrensi = new Category("Tipici Castrensi");
            Category pasticceria = new Category("Pasticceria");
            Category classici = new Category("Classici");

            categoryService.saveCategory(tipiciCastrensi);
            categoryService.saveCategory(pasticceria);
            categoryService.saveCategory(classici);

            // Prodotti per tipici Castrensi
            productService.saveProduct(createProduct("Biscotti Castriciani", "https://res.cloudinary.com/dn9wsjpqo/image/upload/v1741441716/Products/2.webp.webp", 3.5, "Biscotti Castriciani morbidi", tipiciCastrensi));
            productService.saveProduct(createProduct("Occhi di Bue", "https://image.url/2.jpg", 2.8, "Occhi di Bue con marmellata", tipiciCastrensi));
            productService.saveProduct(createProduct("Biscotti Castriciani", "https://image.url/3.jpg", 2.5, "Biscotti Castriciani morbidi", tipiciCastrensi));

            // Prodotti per pasticceria
            productService.saveProduct(createProduct("Torta di Mele", "https://image.url/4.jpg", 8.0, "Torta di mele classica", pasticceria));
            productService.saveProduct(createProduct("Tiramisù", "https://image.url/5.jpg", 10.0, "Tiramisù della casa", pasticceria));
            productService.saveProduct(createProduct("Cheesecake", "https://image.url/6.jpg", 9.0, "Cheesecake ai frutti di bosco", pasticceria));

            // Prodotti per classici
            productService.saveProduct(createProduct("Crostata di Albicocche", "https://image.url/7.jpg", 7.0, "Crostata con marmellata di albicocche", classici));
            productService.saveProduct(createProduct("Crostata di Nutella", "https://image.url/8.jpg", 7.5, "Crostata con crema di nocciole", classici));

        }
    }

    private Product createProduct(String title, String image, double price, String desc, Category category) {
        Product p = new Product();
        p.setTitle(title);
        p.setImage(image);
        p.setPrice(price);
        p.setDescription(desc);
        p.setCategory(category);
        p.setStock(100);
        p.setSku(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        return p;
    }
}
