package com.example.contracts;




import com.example.DTOs.userdto.RequestUserDTO;
import com.example.DTOs.userdto.ResponseUserDTO;

import java.util.List;


public interface UserService {
    ResponseUserDTO create(RequestUserDTO entity);

    ResponseUserDTO read(Long id);

    ResponseUserDTO update(Long id, RequestUserDTO entity);

    void delete(Long id);

    List<ResponseUserDTO> readAll();
}
