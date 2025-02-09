package com.example.contracts;


import com.example.DTOs.orderdto.RequestOrderDTO;
import com.example.DTOs.orderdto.ResponseOrderDTO;

import java.util.List;


public interface OrderService {
    ResponseOrderDTO create(RequestOrderDTO entity);

    ResponseOrderDTO read(Long id);

    ResponseOrderDTO update(Long id, RequestOrderDTO entity);

    void delete(Long id);

    List<ResponseOrderDTO> findAll();

}
