package it.epicode.gestione_biscottificio.runner;

import it.epicode.gestione_biscottificio.customers.Customer;
import it.epicode.gestione_biscottificio.customers.CustomerService;
import it.epicode.gestione_biscottificio.category.Category;
import it.epicode.gestione_biscottificio.category.CategoryService;
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

    @Override
    public void run(ApplicationArguments args) throws Exception {

        customerService.saveCustomer(new Customer("Mario Rossi", "mario@mail.com", "1234567890", "Via Roma 1"));
        customerService.saveCustomer(new Customer("Giovanni Bianchi", "giovanni@mail.com", "0987654321", "Via Milano 2"));

        categoryService.saveCategory(new Category("Dolci"));
        categoryService.saveCategory(new Category("Salati"));
    }
}
