package com.capgemini.wsb.fitnesstracker.mail.internal;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.capgemini.wsb.fitnesstracker.mail.api.EmailDto;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailSender;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderImpl implements EmailSender {

    private final JavaMailSender mailSender;

    @Override
    public void send(EmailDto email) {
        // Wysyłanie emaila
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(email.toAddress());
            helper.setSubject(email.subject());
            helper.setText(email.content(), true);

            mailSender.send(mimeMessage);
            log.info("Email wysłany do: {}", email.toAddress());
        } catch (MessagingException e) {
            log.error("Błąd podczas wysyłania e-maila do: {}", email.toAddress(), e);
        }
    }
    
}
