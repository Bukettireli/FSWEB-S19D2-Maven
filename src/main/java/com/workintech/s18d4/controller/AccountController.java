package com.workintech.s18d4.controller;

import com.workintech.s18d4.dto.AccountResponse;
import com.workintech.s18d4.dto.CustomerResponse;
import com.workintech.s18d4.entity.Account;
import com.workintech.s18d4.entity.Customer;
import com.workintech.s18d4.service.AccountService;
import com.workintech.s18d4.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final CustomerService customerService;

    public AccountController(AccountService accountService, CustomerService customerService) {
        this.accountService = accountService;
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> findAll() {
        return ResponseEntity.ok(
                accountService.findAll().stream()
                        .map(this::toResponse)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> find(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(accountService.find(id)));
    }

    @PostMapping("/{customerId}")
    public ResponseEntity<AccountResponse> save(@PathVariable Long customerId, @RequestBody Account account) {
        Customer customer = customerService.find(customerId);
        account.setCustomer(customer);
        return ResponseEntity.ok(toResponse(accountService.save(account)));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<AccountResponse> update(@PathVariable Long customerId, @RequestBody Account account) {
        Customer customer = customerService.find(customerId);
        account.setCustomer(customer);
        return ResponseEntity.ok(toResponse(accountService.save(account)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AccountResponse> delete(@PathVariable Long id) {
        accountService.find(id);
        return ResponseEntity.ok(toResponse(accountService.delete(id)));
    }

    private AccountResponse toResponse(Account account) {
        CustomerResponse customerResponse = new CustomerResponse(
                account.getCustomer().getId(),
                account.getCustomer().getEmail(),
                account.getCustomer().getSalary()
        );
        return new AccountResponse(
                account.getId(),
                account.getAccountName(),
                account.getMoneyAmount(),
                customerResponse
        );
    }
}
