package com.leumas.account.service;

import com.leumas.account.client.CustomerClient;
import com.leumas.account.client.dto.CustomerResponse;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerGateway {
    private final CustomerClient customerClient;

    @CircuitBreaker(name = "customerFinding", fallbackMethod = "fallbackFindCustomer")
    @Retry(name = "customerFinding")
    public CustomerResponse findCustomer(String id) {
        try{
            return customerClient.getCustomer(id);
        } catch (FeignException.NotFound e) {
            throw new EntityNotFoundException("Customer not found");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private CustomerResponse fallbackFindCustomer(String id, Throwable t) {
        log.info("called fallbackFindCustomer");
        throw new ResponseStatusException(
                HttpStatus.SERVICE_UNAVAILABLE,
                "Customer service unavailable: " + (t != null ? t.getMessage() : "unknown error"),
                t
        );
    }
}
