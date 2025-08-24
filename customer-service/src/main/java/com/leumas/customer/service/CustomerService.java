package com.leumas.customer.service;

import com.leumas.customer.kafka.CustomerEventProducer;
import com.leumas.customer.model.Customer;
import com.leumas.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;
    private final CustomerEventProducer kafkaProducer;

    public List<Customer> findAll() {
        return repository.findAll();
    }

    public Optional<Customer> findById(String id) {
        return repository.findById(id);
    }

    public Customer save(Customer customer) {
        Customer newCustomer = repository.save(customer);
        kafkaProducer.sendCustomerCreatedEvent(newCustomer.getId());
        return newCustomer;
    }

    public Optional<Customer> update(Customer customer, String id) {
        return findById(id).map(oldCustomer -> {
            customer.setId(oldCustomer.getId());
            Customer updated = repository.save(customer);
            kafkaProducer.sendCustomerUpdatedEvent(updated.getId());
            return updated;
        });

    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
