package com.proyecto.pe.edu.ciberfake.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.proyecto.pe.edu.ciberfake.exceptions.CiberFakeException;
import com.proyecto.pe.edu.ciberfake.model.EmailNotificacion;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    @Async
    public void enviarMail(EmailNotificacion emailNotificacion){
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("cibernet@cibertec.pe");
            messageHelper.setTo(emailNotificacion.getDestinatario());
            messageHelper.setSubject(emailNotificacion.getAsunto());
            messageHelper.setText(emailNotificacion.getCuerpo());
        };
        try{
            mailSender.send(messagePreparator);
            log.info("Email de verificación enviado!!!");
        } catch(MailException e){
            log.error("Ocurrio un error al enviar email",e);
            throw new CiberFakeException("El error ocurrio cuando se envio un email a "+ emailNotificacion.getDestinatario(),e);
        }
    }
}
