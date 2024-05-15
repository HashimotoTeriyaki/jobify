package com.webwizard.jobofferservice.repository;

import com.webwizard.jobofferservice.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    Currency findFirstByName(String name);
}
