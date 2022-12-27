package com.krsoft.abovebytes.controllers;

import com.krsoft.abovebytes.models.ResponseAPI;
import com.krsoft.abovebytes.services.email.EmailSenderService;
import com.krsoft.abovebytes.utils.CustomUtils;
import freemarker.template.TemplateException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(path = "/email")
@CrossOrigin
@Log4j2
public class EmailController {
    @Autowired
    EmailSenderService emailSenderService;

    @PostMapping("/appointment/sukissime")
    public ResponseEntity<ResponseAPI> sendEMailSukissime(@RequestParam(name = "to") String to, @RequestParam(name = "user") String user, @RequestParam(name = "date") String date,
                                                          @RequestParam(name = "customer") String customer, @RequestParam(name = "service") String service, @RequestParam(name = "template") String template) throws TemplateException, IOException {

        if (!CustomUtils.validateEmail(to)) {
            ResponseAPI responseAPI = new ResponseAPI();
            responseAPI.setMessage("Invalid email: " + to);

            log.error("Invalid email: {}", to);

            return new ResponseEntity<>(responseAPI, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Sending appointment email to {} with {} customername for these services {} user {} template {} on {}", to, customer, service, user, template, date);
        return new ResponseEntity<>(emailSenderService.sendEmailAppointmentSukissime(to, user, customer, service, template, date), HttpStatus.OK);
    }
}
