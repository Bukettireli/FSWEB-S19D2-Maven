package com.workintech.s18d4.controller;

import com.workintech.s18d4.dto.CustomerResponse;
import com.workintech.s18d4.entity.Customer;
import com.workintech.s18d4.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAll() {
        return ResponseEntity.ok(
                customerService.findAll().stream()
                        .map(this::toResponse)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> find(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(customerService.find(id)));
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> save(@RequestBody Customer customer) {
        return ResponseEntity.ok(toResponse(customerService.save(customer)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(@PathVariable Long id, @RequestBody Customer customer) {
        customer.setId(id);
        return ResponseEntity.ok(toResponse(customerService.save(customer)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerResponse> delete(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(customerService.delete(id)));
    }

    private CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(customer.getId(), customer.getEmail(), customer.getSalary());
    }
}
