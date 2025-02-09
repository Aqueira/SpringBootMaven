package com.example.customer.controller;
import com.example.DTOs.customerdto.CurrentDTO;
import com.example.DTOs.customerdto.RequestCustomerDTO;
import com.example.DTOs.customerdto.ResponseCustomerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.example.contracts.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PreAuthorize("hasRole('USER') and #id == authentication.principal.id or hasAuthority('management::read')")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseCustomerDTO> read(@PathVariable("id") Long id) {
        return ResponseEntity.ok(customerService.read(id));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('management::read')")
    public ResponseEntity<List<ResponseCustomerDTO>> readAll() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') and #id == authentication.principal.id or hasAuthority('management::update')")
    public ResponseEntity<ResponseCustomerDTO> update(@PathVariable Long id, @RequestBody RequestCustomerDTO requestCustomerDTO) {
        return ResponseEntity.ok(customerService.update(id, requestCustomerDTO));
    }

    @GetMapping("/current")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CurrentDTO> current(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(customerService.getCurrent(userDetails));
    }
}
