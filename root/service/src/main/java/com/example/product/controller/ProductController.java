package com.example.product.controller;

import com.example.DTOs.productdto.RequestProductDTO;
import com.example.DTOs.productdto.ResponseProductDTO;
import com.example.contracts.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasAuthority('management::write')")
    public ResponseEntity<ResponseProductDTO> create(@RequestBody RequestProductDTO requestProductDTO) {
        return ResponseEntity.ok(productService.create(requestProductDTO));
    }

    @PreAuthorize("hasAuthority('management::read')")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseProductDTO> read(@PathVariable Long id) {
        return ResponseEntity.ok(productService.read(id));
    }

    @PreAuthorize("hasAuthority('management::update')")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseProductDTO> update(@PathVariable Long id, @RequestBody RequestProductDTO requestProductDTO) {
        return ResponseEntity.ok(productService.update(id, requestProductDTO));
    }

    @PreAuthorize("hasAuthority('management::delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<ResponseProductDTO>> readAll() {
        return ResponseEntity.ok(productService.readAll());
    }
}
