package com.example.sofilove.event.Pedido;

import com.example.sofilove.Pedido.domain.Pedido;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.thymeleaf.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

@Component
public class PedidoEventListener {



    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Async
    @EventListener
    public void onPedidoCreado(PedidoCreatedEvent event) throws MessagingException {
        Pedido pedido = event.getPedido();
        sendEmail(pedido.getEmail(), "Pedido Creado", "pedido-request-received", pedido);
    }

    @Async
    @EventListener
    public void onPedidoEnviado(PedidoEnviadoEvent event) throws MessagingException {
        Pedido pedido = event.getPedido();
        sendEmail(pedido.getEmail(), "Pedido Enviado", "pedido-request-sent", pedido);
    }

    @Async
    @EventListener
    public void onPedidoFallido(PedidoRechazadoEvent event) throws MessagingException {
        Pedido pedido = event.getPedido();
        sendEmail(pedido.getEmail(), "Pedido Fallido", "pedido-request-fallido", pedido);
    }

    private void sendEmail(String recipientEmail, String subject, String templateName, Pedido pedido) throws MessagingException {
        Context context = new Context();
        context.setVariable("nombreUsuario", pedido.getUsuario().getNombre());
        context.setVariable("estadoPedido", pedido.getEstado().toString());
        context.setVariable("total", pedido.getTotal());

        String htmlContent = templateEngine.process(templateName, context);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        helper.setTo(recipientEmail);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }

}