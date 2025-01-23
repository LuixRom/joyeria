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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PedidoEventListener {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Async
    @EventListener
    public void handlePedidoEvent(PedidoEvent event) throws MessagingException {
        Pedido pedido = event.getPedido();
        String templateName;
        String subject;

        // Identificar el tipo de evento
        if (event instanceof PedidoCreatedEvent) {
            templateName = "pedido-created-confirmation";
            subject = "Pedido Creado";
        } else if (event instanceof PedidoConfirmadoEvent) {
            templateName = "pedido-enviado-confirmation";
            subject = "Pedido Enviado";
        } else if (event instanceof PedidoRechazadoEvent) {
            templateName = "pedido-rechazado-confirmation";
            subject = "Pedido Rechazado";
        } else {
            throw new IllegalArgumentException("Evento no manejado: " + event.getClass().getSimpleName());
        }

        // Enviar el correo
        sendEmail(pedido.getEmail(), subject, templateName, pedido);
    }

    private void sendEmail(String recipientEmail, String subject, String templateName, Pedido pedido) throws MessagingException {
        // Preparar el contexto para Thymeleaf
        Context context = new Context();
        context.setVariable("nombreFacturacion", pedido.getNombreFacturacion());
        context.setVariable("apellido", pedido.getApellido());
        context.setVariable("phone", pedido.getPhone());
        context.setVariable("email", pedido.getEmail());
        context.setVariable("calle", pedido.getCalle());
        context.setVariable("distrito", pedido.getDistrito());
        context.setVariable("departamento", pedido.getDepartamento());
        context.setVariable("estadoPedido", pedido.getEstado().toString());
        context.setVariable("total", pedido.getTotal());
        context.setVariable("fechaPedido", pedido.getFechaPedido());
        context.setVariable("documento", pedido.getDocumento()); // Agregar el documento al contexto

        // Pasar los items del pedido
        List<Map<String, Object>> pedidoItems = pedido.getItems().stream().map(item -> {
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put("productName", item.getProducto().getName());
            itemMap.put("cantidad", item.getCantidad());
            itemMap.put("subtotal", item.getSubtotal());
            itemMap.put("imageUrl", item.getProducto().getImagenes());
            return itemMap;
        }).collect(Collectors.toList());
        context.setVariable("pedidoItems", pedidoItems);

        // Procesar el template
        String htmlContent = templateEngine.process(templateName, context);

        // Crear y enviar el correo
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        helper.setTo(recipientEmail);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }
}