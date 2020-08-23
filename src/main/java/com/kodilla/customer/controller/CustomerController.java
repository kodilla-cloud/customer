package com.kodilla.customer.controller;

import com.kodilla.customer.controller.response.GetCustomerProductsResponse;
import com.kodilla.customer.controller.response.GetCustomerResponse;
import com.kodilla.customer.dto.AccountDto;
import com.kodilla.customer.dto.CustomerDto;
import com.kodilla.customer.service.CustomerService;
import com.kodilla.customer.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/customer", produces = { MediaType.APPLICATION_JSON_VALUE })
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final ProductService productService;

    @GetMapping("/{customerId}")
    public GetCustomerResponse getCustomer(@PathVariable Long customerId) {
        return customerService.findCustomer(customerId)
                .map(GetCustomerResponse::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{customerId}/products")
    public GetCustomerProductsResponse getCustomerProducts(@PathVariable Long customerId) {
        CustomerDto customerDto = customerService.findCustomer(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        List<AccountDto> customerAccounts = productService.findCustomerAccounts(customerId);

        return GetCustomerProductsResponse.builder()
                .customerId(customerDto.getId())
                .fullName(customerDto.getFirstName() + " " + customerDto.getLastName())
                .accounts(customerAccounts)
                .build();
    }
}
