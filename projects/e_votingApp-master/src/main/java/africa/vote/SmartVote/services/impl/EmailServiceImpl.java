package africa.vote.SmartVote.services.impl;

import africa.vote.SmartVote.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    @Override
    public void sendEmail(String receiverMail, String email) {
        try {
            MimeMessage mailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mailMessage, "utf-8");
            mimeMessageHelper.setSubject("[Smart System] Email Address Confirmation...");
            mimeMessageHelper.setTo(receiverMail);
            mimeMessageHelper.setFrom("adulojujames457@gmail.com");
            mimeMessageHelper.setText(email, true);
            javaMailSender.send(mailMessage);
        } catch (MessagingException ex) {
            log.info("Problem: " + ex.getMessage());
            throw new RuntimeException();
        } catch (MailException ex) {
            log.info("Problem: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
}