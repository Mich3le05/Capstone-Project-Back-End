package it.epicode.gestione_biscottificio.products;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;

    public List<ProductResponse> findAll() {
        return mapper.toDto(repository.findAll());
    }

    public ProductResponse findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + id));
    }

    public ProductResponse save(@Valid ProductRequest request) {
        Product product = mapper.toEntity(request);
        return mapper.toDto(repository.save(product));
    }

    public ProductResponse update(Long id, @Valid ProductRequest request) {
        Product entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + id));
        mapper.updateEntity(entity, request);
        return mapper.toDto(repository.save(entity));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Product not found with id " + id);
        }
        repository.deleteById(id);
    }
}
