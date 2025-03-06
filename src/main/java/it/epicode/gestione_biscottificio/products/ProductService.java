package it.epicode.gestione_biscottificio.products;

import it.epicode.gestione_biscottificio.response.CreateResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse productResponseFromEntity(Product product) {
        ProductResponse response = new ProductResponse();
        BeanUtils.copyProperties(product, response);
        return response;
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream().map(this::productResponseFromEntity).toList();
    }

    public ProductDetailResponse findProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        ProductDetailResponse response = new ProductDetailResponse();
        BeanUtils.copyProperties(product, response);
        return response;
    }

    public CreateResponse save(ProductRequest productRequest) {
        Product product = new Product();
        BeanUtils.copyProperties(productRequest, product);
        productRepository.save(product);
        return new CreateResponse(product.getId());
    }

    public ProductDetailResponse modify(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        BeanUtils.copyProperties(productRequest, product);
        productRepository.save(product);
        ProductDetailResponse response = new ProductDetailResponse();
        BeanUtils.copyProperties(product, response);
        return response;
    }

    public ProductDetailResponse modifyProductDetails(Long id, ProductPatchRequest productPatchRequest) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        if (productPatchRequest.getTitle() != null) product.setTitle(productPatchRequest.getTitle());
        if (productPatchRequest.getImage() != null) product.setImage(productPatchRequest.getImage());
        if (productPatchRequest.getPrice() != null) product.setPrice(productPatchRequest.getPrice());
        if (productPatchRequest.getDescription() != null) product.setDescription(productPatchRequest.getDescription());
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
}

