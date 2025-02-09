package com.example.contracts;



import com.example.DTOs.productdto.RequestProductDTO;
import com.example.DTOs.productdto.ResponseProductDTO;

import java.util.List;

public interface ProductService {
    ResponseProductDTO create(RequestProductDTO requestProductDTO);

    ResponseProductDTO read(Long id);

    ResponseProductDTO update(Long id, RequestProductDTO requestProductDTO);

    void delete(Long id);

    List<ResponseProductDTO> readAll();
}
