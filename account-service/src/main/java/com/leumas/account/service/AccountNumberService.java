package com.leumas.account.service;

import com.leumas.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountNumberService {
    private final AccountRepository accountRepository;
    private final Random random = new Random();

    public String generateAccountNumber() {
        String number;
        do{
            number = String.format("%08d", random.nextInt(100_000_000));
        }while (accountRepository.existsByAccount(number));
        return number;
    }
}
