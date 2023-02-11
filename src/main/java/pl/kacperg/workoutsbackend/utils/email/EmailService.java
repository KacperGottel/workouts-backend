package pl.kacperg.workoutsbackend.utils.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import pl.kacperg.workoutsbackend.user.dto.UserRegisterDTO;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String email;

    public void sendInitEmail(@Valid UserRegisterDTO registerDTO, String token) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        Context context = new Context();
        HashMap<String, Object> variables = new HashMap<>();
        String link = "link.pl/" + token;
        variables.put("name", registerDTO.username);
        variables.put("link", link);
        context.setVariables(variables);
        helper.setFrom(email);
        helper.setTo(registerDTO.email);
        helper.setSubject("INITIAL MAIL WITH TOKEN");
        String html = templateEngine.process("init-email-token.html", context);
        helper.setText(html, true);

        log.info("Sending email: {} with html body: {}", email, html);
        emailSender.send(message);

    }
}
