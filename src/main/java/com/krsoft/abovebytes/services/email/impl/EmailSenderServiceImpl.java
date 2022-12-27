package com.krsoft.abovebytes.services.email.impl;

import com.krsoft.abovebytes.entities.Property;
import com.krsoft.abovebytes.models.ResponseAPI;
import com.krsoft.abovebytes.services.email.EmailSenderService;
import com.krsoft.abovebytes.services.properties.PropertyService;
import com.krsoft.abovebytes.utils.CustomUtils;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
public class EmailSenderServiceImpl implements EmailSenderService {
    private Configuration freeMarkerCfg;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    PropertyService propertyService;
    private final StringTemplateLoader templateLoader;

    public EmailSenderServiceImpl() {
        this.templateLoader = new StringTemplateLoader();
        this.freeMarkerCfg = configFreeMarker(templateLoader);
    }

    @Override
    public ResponseAPI sendEmailAppointmentSukissime(String to, String user, String customerName, String service, String template, String date) {
        Map<String, Object> parameters = new HashMap<>();
//        String date =  LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy hh:mm a", Locale.FRANCE));
        String output = date.substring(0, 1).toUpperCase() + date.substring(1);
        String templateId = propertyService.getProperty(template).map(Property::getValue).orElse("null");

        parameters.put("user", propertyService.getProperty(user).map(Property::getValue).orElse(""));
        parameters.put("customer", customerName);
        parameters.put("appointmentdate", output);
        parameters.put("webHost", propertyService.getProperty("appointment-webhost").map(Property::getValue).orElse("null"));
        parameters.put("services", service);
        parameters.put("url", "https://abovebytes.com");
        parameters.put("logo", propertyService.getProperty("appointment-logo").map(Property::getValue).orElse("null"));

        if (!CustomUtils.validateTemplate(templateId)) {
            ResponseAPI responseAPI = new ResponseAPI();
            responseAPI.setMessage("Template not found: " + template);

            log.error("Template not found: {}", template);

            return responseAPI;
        }

        templateLoader.putTemplate(template, templateId);

        return sendEmailToUser(to, customerName, generateHtml(parameters, template));
    }

    private Configuration configFreeMarker(StringTemplateLoader templateLoader) {
        Version ourVersion = Configuration.VERSION_2_3_31;
        freemarker.template.Configuration configuration = new freemarker.template.Configuration(ourVersion);
        configuration.setObjectWrapper(new DefaultObjectWrapper(ourVersion));
        configuration.setDefaultEncoding("UTF-8");
        configuration.setOutputEncoding("UTF-8");
        configuration.setTemplateLoader(templateLoader);
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.DEBUG_HANDLER);
        return configuration;
    }

    private String generateHtml(Map<String, Object> parameters, String templateId) {
        try {
            Template template = freeMarkerCfg.getTemplate(templateId);
            StringWriter writer = new StringWriter();
            template.process(parameters, writer);
            return writer.toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private ResponseAPI sendEmailToUser(String to, String customerName, String emailContent) {
        ResponseAPI responseAPI = new ResponseAPI();
        try {
            final MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(emailContent, "text/html; charset=utf-8");
            final Multipart mp = new MimeMultipart();
            final MimeMessage msg = mailSender.createMimeMessage();
            String from = propertyService.getProperty("miscellanea-email").map(Property::getValue).orElse("null");
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            mp.addBodyPart(htmlPart);

            helper.setFrom(new InternetAddress(from));
            helper.setTo(to);
            helper.setSubject("Nouveau Rendez-vous pour " + customerName);
            helper.setText(emailContent, true);
            //helper.addAttachment("MergedPaymentVoucher.pdf", new ByteArrayResource(attachment));
            log.info("Sending email from {}", from);

            mailSender.send(msg);
            responseAPI.setStatus(true);
            log.info("Email successfully sent to {}", to);
        } catch (MessagingException e) {
            responseAPI.setMessage("Problem occurred while creating & sending email " + e);
            log.error("Problem occurred while creating & sending email {}", e);
            throw new RuntimeException("Problem occurred while creating & sending email.", e);
        }
        return responseAPI;
    }
}
