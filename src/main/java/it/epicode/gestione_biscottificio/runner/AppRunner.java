package it.epicode.gestione_biscottificio.runner;

import it.epicode.gestione_biscottificio.customers.Customer;
import it.epicode.gestione_biscottificio.customers.CustomerService;
import it.epicode.gestione_biscottificio.category.Category;
import it.epicode.gestione_biscottificio.category.CategoryService;
import it.epicode.gestione_biscottificio.products.Product;
import it.epicode.gestione_biscottificio.products.ProductService;
import it.epicode.gestione_biscottificio.users.User;
import it.epicode.gestione_biscottificio.users.UserRole;
import it.epicode.gestione_biscottificio.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements ApplicationRunner {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Creazione admin solo se non esiste
        if (userService.findByEmail("admin@mail.com").isEmpty()) { // Corretto da isPresent() a isEmpty()
            User admin = new User("Admin", "User", "admin", "password123", "admin@mail.com", UserRole.ADMIN);
            userService.saveUser(admin);
        }

        // Creazione clienti
        customerService.saveCustomer(new Customer("Mario Rossi", "mario@mail.com", "1234567890", "Via Roma 1"));
        customerService.saveCustomer(new Customer("Giovanni Bianchi", "giovanni@mail.com", "0987654321", "Via Milano 2"));

        // Creazione categorie
        Category dolci = new Category("Dolci");
        Category salati = new Category("Salati");
        categoryService.saveCategory(dolci);
        categoryService.saveCategory(salati);

        // Creazione prodotti con immagini
        Product product1 = new Product(null, "SKU001", "Biscotti al cioccolato", "https://example.com/images/biscotti-cioccolato.jpg", 5.99, "Deliziosi biscotti al cioccolato", dolci, 100);
        Product product2 = new Product(null, "SKU002", "Taralli salati", "https://example.com/images/taralli-salati.jpg", 3.49, "Squisiti taralli salati", salati, 200);

        productService.saveProduct(product1);
        productService.saveProduct(product2);
    }
}
