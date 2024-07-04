package com.webwizard.notificationservice.service;

import com.webwizard.notificationservice.exception.EmailServiceException;
import com.webwizard.notificationservice.model.Message;
import com.webwizard.notificationservice.repository.MessageRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@Slf4j
@EnableScheduling
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final MessageRepository messageRepository;

    @Scheduled(fixedDelay = 1000)
    @Transactional
    protected void checkNewMessage() {
        Optional<Message> message = messageRepository.findFirstBySendFalse();
        if (message.isPresent()) {
            log.info("New message in queue found!");
            log.info("Sending message...");
            Message messageToSend = message.get();
            messageToSend.setSend(true);
            sendMessage(messageToSend);
            messageRepository.save(messageToSend);
            log.info("Message sent successfully!");
        }
    }

    private void sendMessage(Message message) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
            helper.setTo(message.getRecipient());
            helper.setSubject("Successful Creation of Job Offer");
            Context context = setUpContext(message);
            String htmlContent = templateEngine.process("email-template", context);
            helper.setText(htmlContent, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new EmailServiceException("An error occurred while sending the email", e.toString());
        }
    }

    private Context setUpContext(Message message) {
        Context context = new Context();
        context.setVariable("createdDate", message.getCreatedDate()
                .format(DateTimeFormatter
                        .ofPattern("yyyy-MM-dd HH:mm:ss")
                )
        );
        context.setVariable("jobLocation", message.getLocation());
        context.setVariable("jobTitle", message.getTitle());
        context.setVariable("contactName", message.getContactName());
        return context;
    }
}
