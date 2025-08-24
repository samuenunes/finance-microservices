package com.leumas.customer.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "customers")
public class Customer {
    @Id
    private String id;

    private String name;
    private String email;
    private String phone;
    private String cpf;
    private LocalDate birthDate;

    private Address address;
}
