package com.webwizard.jobofferservice.repository;

import com.webwizard.jobofferservice.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
    Optional<Contact> findAllByPhoneAndEmailAndName(String phone, String email, String name);
}
