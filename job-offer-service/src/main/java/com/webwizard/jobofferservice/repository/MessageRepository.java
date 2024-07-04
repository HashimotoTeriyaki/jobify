package com.webwizard.jobofferservice.repository;

import com.webwizard.jobofferservice.model.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}
