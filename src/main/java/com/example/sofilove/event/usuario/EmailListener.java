package com.example.sofilove.event.usuario;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class EmailListener {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public EmailListener(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    @EventListener
    public void sendEmail(CreateAccountEvent event) throws MessagingException {
        // Obtener el usuario desde el evento
        String email = event.getEmail();
        String nombre = event.getName();

        // Preparar el contexto para Thymeleaf
        Context context = new Context();
        context.setVariable("nombre", nombre);

        // Procesar la plantilla HTML usando Thymeleaf
        String contenidoHtml = templateEngine.process("welcome-email", context);

        // Crear el mensaje MIME para enviar HTML
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, "utf-8");

        // Configurar los detalles del correo
        helper.setTo(email);
        helper.setSubject("¡Bienvenido!");
        helper.setText(contenidoHtml, true);

        // Enviar el correo
        mailSender.send(mensaje);
    }
}
