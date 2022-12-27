package com.krsoft.zedpay;

import com.krsoft.zedpay.beans.ApplicationContextProvider;
import com.krsoft.zedpay.entities.Property;
import com.krsoft.zedpay.services.properties.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import java.util.Properties;

@SpringBootApplication
public class MiscellaneaApplication {
    // implements CommandLineRunner
    @Autowired
    ApplicationContextProvider applicationContextProvider;
    @Autowired
    PropertyService propertyService;
    public static void main(String[] args) {
        SpringApplication.run(MiscellaneaApplication.class, args);
    }

//    @Override
//    public void run(String...args) throws Exception {
////        EmailSenderService emailSenderService = applicationContextProvider.getApplicationContext().getBean(EmailSenderService.class);
////        emailSenderService.sendEmailAppointmentSukissime("krsoft2015@gmail.com", "sukissime-user", "Christian Ngabi", "Soins de visage, Pedicure, Massage", "sukissime-template", "date");
//        UserRepository userRepository = applicationContextProvider.getApplicationContext().getBean(UserRepository.class);
//
//        Optional<User> user = userRepository.findByUserLoginAndUserPassword("krys.n", "krys.n");
//
//        if (user.isPresent()) {
//             user.get();
//        }
//    }

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setMaxPayloadLength(64000);
        return loggingFilter;
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(propertyService.getProperty("miscellanea-host").map(Property::getValue).orElse("null"));
        sender.setUsername(propertyService.getProperty("miscellanea-email").map(Property::getValue).orElse("null"));
        sender.setPassword(propertyService.getProperty("miscellanea-password").map(Property::getValue).orElse("null"));
        sender.setPort(587);
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        sender.setJavaMailProperties(props);
        return sender;
    }
}
