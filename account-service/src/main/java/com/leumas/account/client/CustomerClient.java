package com.leumas.account.client;

import com.leumas.account.client.dto.CustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service", url = "${customer.service.url}")
public interface CustomerClient {
    @GetMapping("/{id}")
    CustomerResponse getCustomer(@PathVariable("id") String id);
}
