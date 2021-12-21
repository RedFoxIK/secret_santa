package com.kalashnyk.santa.service;

import com.kalashnyk.santa.model.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;


@Slf4j
@Service
public class EmailService {

    private static final String SUBJECT = "SECRET SANTA!!!";
    private static final String IMAGE_ID = "1";
    private static final String HTML_MESSAGE = "<html>Привет %s!<br>" +
            "В этом году я не расчитал свои силы и мне очень нужна твоя помощь.<br>" +
            "Пожалуйста подбери подарок для этого человечка: <b><u>%s %s</u></b><br>" +
            "<img src=\"cid:" + IMAGE_ID + "\" </html><br>" +
            "И я побеспокоюсь о тебе <br><br><br>" +
            "Твой Санта)";


    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Value("${email.sender}")
    private String sender;

    public void sendEmail(Player santa, Player recipient) {
        try {
            MimeMessage mimeMessage = createMimeMessage(santa, recipient);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | URISyntaxException e) {
            log.error("Email to {} was not sent due to reason:", santa.getEmail(), e);
        }
    }

    private MimeMessage createMimeMessage(Player santa, Player recipient) throws MessagingException, URISyntaxException {
        MimeMessage msg = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(santa.getEmail());
        helper.setSubject(SUBJECT);

        String htmlMessage = String.format(HTML_MESSAGE, santa.getFirstName(), recipient.getFirstName(), recipient.getLastName());
        helper.setText(htmlMessage, true);
        helper.addInline(IMAGE_ID,  new FileSystemResource(getImage()));

        return msg;
    }

    private File getImage() throws URISyntaxException {
        URL resource = getClass().getClassLoader().getResource("images/present.png");
        if (resource == null) {
            throw new IllegalArgumentException("Image not found!");
        }
        return new File(resource.toURI());
    }
}
