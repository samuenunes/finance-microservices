package com.leumas.account.repository;

import com.leumas.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>
{
    public boolean existsByAccount(String accountNumber);
}
