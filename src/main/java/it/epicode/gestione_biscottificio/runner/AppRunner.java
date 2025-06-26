package it.epicode.gestione_biscottificio.runner;

import it.epicode.gestione_biscottificio.category.Category;
import it.epicode.gestione_biscottificio.category.CategoryService;
import it.epicode.gestione_biscottificio.products.Product;
import it.epicode.gestione_biscottificio.products.ProductService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component // Attivato per l'esecuzione all'avvio
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
            System.out.println("✅ Nessuna categoria trovata. Creazione categorie iniziali...");

            Category tipiciCastrensi = new Category("Tipici Castrensi");
            Category pasticceria = new Category("Pasticceria");
            Category classici = new Category("Classici");

            categoryService.saveCategory(tipiciCastrensi);
            categoryService.saveCategory(pasticceria);
            categoryService.saveCategory(classici);

            System.out.println("✅ Categorie create con successo.");

            /*
            if (productService.getAllProducts().isEmpty()) {
                System.out.println("✅ Nessun prodotto trovato. Creazione prodotti iniziali...");

                productService.saveProduct(createProduct("Biscotti Castriciani", "https://res.cloudinary.com/dn9wsjpqo/image/upload/v1741441716/Products/2.webp.webp", 3.5, "Biscotti Castriciani morbidi", tipiciCastrensi));
                productService.saveProduct(createProduct("Cheesecake", "https://image.url/6.jpg", 9.0, "Cheesecake ai frutti di bosco", pasticceria));
                productService.saveProduct(createProduct("Crostata di Nutella", "https://image.url/8.jpg", 7.5, "Crostata con crema di nocciole", classici));

                System.out.println("✅ Prodotti creati con successo.");
            }
            */
        } else {
            System.out.println("ℹ️ Le categorie esistono già. Nessuna modifica eseguita.");
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
