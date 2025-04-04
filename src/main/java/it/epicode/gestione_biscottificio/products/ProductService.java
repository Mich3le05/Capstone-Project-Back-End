package it.epicode.gestione_biscottificio.products;

import it.epicode.gestione_biscottificio.category.Category;
import it.epicode.gestione_biscottificio.category.CategoryRepository;
import it.epicode.gestione_biscottificio.response.CreateResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Validated
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductResponse productResponseFromEntity(Product product) {
        ProductResponse response = new ProductResponse();
        BeanUtils.copyProperties(product, response);
        return response;
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream().map(this::productResponseFromEntity).toList();
    }

    public ProductDetailResponse findProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        ProductDetailResponse response = new ProductDetailResponse();
        BeanUtils.copyProperties(product, response);
        return response;
    }

    public CreateResponse save(ProductRequest productRequest) {
        Product product = new Product();
        BeanUtils.copyProperties(productRequest, product);

        // ✅ Corretto il riferimento al repository
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        product.setCategory(category);

        if (product.getSku() == null || product.getSku().isEmpty()) {
            product.setSku(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }

        productRepository.save(product);
        return new CreateResponse(product.getId());
    }

    public ProductDetailResponse modify(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        BeanUtils.copyProperties(productRequest, product);

        // ✅ Controllo per aggiornare la categoria
        if (productRequest.getCategoryId() != null) {
            Category category = categoryRepository.findById(productRequest.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found"));
            product.setCategory(category);
        }

        productRepository.save(product);
        ProductDetailResponse response = new ProductDetailResponse();
        BeanUtils.copyProperties(product, response);
        return response;
    }

    public ProductDetailResponse modifyProductDetails(Long id, ProductPatchRequest productPatchRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        if (productPatchRequest.getTitle() != null) product.setTitle(productPatchRequest.getTitle());
        if (productPatchRequest.getImage() != null) product.setImage(productPatchRequest.getImage());
        if (productPatchRequest.getPrice() != null) product.setPrice(productPatchRequest.getPrice());
        if (productPatchRequest.getDescription() != null) product.setDescription(productPatchRequest.getDescription());

        if (productPatchRequest.getCategoryId() != null) {
            Category category = categoryRepository.findById(productPatchRequest.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found"));
            product.setCategory(category);
        }

        productRepository.save(product);
        ProductDetailResponse response = new ProductDetailResponse();
        BeanUtils.copyProperties(product, response);
        return response;
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product not found");
        }
        productRepository.deleteById(id);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<ProductResponse> findByCategory(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);

        if (products.isEmpty()) {
            throw new EntityNotFoundException("Nessun prodotto trovato per questa categoria");
        }

        return products.stream()
                .map(this::productResponseFromEntity)
                .toList();
    }

}
