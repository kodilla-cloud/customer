package com.kodilla.customer.mapper;

import com.kodilla.customer.dto.CustomerDto;
import com.kodilla.customer.entity.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {
    public CustomerDto mapToCustomerDto(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .build();
    }
}
