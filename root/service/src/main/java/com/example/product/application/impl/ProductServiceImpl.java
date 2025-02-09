package com.example.product.application.impl;

import com.example.DTOs.productdto.RequestProductDTO;
import com.example.DTOs.productdto.ResponseProductDTO;
import com.example.contracts.ProductService;
import com.example.domains.Product;
import com.example.exceptions.NotFoundException;
import com.example.product.data.ProductRepository;
import lombok.RequiredArgsConstructor;
import com.example.mappers.productMappers.RequestProductMapper;
import com.example.mappers.productMappers.ResponseProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ResponseProductMapper responseProductMapper;
    private final RequestProductMapper requestProductMapper;

    @Override
    @Transactional
    public ResponseProductDTO create(RequestProductDTO requestProductDTO) {
        return responseProductMapper.toDTO(productRepository.save(requestProductMapper.toEntity(requestProductDTO)));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseProductDTO read(Long id) {
        return productRepository.findById(id)
                .map(responseProductMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }

    @Override
    @Transactional
    public ResponseProductDTO update(Long id, RequestProductDTO requestProductDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        product.setProductName(requestProductDTO.productName());
        product.setPrice(requestProductDTO.price());
        productRepository.save(product);

        return responseProductMapper.toDTO(product);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseProductDTO> readAll() {
        return responseProductMapper.toDTOs(productRepository.findAll());
    }
}
