package it.epicode.gestione_biscottificio.products;

import it.epicode.gestione_biscottificio.category.Category;
import it.epicode.gestione_biscottificio.category.CategoryRepository;
import it.epicode.gestione_biscottificio.response.CreateResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class ProductController {

    private final ProductRepository productRepository; // Aggiungi iniezione del repository Product
    private final CategoryRepository categoryRepository; // Aggiungi iniezione del repository Category
    private final ProductService productService;

    @GetMapping("/category/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<ProductResponse> findByCategory(@PathVariable Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);

        if (products.isEmpty()) {
            throw new EntityNotFoundException("Nessun prodotto trovato per questa categoria");
        }

        return products.stream().map(this::productResponseFromEntity).toList();
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<ProductResponse> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public ProductDetailResponse findProductById(@PathVariable Long id) {
        return productService.findProductById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public CreateResponse save(@RequestBody ProductRequest productRequest) {
        return productService.save(productRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ProductDetailResponse modify(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        return productService.modify(id, productRequest);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ProductDetailResponse modifyPatch(@PathVariable Long id, @RequestBody ProductPatchRequest productPatchRequest) {
        return productService.modifyProductDetails(id, productPatchRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    // Metodo per mappare un Product in ProductResponse
    private ProductResponse productResponseFromEntity(Product product) {
        return new ProductResponse(product.getId(), product.getTitle(), product.getImage(), product.getPrice(), product.getDescription());
    }
}
